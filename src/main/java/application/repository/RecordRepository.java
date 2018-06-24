package application.repository;

import application.entity.MaintenanceRecord;
import org.springframework.data.repository.CrudRepository;

public interface RecordRepository extends CrudRepository<MaintenanceRecord, Long> {
}
