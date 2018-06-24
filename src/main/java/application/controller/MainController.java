package application.controller;

import application.repository.DeviceRepository;
import application.service.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    private final static Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    private static final int REMINDER_DAYS = 365;

    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping(value = "/reminder", method = RequestMethod.GET)
    public String test(Model model) {
        LOGGER.info("testing");
        model.addAttribute("reminder", mainService.getReminders(REMINDER_DAYS));
        return "reminder";
    }

//    @RequestMapping(value = "/api/device/list", method = RequestMethod.GET)
//    public @ResponseBody
//    Iterable<Device> deviceList() {
//        List<Device> devices = deviceRepository.findAll();
//        MaintenanceReminderUtil reminderUtil = new MaintenanceReminderUtil(devices);
//        List<MaintenanceReminderUtil.MaintenanceReminder>
//                reminders = reminderUtil.getReminders(10);
//        reminders.forEach(maintenanceReminder -> LOGGER.info(maintenanceReminder.toString()));
//        return devices;
//    }
}
