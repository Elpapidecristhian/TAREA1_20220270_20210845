package com.example.tarea1_20220270_20210845.controller;

import com.example.tarea1_20220270_20210845.entity.*;
import com.example.tarea1_20220270_20210845.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final JobRepository jobRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeController(EmployeeRepository employeeRepository,
                              JobRepository jobRepository,
                              DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.jobRepository = jobRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public String listEmployees(@RequestParam(required = false) String search, Model model) {
        List<Employee> employees;
        if (search != null && !search.trim().isEmpty()) {
            employees = employeeRepository.searchEmployees(search);
        } else {
            employees = employeeRepository.findAll();
        }
        model.addAttribute("employees", employees);
        return "employees/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        addSelectorsToModel(model);
        return "employees/form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee,
                               RedirectAttributes redirectAttributes) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "El correo electrónico ya existe");
            return "redirect:/employees/new";
        }
        employeeRepository.save(employee);
        redirectAttributes.addFlashAttribute("success", "Empleado guardado exitosamente");
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
        model.addAttribute("employee", employee);
        addSelectorsToModel(model);
        return "employees/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        employeeRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Empleado eliminado exitosamente");
        return "redirect:/employees";
    }

    private void addSelectorsToModel(Model model) {
        model.addAttribute("jobs", jobRepository.findAll());
        model.addAttribute("managers", employeeRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
    }
}