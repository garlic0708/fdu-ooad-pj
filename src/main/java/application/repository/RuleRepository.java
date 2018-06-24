package application.repository;

import application.entity.MaintenanceRule;
import org.springframework.data.repository.CrudRepository;

public interface RuleRepository extends CrudRepository<MaintenanceRule, Long> {
}
