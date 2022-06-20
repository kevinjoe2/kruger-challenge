package com.kchamorro.krugerchallenge.mapper;

import com.kchamorro.krugerchallenge.dto.EmployeeRequestDto;
import com.kchamorro.krugerchallenge.entity.ContactEntity;
import com.kchamorro.krugerchallenge.entity.EmployeeEntity;
import com.kchamorro.krugerchallenge.entity.RoleEntity;
import com.kchamorro.krugerchallenge.entity.UserEntity;
import com.kchamorro.krugerchallenge.util.enumerator.ContactTypeEnum;
import com.kchamorro.krugerchallenge.util.enumerator.IdentificationTypeEnum;
import com.kchamorro.krugerchallenge.util.functions.EmployeeUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public static EmployeeEntity employeeDtoToEmployeeEntity(EmployeeRequestDto employeeRequestDto){

        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setIdentificationType(IdentificationTypeEnum.IDENTIFICATION_CARD);
        employeeEntity.setIdentification(employeeRequestDto.getIdentificationCard());
        employeeEntity.setFirstName(employeeRequestDto.getNames());
        employeeEntity.setFirstSurname(employeeRequestDto.getLastnames());

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
}
