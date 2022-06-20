package com.kchamorro.krugerchallenge.service.Impl;

import com.kchamorro.krugerchallenge.dto.EmployeeRequestDto;
import com.kchamorro.krugerchallenge.dto.EmployeeResponseDto;
import com.kchamorro.krugerchallenge.entity.ContactEntity;
import com.kchamorro.krugerchallenge.entity.EmployeeEntity;
import com.kchamorro.krugerchallenge.entity.RoleEntity;
import com.kchamorro.krugerchallenge.entity.UserEntity;
import com.kchamorro.krugerchallenge.mapper.GeneralMapper;
import com.kchamorro.krugerchallenge.repository.ContactRepository;
import com.kchamorro.krugerchallenge.repository.EmployeeRepository;
import com.kchamorro.krugerchallenge.repository.RoleRepository;
import com.kchamorro.krugerchallenge.repository.UserRepository;
import com.kchamorro.krugerchallenge.service.EmployeeService;
import com.kchamorro.krugerchallenge.util.enumerator.RoleEnum;
import com.kchamorro.krugerchallenge.util.functions.EmployeeUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ContactRepository contactRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<EmployeeEntity> list() {
        return employeeRepository.findAll();
    }

    @Override
    public EmployeeEntity findById(Long id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    @Transactional
    public EmployeeResponseDto save(EmployeeRequestDto employeeRequestDto) {

        validateEmployee(employeeRequestDto);

        EmployeeEntity employeeEntity = GeneralMapper.employeeDtoToEmployeeEntity(employeeRequestDto);
        ContactEntity contactEntity = GeneralMapper.employeeDtoToContactEntity(employeeRequestDto, employeeEntity);

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

        employeeRepository.save(employeeEntity);
        contactRepository.save(contactEntity);
        userRepository.save(userEntity);

        return EmployeeResponseDto.builder()
                .username(userEntity.getUsername())
                .password(password)
                .build();
    }

    @Override
    @Transactional
    public EmployeeEntity update(Long employeeId, EmployeeRequestDto employeeRequestDto) {

        validateEmployee(employeeRequestDto);

        EmployeeEntity employeeEntityDb = employeeRepository.findById(employeeId).orElse(null);

        if (employeeEntityDb != null) {

            EmployeeEntity employeeEntity =
                    GeneralMapper.employeeEntityToEmployeeEntityDb(
                            GeneralMapper.employeeDtoToEmployeeEntity(employeeRequestDto),
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

    private void validateEmployee(EmployeeRequestDto employeeRequestDto){
        if (!employeeRequestDto.getIdentificationCard().matches("[0-9]+"))
            throw new RuntimeException("Identification only numbers");
        if (employeeRequestDto.getIdentificationCard().length() != 10)
            throw new RuntimeException("Identification must have 10 digits");
//        if (!employeeRequestDto.getEmail().matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$"))
//            throw new RuntimeException("Invalid email");
//        if (!employeeRequestDto.getNames().matches("[a-zA-Z]+"))
//            throw new RuntimeException("Invalid names");
//        if (!employeeRequestDto.getLastnames().matches("[a-zA-Z]+"))
//            throw new RuntimeException("Invalid lastnames");
    }
}
