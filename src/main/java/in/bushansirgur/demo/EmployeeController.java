package in.bushansirgur.demo;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    // Explicit constructor injection
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    // Get all employees with pagination, sorting, and filtering
    @GetMapping
    public Page<Employee> getAllEmployees(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "desc") String order,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Boolean employeeAccount
    ) {
        Pageable pageable = PageRequest.of(
                Math.max(0, page - 1), // Page index is 0-based
                limit,
                Sort.by(order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sort)
        );

        return employeeService.getEmployees(q, status, employeeAccount, pageable);
    }

    // Report: Total Salary by Department
    @GetMapping("/reports/total-salary")
    public List<Object[]> getTotalSalaryByDepartment() {
        return employeeService.getTotalSalaryByDepartment();
    }

    // Report: Total Employees by Gender
    @GetMapping("/reports/employee-gender")
    public List<Object[]> countEmployeesByGender() {
        return employeeService.countEmployeesByGender();
    }

    // Report: Average Salary by Position
    @GetMapping("/reports/average-salary")
    public List<Object[]> getAverageSalaryByPosition() {
        return employeeService.getAverageSalaryByPosition();
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new employee
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    // Update an existing employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Custom endpoints
    @GetMapping("/department/{department}")
    public List<Employee> getEmployeesByDepartment(@PathVariable String department) {
        return employeeService.getEmployeesByDepartment(department);
    }

    @GetMapping("/gender/{gender}")
    public List<Employee> getEmployeesByGender(@PathVariable String gender) {
        return employeeService.getEmployeesByGender(gender);
    }

    @GetMapping("/salary/{minSalary}")
    public List<Employee> getEmployeesBySalaryGreaterThan(@PathVariable double minSalary) {
        return employeeService.getEmployeesBySalaryGreaterThan(minSalary);
    }

    @GetMapping("/status/{status}")
    public List<Employee> getEmployeesByStatus(@PathVariable String status) {
        return employeeService.getEmployeesByStatus(status);
    }

    @GetMapping("/search")
    public List<Employee> searchEmployeesByName(@RequestParam String keyword) {
        return employeeService.searchEmployeesByName(keyword);
    }

    @GetMapping("/department/{department}/salary")
    public List<Employee> getEmployeesByDepartmentAndSalaryRange(
            @PathVariable String department,
            @RequestParam double minSalary,
            @RequestParam double maxSalary) {
        return employeeService.getEmployeesByDepartmentAndSalaryRange(department, minSalary, maxSalary);
    }
}
