package com.kchamorro.krugerchallenge.mapper;

import com.kchamorro.krugerchallenge.dto.EmployeeInformationResponse;
import com.kchamorro.krugerchallenge.dto.EmployeeInformationVaccineResponse;
import com.kchamorro.krugerchallenge.dto.EmployeeRequestDto;
import com.kchamorro.krugerchallenge.entity.*;
import com.kchamorro.krugerchallenge.util.enumerator.ContactTypeEnum;
import com.kchamorro.krugerchallenge.util.enumerator.IdentificationTypeEnum;
import com.kchamorro.krugerchallenge.util.enumerator.VaccineStatusEnum;
import com.kchamorro.krugerchallenge.util.functions.EmployeeUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GeneralMapper {

    public static User userEntityToUserSecurity(UserEntity userEntity){
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userEntity.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                authorities
        );
    }

    public static EmployeeEntity employeeDtoToEmployeeEntity(
            EmployeeRequestDto employeeRequestDto,
            UserEntity userEntity
    ){

        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setIdentificationType(IdentificationTypeEnum.IDENTIFICATION_CARD);
        employeeEntity.setIdentification(employeeRequestDto.getIdentificationCard());
        employeeEntity.setFirstName(employeeRequestDto.getNames());
        employeeEntity.setFirstSurname(employeeRequestDto.getLastnames());
        employeeEntity.setUser(userEntity);

        return employeeEntity;
    }

    public static ContactEntity employeeDtoToContactEntity(EmployeeRequestDto employeeRequestDto, EmployeeEntity employeeEntity){

        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setType(ContactTypeEnum.EMAIL);
        contactEntity.setValue(employeeRequestDto.getEmail());
        contactEntity.setPersonEntity(employeeEntity);

        return contactEntity;
    }

    public static EmployeeEntity employeeEntityToEmployeeEntityDb(
            EmployeeEntity employeeEntity,
            EmployeeEntity employeeEntityDb
    ){

        employeeEntity.setIdentification(employeeEntityDb.getIdentification());
        employeeEntity.setFirstName(employeeEntityDb.getFirstName());
        employeeEntity.setFirstSurname(employeeEntityDb.getFirstSurname());

        return employeeEntity;
    }

    public static ContactEntity contactEntityToContactEntityDb(
            ContactEntity contactEntity,
            ContactEntity contactEntityDb
    ){

        contactEntity.setValue(contactEntityDb.getValue());

        return contactEntity;
    }

    public static UserEntity employeeDtoToUserEntity(
            EmployeeRequestDto employeeRequestDto,
            String password,
            RoleEntity roleEntity
    ){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(EmployeeUtil.generateUsername(employeeRequestDto));
        userEntity.setPassword(password);
        userEntity.setRoles(List.of(roleEntity));
        return userEntity;
    }

    public static EmployeeInformationResponse employeeEntityToEmployeeInformationResponse(
            EmployeeEntity employeeEntity
    ){

        ContactEntity mobilPhone = employeeEntity.getContacts().stream()
                .filter(concat -> ContactTypeEnum.MOBIL_PHONE.equals(concat.getType()))
                .findFirst().orElse(null);

        return EmployeeInformationResponse.builder()
                .dateBirth(employeeEntity.getDateBirth() != null ? employeeEntity.getDateBirth().toString() : "")
                .homeAddress(employeeEntity.getHomeAddress() != null ? employeeEntity.getHomeAddress() : "")
                .mobilePhone(mobilPhone != null ? mobilPhone.getValue() : "")
                .statusVaccine(employeeEntity.getVaccineStatus() != null ? employeeEntity.getVaccineStatus().toString() : "")
                .vaccines(employeeEntity.getVaccineEntities() != null ?
                        employeeEntity.getVaccineEntities().stream()
                        .map(vaccine ->
                                EmployeeInformationVaccineResponse
                                        .builder()
                                        .type(vaccine.getType().toString())
                                        .date(vaccine.getDate().toString())
                                        .number(String.valueOf(vaccine.getNumberDoses()))
                                        .build()
                        ).collect(Collectors.toList()) :
                        List.of()
                )
                .build();
    }

    public static EmployeeEntity employeeInformationResponseToEmployeeEntity(
            EmployeeEntity employeeEntity,
            EmployeeInformationResponse employeeInformationResponse
    ){
        employeeEntity.setDateBirth(LocalDate.parse(employeeInformationResponse.getDateBirth()));
        employeeEntity.setHomeAddress(employeeInformationResponse.getHomeAddress());
        employeeEntity.setVaccineStatus(VaccineStatusEnum.valueOf(employeeInformationResponse.getStatusVaccine()));
        return employeeEntity;
    }

    public static ContactEntity employeeInformationResponseToContactEntity(
            EmployeeInformationResponse employeeInformationResponse,
            ContactTypeEnum contactTypeEnum
    ){
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setType(contactTypeEnum);
        contactEntity.setValue(employeeInformationResponse.getMobilePhone());
        return contactEntity;
    }

    public static List<VaccineEntity> employeeInformationResponseToVaccineEntity(
            EmployeeInformationResponse employeeInformationResponse,
            List<VaccineTypeEntity> vaccineTypeEntities
    ){

        List<VaccineEntity> vaccineEntities = new ArrayList<>();
        for (EmployeeInformationVaccineResponse vaccine : employeeInformationResponse.getVaccines()) {
            VaccineEntity vaccineEntity = new VaccineEntity();
            vaccineEntity.setDate(LocalDate.parse(vaccine.getDate()));
            vaccineEntity.setNumberDoses(Integer.parseInt(vaccine.getNumber()));
            if (vaccineTypeEntities.stream().anyMatch(vaccineF -> vaccine.getType().equals(vaccineF.getName())))
                vaccineEntity.setType(vaccineTypeEntities.stream().filter(vaccineF -> vaccine.getType().equals(vaccineF.getName())).findFirst().get());
            vaccineEntities.add(vaccineEntity);
        }
        return vaccineEntities;
    }
}
