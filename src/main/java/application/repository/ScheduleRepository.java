package application.repository;

import application.entity.MaintenanceSchedule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<MaintenanceSchedule, Long> {

    List<MaintenanceSchedule> findByRule_Device_Id(long id);
}
