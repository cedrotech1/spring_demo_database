package in.bushansirgur.demo;

import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecifications {

    public static Specification<Employee> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isEmpty()) return null;
            String likeKeyword = "%" + keyword.toLowerCase() + "%";
            return criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("firstname")), likeKeyword),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("lastname")), likeKeyword)
            );
        };
    }

    public static Specification<Employee> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null || status.isEmpty()) return null;
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Employee> hasEmployeeAccount(Boolean hasAccount) {
        return (root, query, criteriaBuilder) -> {
            if (hasAccount == null) return null;
            return criteriaBuilder.equal(root.get("employeeAccount"), hasAccount);
        };
    }
}

