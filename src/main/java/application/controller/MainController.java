package application.controller;

import application.entity.MaintenanceRecord;
import application.entity.MaintenanceSchedule;
import application.service.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String reminder(Model model) {
        LOGGER.info("testing");
        model.addAttribute("reminder", mainService.getReminders(REMINDER_DAYS));
        return "reminder";
    }

    @RequestMapping(value = "/record", method = RequestMethod.GET)
    public String record() {
        return "record";
    }

    @RequestMapping(value = "/getRecord", method = RequestMethod.GET)
    @ResponseBody
    public List<MaintenanceRecord> getRecord() {
        return mainService.getRecords();
    }

    @GetMapping("/addRecord")
    public String addRecordForm(Model model) {
        model.addAttribute("employees", mainService.getEmployees())
                .addAttribute("record", new MaintenanceRecord())
                .addAttribute("devices", mainService.getDevices());
        return "addRecord";
    }

    @PostMapping("/addRecord")
    public String addRecord(@ModelAttribute MaintenanceRecord record) {
        LOGGER.info(record.toString());
        mainService.addRecord(record);
        return "redirect:/record";
    }

    @GetMapping("/getSchedule")
    public @ResponseBody List<MaintenanceSchedule> getSchedule(@RequestParam String device) {
        return mainService.getSchedules(Long.parseLong(device));
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
