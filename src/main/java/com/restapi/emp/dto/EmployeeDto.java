package com.restapi.emp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {
    private Long id;

    @NotEmpty(message = "Firstname is mandatory.")
    private String firstName;

    @NotEmpty(message = "Lastname is mandatory.")
    private String lastName;

    @NotBlank(message = "Email is mandatory.")
    @Email
    private String email;

    @NotBlank(message = "Department ID is mandatory.")
    private Long departmentId;

    private DepartmentDto departmentDto;

    public EmployeeDto(Long id,String firstName,String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    public EmployeeDto(Long id,String firstName,String lastName, String email, Long departmentId) {
        this(id,firstName,lastName,email);
        this.departmentId = departmentId;
    }


    public EmployeeDto(Long id,String firstName,String lastName, String email, DepartmentDto departmentDto) {
        this(id,firstName,lastName,email);
        this.departmentDto = departmentDto;
    }
}