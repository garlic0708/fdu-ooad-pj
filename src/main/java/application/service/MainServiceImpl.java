package application.service;

import application.entity.*;
import application.entity.util.MaintenanceReminderUtil;
import application.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainServiceImpl implements MainService {
    private final DeviceRepository deviceRepository;
    private final RecordRepository recordRepository;
    private final EmployeeRepository employeeRepository;
    private final ScheduleRepository scheduleRepository;
    private final RuleRepository ruleRepository;

    @Autowired
    public MainServiceImpl(DeviceRepository deviceRepository, RecordRepository recordRepository, EmployeeRepository employeeRepository, ScheduleRepository scheduleRepository, RuleRepository ruleRepository) {
        this.deviceRepository = deviceRepository;
        this.recordRepository = recordRepository;
        this.employeeRepository = employeeRepository;
        this.scheduleRepository = scheduleRepository;
        this.ruleRepository = ruleRepository;
    }

    @Override
    public List<MaintenanceReminderUtil.MaintenanceReminder> getReminders(int days) {
        Iterable<MaintenanceRule> rules = ruleRepository.findAll();
        List<Device> devices = deviceRepository.findAll();
        MaintenanceReminderUtil reminderUtil = new MaintenanceReminderUtil(devices);
        return reminderUtil.getReminders(days);
    }

    @Override
    public List<MaintenanceRecord> getRecords() {
        return recordRepository.findAll();
    }

    @Override
    public List<MaintenanceRecord> getRecords(long scheduleId) {
        return recordRepository.findByScheduleId(scheduleId);
    }

    @Override
    public void addRecord(MaintenanceRecord record) {
        recordRepository.save(record);
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Device> getDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public List<MaintenanceSchedule> getSchedules(long deviceId) {
        return scheduleRepository.findByRule_Device_Id(deviceId);
    }

    @Override
    public long getTotalDurationByDevice(long deviceId) {
        return recordRepository.findByScheduleRuleDeviceId(deviceId)
                .stream().map(MaintenanceRecord::getDurationInMinutes)
                .reduce(0L, Long::sum);
    }

    @Override
    public long getTotalDurationBySchedule(long scheduleId) {
        return recordRepository.findByScheduleId(scheduleId)
                .stream().map(MaintenanceRecord::getDurationInMinutes)
                .reduce(0L, Long::sum);
    }
}
