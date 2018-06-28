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

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class SpringBootApplication {
    private final static Logger LOGGER = LoggerFactory.getLogger(SpringBootApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }

    @Bean
//    @Transactional
    public CommandLineRunner test(EmployeeRepository employeeRepository,
                                  DeviceRepository deviceRepository,
                                  RecordRepository recordRepository,
                                  RuleRepository ruleRepository,
                                  ScheduleRepository scheduleRepository) {
        return args -> {
            Device device1 = new Device("Device 1", "PC", "ThinkPad S3", "Room 303");
            Device device2 = new Device("Device 2", "PC", "ThinkPad S3", "Room 304");
            deviceRepository.saveAll(Arrays.asList(device1, device2));

            MaintenanceRule dev1Big = new MaintenanceRule("Device 1 Big", 60, device1);
            MaintenanceRule dev1Small = new MaintenanceRule("Device 1 Small", 30, device1);
            MaintenanceRule dev2Big = new MaintenanceRule("Device 2 Big", 50, device2);
            MaintenanceRule dev2Small = new MaintenanceRule("Device 2 Small", 20, device2);
            ruleRepository.saveAll(Arrays.asList(dev1Big, dev1Small, dev2Big, dev2Small));

            Instant now = Instant.now();
            MaintenanceSchedule div1BigScd = new MaintenanceSchedule(Date.from(now.minus(Duration.ofDays(1))), dev1Big);
            MaintenanceSchedule dev2BigScd = new MaintenanceSchedule(Date.from(now.minus(Duration.ofDays(2))), dev2Big);
            MaintenanceSchedule dev1SmallScd = new MaintenanceSchedule(Date.from(now.minus(Duration.ofDays(3))), dev1Small);
            MaintenanceSchedule dev2SmallScd = new MaintenanceSchedule(Date.from(now.minus(Duration.ofDays(4))), dev2Small);
            scheduleRepository.saveAll(Arrays.asList(div1BigScd, dev2BigScd, dev1SmallScd, dev2SmallScd));

            Employee jack = new Employee("Jack");
            Employee mike = new Employee("Mike");
            employeeRepository.saveAll(Arrays.asList(jack, mike));

            MaintenanceRecord record1 = new MaintenanceRecord(Date.from(now), Date.from(now.plus(Duration.ofMinutes(90))),
                    jack, div1BigScd);
            MaintenanceRecord record2 = new MaintenanceRecord(Date.from(now), Date.from(now.plus(Duration.ofMinutes(120))),
                    mike, dev2BigScd);
            recordRepository.saveAll(Arrays.asList(record1, record2));
//            Device device = new Device("EQ3", "PC", "ThinkPad S3", "Room 303");
//            deviceRepository.save(device);
//            System.out.println(deviceRepository.findAll());
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
//            MaintenanceSchedule schedule = scheduleRepository.findById(7L).orElse(null);
//            Employee employee = employeeRepository.findById(10L).orElse(null);
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            schedules.add(new MaintenanceSchedule(dateFormat.parse("2018-01-01"), rules.get(0)));
//            schedules.add(new MaintenanceSchedule(dateFormat.parse("2018-01-04"), rules.get(1)));
//            schedules.add(new MaintenanceSchedule(dateFormat.parse("2018-01-07"), rules.get(1)));
//            schedules.add(new MaintenanceSchedule(dateFormat.parse("2018-01-09"), rules.get(2)));
//            scheduleRepository.saveAll(schedules);
//            MaintenanceRecord record1 = new MaintenanceRecord(
//                    dateFormat.parse("2018-06-03"),
//                    dateFormat.parse("2018-06-04"),
//                    employee,
//                    schedule
//            );
//            recordRepository.save(record1);
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
