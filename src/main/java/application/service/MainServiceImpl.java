package application.service;

import application.entity.Device;
import application.entity.util.MaintenanceReminderUtil;
import application.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainServiceImpl implements MainService {
    private final DeviceRepository deviceRepository;

    @Autowired
    public MainServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public List<MaintenanceReminderUtil.MaintenanceReminder> getReminders(int days) {
        List<Device> devices = deviceRepository.findAll();
        MaintenanceReminderUtil reminderUtil = new MaintenanceReminderUtil(devices);
        return reminderUtil.getReminders(days);
    }
}
