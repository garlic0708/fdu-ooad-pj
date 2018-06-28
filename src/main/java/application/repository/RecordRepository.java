package application.repository;

import application.entity.MaintenanceRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecordRepository extends CrudRepository<MaintenanceRecord, Long> {

    List<MaintenanceRecord> findAll();

    List<MaintenanceRecord> findByScheduleId(long scheduleId);

    List<MaintenanceRecord> findByScheduleRuleDeviceId(long deviceId);
}
