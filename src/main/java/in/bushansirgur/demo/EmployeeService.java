package in.bushansirgur.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepo;

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepo.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        return employeeRepo.save(employee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepo.existsById(id)) {
            throw new RuntimeException("Employee not found with id " + id);
        }
        employeeRepo.deleteById(id);
    }
}
