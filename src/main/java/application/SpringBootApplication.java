package application;

import application.entity.*;
import application.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class SpringBootApplication {
    private final static Logger LOGGER = LoggerFactory.getLogger(SpringBootApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }

//    @Bean
//    @Transactional
    public CommandLineRunner test(EmployeeRepository employeeRepository,
                                  DeviceRepository deviceRepository,
                                  RecordRepository recordRepository,
                                  RuleRepository ruleRepository,
                                  ScheduleRepository scheduleRepository) {
        return args -> {
//            Device device = new Device("EQ1", "PC", "ThinkPad S3", "Room 303");
//            device = deviceRepository.save(device);
//            MaintenanceRule rule = new MaintenanceRule("Da baoyang", 20, device);
//            rule = ruleRepository.save(rule);
//            MaintenanceSchedule schedule =
////                    scheduleRepository.findById(3L).get();
//                    new MaintenanceSchedule(Date.from(Instant.now()), rule);
//            schedule = scheduleRepository.save(schedule);
//            LOGGER.info(schedule.toString());
//            LOGGER.info(schedule.getNextMaintenanceDate().toString());
//            List<Device> devices = new ArrayList<>();
//            devices.add(new Device("EQ1", "PC", "ThinkPad S3", "Room 303"));
//            devices.add(new Device("EQ2", "PC", "ThinkPad S4", "Room 304"));
//            devices = StreamSupport.stream(deviceRepository.saveAll(devices).spliterator(), false)
//                    .collect(Collectors.toList());
//            List<MaintenanceRule> rules = new ArrayList<>();
//            rules.add(new MaintenanceRule("Big", 60, devices.get(0)));
//            rules.add(new MaintenanceRule("Big", 50, devices.get(1)));
//            rules.add(new MaintenanceRule("Small", 30, devices.get(1)));
//            rules = StreamSupport.stream(ruleRepository.saveAll(rules).spliterator(), false)
//                    .collect(Collectors.toList());
//            List<MaintenanceSchedule> schedules = new ArrayList<>();
            MaintenanceSchedule schedule = scheduleRepository.findById(7L).orElse(null);
            Employee employee = employeeRepository.findById(10L).orElse(null);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            schedules.add(new MaintenanceSchedule(dateFormat.parse("2018-01-01"), rules.get(0)));
//            schedules.add(new MaintenanceSchedule(dateFormat.parse("2018-01-04"), rules.get(1)));
//            schedules.add(new MaintenanceSchedule(dateFormat.parse("2018-01-07"), rules.get(1)));
//            schedules.add(new MaintenanceSchedule(dateFormat.parse("2018-01-09"), rules.get(2)));
//            scheduleRepository.saveAll(schedules);
            MaintenanceRecord record1 = new MaintenanceRecord(
                    dateFormat.parse("2018-06-03"),
                    dateFormat.parse("2018-06-04"),
                    employee,
                    schedule
            );
            recordRepository.save(record1);
//            Device device1 = deviceRepository.findById(1L).orElse(null);
//            Iterable<Device> deviceIterable = deviceRepository.findAll();
//            deviceIterable.forEach(device -> device.getMaintenanceRules().forEach(rule -> LOGGER.info(rule.getName())));
//            devices = StreamSupport.stream(deviceIterable.spliterator(), false)
//                    .collect(Collectors.toList());
//            MaintenanceReminderUtil reminderUtil = new MaintenanceReminderUtil(devices);
//            List<MaintenanceReminderUtil.MaintenanceReminder>
//                    reminders = reminderUtil.getReminders(10);
//            reminders.forEach(maintenanceReminder -> LOGGER.info(maintenanceReminder.toString()));
        };
    }
}
