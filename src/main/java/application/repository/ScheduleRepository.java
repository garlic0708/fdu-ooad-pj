package application.repository;

import application.entity.MaintenanceSchedule;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepository extends CrudRepository<MaintenanceSchedule, Long> {
}
