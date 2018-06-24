package application.service;

import application.entity.util.MaintenanceReminderUtil;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MainService {
    List<MaintenanceReminderUtil.MaintenanceReminder> getReminders(int days);
}
