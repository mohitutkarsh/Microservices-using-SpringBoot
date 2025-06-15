package com.Mohit.department_service.controller;

import ch.qos.logback.classic.joran.action.LoggerAction;
import com.Mohit.department_service.client.EmployeeClient;
import com.Mohit.department_service.model.Department;
import com.Mohit.department_service.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private EmployeeClient employeeClient;

    @PostMapping
    public Department add(@RequestBody Department department) {
        LOGGER.info("Adding department: {}", department);
        return repository.addDepartment(department);
    }

    @GetMapping
    public List<Department> findAll() {
        LOGGER.info("Finding all departments");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Department findById(@PathVariable("id") Long id) {
        LOGGER.info("Finding department by id: {}", id);
        return repository.findById(id);
    }

    @GetMapping("/with-employees")
    public List<Department> findWithEmployees() {
        LOGGER.info("Finding all departments with employees");
        List<Department> departments =  repository.findAll();

        departments.forEach(department ->
                department.setEmployees(employeeClient.findByDepartment
                        (department.getId())));

        return departments;
    }
}
