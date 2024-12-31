package in.bushansirgur.demo;
import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
 
    Page<Employee> findAll(Specification<Employee> spec, Pageable pageable);

      // Custom Query for Report: Total Salary by Department
      @Query("SELECT e.department, SUM(e.salary) FROM Employee e GROUP BY e.department")
      List<Object[]> findTotalSalaryByDepartment();
  
      // Custom Query for Report: Total Employees by Gender
      @Query("SELECT e.gender, COUNT(e) FROM Employee e GROUP BY e.gender")
      List<Object[]> countEmployeesByGender();
  
      // Custom Query for Report: Average Salary by Position
      @Query("SELECT e.position, AVG(e.salary) FROM Employee e GROUP BY e.position")
      List<Object[]> findAverageSalaryByPosition();

    // Find employees by department
    List<Employee> findByDepartment(String department);

    // Find employees by gender
    List<Employee> findByGender(String gender);

    // Find employees whose salary is greater than a specific value
    List<Employee> findBySalaryGreaterThan(double salary);

    // Find employees by status
    List<Employee> findByStatus(String status);

    // Custom query to find employees by department and salary range
    @Query("SELECT e FROM Employee e WHERE e.department = ?1 AND e.salary BETWEEN ?2 AND ?3")
    List<Employee> findByDepartmentAndSalaryRange(String department, double minSalary, double maxSalary);

    // Custom query to find employees by name containing a keyword (case insensitive)
    @Query("SELECT e FROM Employee e WHERE LOWER(e.firstname) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(e.lastname) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Employee> findByNameContaining(String keyword);
}
