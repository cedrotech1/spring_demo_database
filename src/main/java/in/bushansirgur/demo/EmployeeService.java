package in.bushansirgur.demo;

import java.util.List;
import java.util.Optional;
import in.bushansirgur.demo.ResourceNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.RequiredArgsConstructor;

@Service
// @RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepo; // This will hold the injected repository

    // Constructor injection for employeeRepo
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
    public Page<Employee> getEmployees(String q, String status, Boolean employeeAccount, Pageable pageable) {
        Specification<Employee> spec = Specification
                .where(EmployeeSpecifications.hasKeyword(q))
                .and(EmployeeSpecifications.hasStatus(status))
                .and(EmployeeSpecifications.hasEmployeeAccount(employeeAccount));

        return employeeRepo.findAll(spec, pageable);
    }

    public List<Object[]> getTotalSalaryByDepartment() {
        return employeeRepo.findTotalSalaryByDepartment();
    }

    public List<Object[]> countEmployeesByGender() {
        return employeeRepo.countEmployeesByGender();
    }

    public List<Object[]> getAverageSalaryByPosition() {
        return employeeRepo.findAverageSalaryByPosition();
    }
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepo.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        employee.setFirstname(employeeDetails.getFirstname());
        employee.setLastname(employeeDetails.getLastname());
        employee.setAge(employeeDetails.getAge());
        employee.setGender(employeeDetails.getGender());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPhone(employeeDetails.getPhone());
        employee.setAddress(employeeDetails.getAddress());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setPosition(employeeDetails.getPosition());
        employee.setSalary(employeeDetails.getSalary());
        employee.setStatus(employeeDetails.getStatus());
        return employeeRepo.save(employee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepo.existsById(id)) {
            throw new RuntimeException("Employee not found with id " + id);
        }
        employeeRepo.deleteById(id);
    }

    // Additional service methods for custom queries
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepo.findByDepartment(department);
    }

    public List<Employee> getEmployeesByGender(String gender) {
        return employeeRepo.findByGender(gender);
    }

    public List<Employee> getEmployeesBySalaryGreaterThan(double salary) {
        return employeeRepo.findBySalaryGreaterThan(salary);
    }

    public List<Employee> getEmployeesByStatus(String status) {
        return employeeRepo.findByStatus(status);
    }

    public List<Employee> getEmployeesByDepartmentAndSalaryRange(String department, double minSalary, double maxSalary) {
        return employeeRepo.findByDepartmentAndSalaryRange(department, minSalary, maxSalary);
    }

    public List<Employee> searchEmployeesByName(String keyword) {
        return employeeRepo.findByNameContaining(keyword);
    }
}
