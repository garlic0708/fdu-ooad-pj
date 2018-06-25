package application.service;

import application.entity.Device;
import application.entity.Employee;
import application.entity.MaintenanceRecord;
import application.entity.MaintenanceSchedule;
import application.entity.util.MaintenanceReminderUtil;

import java.util.List;

public interface MainService {
    List<MaintenanceReminderUtil.MaintenanceReminder> getReminders(int days);

    List<MaintenanceRecord> getRecords();

    void addRecord(MaintenanceRecord record);

    Iterable<Employee> getEmployees();

    Iterable<Device> getDevices();

    List<MaintenanceSchedule> getSchedules(long deviceId);
}
