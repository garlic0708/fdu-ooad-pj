package application.service;

import application.entity.Device;
import application.entity.Employee;
import application.entity.MaintenanceRecord;
import application.entity.MaintenanceSchedule;
import application.entity.util.MaintenanceReminderUtil;
import application.repository.DeviceRepository;
import application.repository.EmployeeRepository;
import application.repository.RecordRepository;
import application.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainServiceImpl implements MainService {
    private final DeviceRepository deviceRepository;
    private final RecordRepository recordRepository;
    private final EmployeeRepository employeeRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public MainServiceImpl(DeviceRepository deviceRepository, RecordRepository recordRepository, EmployeeRepository employeeRepository, ScheduleRepository scheduleRepository) {
        this.deviceRepository = deviceRepository;
        this.recordRepository = recordRepository;
        this.employeeRepository = employeeRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<MaintenanceReminderUtil.MaintenanceReminder> getReminders(int days) {
        List<Device> devices = deviceRepository.findAll();
        MaintenanceReminderUtil reminderUtil = new MaintenanceReminderUtil(devices);
        return reminderUtil.getReminders(days);
    }

    @Override
    public List<MaintenanceRecord> getRecords() {
        return recordRepository.findAll();
    }

    @Override
    public void addRecord(MaintenanceRecord record) {
        recordRepository.save(record);
    }

    @Override
    public Iterable<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Iterable<Device> getDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public List<MaintenanceSchedule> getSchedules(long deviceId) {
        return scheduleRepository.findByRule_Device_Id(deviceId);
    }
}
