package application.service;

import application.SpringBootApplication;
import application.entity.Device;
import application.entity.Employee;
import application.entity.MaintenanceRecord;
import application.entity.MaintenanceSchedule;
import application.entity.util.MaintenanceReminderUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootApplication.class})
@Transactional
@Sql(scripts = {"classpath:create.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class MainServiceTest {

    @Autowired
    private MainService mainService;

//    @Before
//    public void init() {
//        Device device1 = new Device("Device 1", "PC", "ThinkPad S3", "Room 303");
//        Device device2 = new Device("Device 2", "PC", "ThinkPad S3", "Room 304");
//        deviceRepository.saveAll(Arrays.asList(device1, device2));
//
//        MaintenanceRule dev1Big = new MaintenanceRule("Device 1 Big", 60, device1);
//        MaintenanceRule dev1Small = new MaintenanceRule("Device 1 Small", 30, device1);
//        MaintenanceRule dev2Big = new MaintenanceRule("Device 2 Big", 50, device2);
//        MaintenanceRule dev2Small = new MaintenanceRule("Device 2 Small", 20, device2);
//        ruleRepository.saveAll(Arrays.asList(dev1Big, dev1Small, dev2Big, dev2Small));
//
//        Instant now = Instant.now();
//        MaintenanceSchedule div1BigScd = new MaintenanceSchedule(Date.from(now.minus(Duration.ofDays(1))), dev1Big);
//        MaintenanceSchedule dev2BigScd = new MaintenanceSchedule(Date.from(now.minus(Duration.ofDays(2))), dev2Big);
//        MaintenanceSchedule dev1SmallScd = new MaintenanceSchedule(Date.from(now.minus(Duration.ofDays(3))), dev1Small);
//        MaintenanceSchedule dev2SmallScd = new MaintenanceSchedule(Date.from(now.minus(Duration.ofDays(4))), dev2Small);
//        scheduleRepository.saveAll(Arrays.asList(div1BigScd, dev2BigScd, dev1SmallScd, dev2SmallScd));
//
//        Employee jack = new Employee("Jack");
//        Employee mike = new Employee("Mike");
//        employeeRepository.saveAll(Arrays.asList(jack, mike));
//
//        MaintenanceRecord record1 = new MaintenanceRecord(Date.from(now), Date.from(now.plus(Duration.ofMinutes(90))),
//                jack, div1BigScd);
//        MaintenanceRecord record2 = new MaintenanceRecord(Date.from(now), Date.from(now.plus(Duration.ofMinutes(120))),
//                mike, dev2BigScd);
//        recordRepository.saveAll(Arrays.asList(record1, record2));
//    }

    @Test
    public void getReminders() {
        List<MaintenanceReminderUtil.MaintenanceReminder> reminders = mainService.getReminders(120);
        assertEquals(reminders.size(), 14);
        assertEquals(filterReminders(reminders, "Device 1 Big").size(), 2);
        assertEquals(filterReminders(reminders, "Device 1 Small").size(), 4);
        assertEquals(filterReminders(reminders, "Device 2 Big").size(), 2);
        assertEquals(filterReminders(reminders, "Device 2 Small").size(), 6);
    }

    private List<MaintenanceReminderUtil.MaintenanceReminder> filterReminders(
            List<MaintenanceReminderUtil.MaintenanceReminder> reminders, String ruleName) {
        return reminders.stream().filter(rmd -> rmd.getRuleName().equals(ruleName)).collect(Collectors.toList());
    }

    @Test
    public void getRecords() {
        List<MaintenanceRecord> records = mainService.getRecords();
        assertEquals(records.size(), 2);
        assertSetEquals(records, r -> r.getEmployee().getName(), "Jack", "Mike");
    }

    @Test
    public void getRecords1() {
        MaintenanceSchedule schedule = getScheduleOfDevice1();
        List <MaintenanceRecord> records = mainService.getRecords(schedule.getId());
        assertEquals(records.size(), 1);
        assertSetEquals(records, r -> r.getEmployee().getName(), "Jack");
    }

    @Test
    public void addRecord() {
        MaintenanceSchedule schedule = getScheduleOfDevice1();
        Employee jack = mainService.getEmployees().stream().filter(e -> e.getName().equals("Jack"))
                .findAny().orElse(null);
        assertNotNull(jack);
        Instant now = Instant.now();
        MaintenanceRecord newRecord = new MaintenanceRecord(Date.from(now),
                Date.from(now.plus(Duration.ofMinutes(90))),
                jack, schedule);
        mainService.addRecord(newRecord);
        List<MaintenanceRecord> records = mainService.getRecords(schedule.getId());
        assertEquals(records.size(), 2);
        assertSetEquals(records, r -> r.getEmployee().getName(), "Jack", "Jack");
    }

    @Test
    public void getEmployees() {
        List<Employee> employees = mainService.getEmployees();
        assertEquals(employees.size(), 2);
        assertSetEquals(employees, Employee::getName, "Jack", "Mike");
    }

    @Test
    public void getDevices() {
        List<Device> devices = mainService.getDevices();
        assertEquals(devices.size(), 2);
        assertSetEquals(devices, Device::getName, "Device 1", "Device 2");
    }

    @Test
    public void getSchedules() {
        List<MaintenanceSchedule> schedules = getSchedulesOfDevice1();
        assertEquals(schedules.size(), 2);
        assertSetEquals(schedules, s -> s.getRule().getName(), "Device 1 Big", "Device 1 Small");
    }

    private List<MaintenanceSchedule> getSchedulesOfDevice1() {
        List<Device> devices = mainService.getDevices();
        Device device = devices.stream().filter(d -> d.getName().equals("Device 1")).findAny().orElse(null);
        assertNotNull(device);
        return mainService.getSchedules(device.getId());
    }

    private MaintenanceSchedule getScheduleOfDevice1() {
        List<MaintenanceSchedule> schedules = getSchedulesOfDevice1();
        MaintenanceSchedule schedule = schedules.stream().filter(s -> s.getRule().getName().equals("Device 1 Big"))
                .findAny().orElse(null);
        assertNotNull(schedule);
        return schedule;
    }

    private static <T> void assertSetEquals(List<T> list, Function<T, String> mapper, String... values) {
        assertEquals(list.stream().map(mapper).collect(Collectors.toSet()),
                new HashSet<>(Arrays.asList(values)));
    }
}