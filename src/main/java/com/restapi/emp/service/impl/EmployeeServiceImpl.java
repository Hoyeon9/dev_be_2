package com.restapi.emp.service.impl;

import com.restapi.emp.dto.EmployeeDto;
import com.restapi.emp.dto.mapper.EmployeeMapper;
import com.restapi.emp.entity.Department;
import com.restapi.emp.entity.Employee;
import com.restapi.emp.exception.ResourceNotFoundException;
import com.restapi.emp.repository.DepartmentRepository;
import com.restapi.emp.repository.EmployeeRepository;
import com.restapi.emp.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.restapi.emp.service.impl.EmpDeptCommon.getEmployee;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

//        Department department = departmentRepository.findById(employeeDto.getDepartmentId())
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Department is not exists with id: " +
//                                employeeDto.getDepartmentId(),
//                                HttpStatus.NOT_FOUND));

        Department department = EmpDeptCommon.getDepartment(employeeDto.getDepartmentId(), departmentRepository);
        employee.setDepartment(department);

        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = getEmployee(employeeId, employeeRepository);

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .toList();
                //.map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
                //.collect(Collectors.toList());
    }

    public List<EmployeeDto> getAllEmployeesDepartment() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::mapToEmployeeDepartmentDto)
                .toList();
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee = EmpDeptCommon.getEmployee(employeeId, employeeRepository);
//                employeeRepository.findById(employeeId)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException(
//                                "Employee is not exists with given id: " + employeeId,
//                                HttpStatus.NOT_FOUND)
//        );
        if (updatedEmployee.getFirstName() != null)
            employee.setFirstName(updatedEmployee.getFirstName());
        if (updatedEmployee.getLastName() != null)
            employee.setLastName(updatedEmployee.getLastName());
        if (updatedEmployee.getEmail() != null)
            employee.setEmail(updatedEmployee.getEmail());


        Department department = EmpDeptCommon.getDepartment(updatedEmployee.getDepartmentId(), departmentRepository);
//        departmentRepository.findById(updatedEmployee.getDepartmentId())
//                .orElseThrow(() ->
//                        new ResourceNotFoundException(
//                                "Department is not exists with id: " + updatedEmployee.getDepartmentId(),
//                                HttpStatus.NOT_FOUND
//                                ));

        employee.setDepartment(department);

        //Employee updatedEmployeeObj = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {

        Employee employee = EmpDeptCommon.getEmployee(employeeId, employeeRepository);
//        employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new ResourceNotFoundException(
//                        "Employee is not exists with given id: " + employeeId,
//                        HttpStatus.NOT_FOUND)
//        );

        employeeRepository.deleteById(employeeId);
    }
}