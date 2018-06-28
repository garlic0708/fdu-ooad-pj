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

    List<MaintenanceRecord> getRecords(long scheduleId);

    void addRecord(MaintenanceRecord record);

    List<Employee> getEmployees();

    List<Device> getDevices();

    List<MaintenanceSchedule> getSchedules(long deviceId);
}
