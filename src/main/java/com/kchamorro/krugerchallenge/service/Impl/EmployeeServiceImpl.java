package com.kchamorro.krugerchallenge.service.Impl;

import com.kchamorro.krugerchallenge.dto.EmployeeInformationResponse;
import com.kchamorro.krugerchallenge.dto.EmployeeRequestDto;
import com.kchamorro.krugerchallenge.dto.EmployeeResponseDto;
import com.kchamorro.krugerchallenge.entity.*;
import com.kchamorro.krugerchallenge.mapper.GeneralMapper;
import com.kchamorro.krugerchallenge.repository.*;
import com.kchamorro.krugerchallenge.security.CustomDecodeJWT;
import com.kchamorro.krugerchallenge.service.EmployeeService;
import com.kchamorro.krugerchallenge.util.enumerator.ContactTypeEnum;
import com.kchamorro.krugerchallenge.util.enumerator.EmployeeValidatorTypeEnum;
import com.kchamorro.krugerchallenge.util.enumerator.RoleEnum;
import com.kchamorro.krugerchallenge.util.enumerator.VaccineStatusEnum;
import com.kchamorro.krugerchallenge.util.functions.EmployeeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final PersonRepository personRepository;
    private final EmployeeRepository employeeRepository;
    private final ContactRepository contactRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final VaccineRepository vaccineRepository;
    private final VaccineTypeRepository vaccineTypeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<EmployeeInformationResponse> list() {
        return employeeRepository.findAll().stream()
                .map(GeneralMapper::employeeEntityToEmployeeInformationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeInformationResponse> search(
            String statusVaccine,
            String typeVaccine,
            String dateFromVaccine,
            String dateToVaccine
    ) {
        return employeeRepository.findAll().stream()
                .filter(employee -> {
                    if (statusVaccine != null && !statusVaccine.isBlank())
                        return employee.getVaccineStatus().equals(VaccineStatusEnum.valueOf(statusVaccine));
                    else
                        return true;
                })
                .filter(employee -> {
                    if (typeVaccine != null && !typeVaccine.isBlank())
                        return employee
                                .getVaccineEntities()
                                .stream()
                                .anyMatch(vaccine -> vaccine.getType().getName().equals(typeVaccine));
                    else
                        return true;
                })
                .filter(employee -> {
                    if (dateFromVaccine != null && !dateFromVaccine.isBlank()
                            && !employee.getVaccineEntities().isEmpty())
                        return employee
                                .getVaccineEntities()
                                .stream()
                                .anyMatch(vaccine -> vaccine.getDate().isAfter(LocalDate.parse(dateFromVaccine))
                                        || vaccine.getDate().isEqual(LocalDate.parse(dateToVaccine)));
                    else
                        return true;
                })
                .filter(employee -> {
                    if (dateToVaccine != null && !dateToVaccine.isBlank()
                            && !employee.getVaccineEntities().isEmpty())
                        return employee
                                .getVaccineEntities()
                                .stream()
                                .anyMatch(vaccine -> vaccine.getDate().isBefore(LocalDate.parse(dateToVaccine))
                                        || vaccine.getDate().isEqual(LocalDate.parse(dateToVaccine)));
                    else
                        return true;
                })
                .map(GeneralMapper::employeeEntityToEmployeeInformationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeEntity findById(Long id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public EmployeeInformationResponse information(String token) {
        String username = CustomDecodeJWT.getUsername(token);
        EmployeeEntity employeeEntity = employeeRepository.findByUsername(username);
        return GeneralMapper.employeeEntityToEmployeeInformationResponse(employeeEntity);
    }

    @Override
    @Transactional
    public EmployeeResponseDto save(EmployeeRequestDto employeeRequestDto) {

        validateEmployee(employeeRequestDto, EmployeeValidatorTypeEnum.SAVE);

        RoleEntity roleEntity = roleRepository.findFirstByName(RoleEnum.ROLE_EMPLOYEE.toString());

        if (roleEntity == null)
            throw new RuntimeException("Role employee not found");

        String password = EmployeeUtil.generatePassword(employeeRequestDto);

        UserEntity userEntity =
                GeneralMapper.employeeDtoToUserEntity(
                        employeeRequestDto,
                        passwordEncoder.encode(password),
                        roleEntity
                );

        EmployeeEntity employeeEntity = GeneralMapper.employeeDtoToEmployeeEntity(employeeRequestDto, userEntity);
        ContactEntity contactEntity = GeneralMapper.employeeDtoToContactEntity(employeeRequestDto, employeeEntity);

        userRepository.save(userEntity);
        employeeRepository.save(employeeEntity);
        contactRepository.save(contactEntity);

        return EmployeeResponseDto.builder()
                .username(userEntity.getUsername())
                .password(password)
                .build();
    }

    @Override
    @Transactional
    public EmployeeInformationResponse saveEmployee(
            String token,
            EmployeeInformationResponse employeeRequestDto
    ) {

        String username = CustomDecodeJWT.getUsername(token);
        EmployeeEntity employeeEntity = employeeRepository.findByUsername(username);

        // MAPPER INFORMATION EMPLOYEE
        GeneralMapper.employeeInformationResponseToEmployeeEntity(
                employeeEntity, employeeRequestDto);

        // UPDATE CONTACT MOBIL PHONE
        ContactEntity contactEntity
                = GeneralMapper.employeeInformationResponseToContactEntity(
                employeeRequestDto, ContactTypeEnum.MOBIL_PHONE);
        contactRepository.save(contactEntity);
        employeeEntity.getContacts().add(contactEntity);

        // UPDATE VACCINE
        List<VaccineEntity> vaccineEntities = GeneralMapper.employeeInformationResponseToVaccineEntity(
                employeeRequestDto,
                vaccineTypeRepository.findAll());
        vaccineRepository.saveAll(vaccineEntities);
        employeeEntity.getVaccineEntities().addAll(vaccineEntities);

        // UPDATE EMPLOYEE
        employeeRepository.save(employeeEntity);

        return GeneralMapper
                .employeeEntityToEmployeeInformationResponse(
                        employeeEntity
                );
    }

    @Override
    @Transactional
    public EmployeeEntity update(Long employeeId, EmployeeRequestDto employeeRequestDto) {

        validateEmployee(employeeRequestDto, EmployeeValidatorTypeEnum.UPDATE);

        EmployeeEntity employeeEntityDb = employeeRepository.findById(employeeId).orElse(null);

        if (employeeEntityDb != null) {

            EmployeeEntity employeeEntity =
                    GeneralMapper.employeeEntityToEmployeeEntityDb(
                            GeneralMapper.employeeDtoToEmployeeEntity(employeeRequestDto, employeeEntityDb.getUser()),
                            employeeEntityDb);

            ContactEntity contactEntityDb = employeeEntity.getContacts().stream()
                    .filter(contact -> contact.getValue().equals(employeeRequestDto.getEmail()))
                    .findFirst().orElse(null);

            employeeRepository.save(employeeEntity);

            if (contactEntityDb != null) {
                contactRepository.save(
                        GeneralMapper.contactEntityToContactEntityDb(
                                GeneralMapper.employeeDtoToContactEntity(employeeRequestDto, employeeEntity),
                                contactEntityDb
                        ));
            } else {
                ContactEntity contactEntity =
                        GeneralMapper.employeeDtoToContactEntity(employeeRequestDto, employeeEntity);
                contactRepository.save(contactEntity);
            }

            return employeeEntity;
        } else {
            throw new RuntimeException("Employee not found");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    private void validateEmployee(
            EmployeeRequestDto employeeRequestDto,
            EmployeeValidatorTypeEnum validatorTypeEnum
    ) {

        if (!employeeRequestDto.getIdentificationCard().matches("[0-9]+"))
            throw new RuntimeException("Identification only numbers");
        if (employeeRequestDto.getIdentificationCard().length() != 10)
            throw new RuntimeException("Identification must have 10 digits");
        if (!employeeRequestDto.getEmail().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
            throw new RuntimeException("Invalid email");
        if (!employeeRequestDto.getNames().replace(" ", "").matches("^[a-zA-Z]*$"))
            throw new RuntimeException("Invalid names");
        if (!employeeRequestDto.getLastnames().replace(" ", "").matches("^[a-zA-Z]*$"))
            throw new RuntimeException("Invalid lastnames");

        if (validatorTypeEnum.equals(EmployeeValidatorTypeEnum.SAVE)) {
            if (employeeRepository.findByUsername(EmployeeUtil.generateUsername(employeeRequestDto)) != null)
                throw new RuntimeException("Username Employee already exists");
            if (personRepository.findByIdentification(employeeRequestDto.getIdentificationCard()) != null)
                throw new RuntimeException("Identification Employee already exists");
        }
    }
}
