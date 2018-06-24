package application.entity.util;

import application.SpringBootApplication;
import application.entity.Device;
import application.entity.MaintenanceSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MaintenanceReminderUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(MaintenanceReminderUtil.class);

    public static class MaintenanceReminder implements Comparable<MaintenanceReminder> {
        private MaintenanceSchedule schedule;
        private Date date;

        private MaintenanceReminder(MaintenanceSchedule schedule, Date date) {
            this.schedule = schedule;
            this.date = date;
        }

        @Override
        public int compareTo(MaintenanceReminder o) {
            return date.compareTo(o.date);
        }

        public Device getDevice() {
            return schedule.getRule().getDevice();
        }

        public String getRuleName() {
            return schedule.getRule().getName();
        }

        public Date getDate() {
            return date;
        }

        @Override
        public String toString() {
            return String.format("MaintenanceReminder{date=%s, device=%s, rule=%s}", date,
                    getDevice(), getRuleName());
        }
    }

    private PriorityQueue<MaintenanceReminder> queue = new PriorityQueue<>();
    private List<MaintenanceReminder> reminders = new ArrayList<>();

    public MaintenanceReminderUtil(List<Device> devices) {
        queue.addAll(
                devices.stream()
                        .flatMap(device -> device.getMaintenanceRules().stream())
                        .flatMap(rule -> rule.getSchedules().stream())
                        .map(schedule -> new MaintenanceReminder(schedule, schedule.getNextMaintenanceDate()))
                        .collect(Collectors.toList()));
    }

    private void appendDate() {
        MaintenanceReminder reminder = queue.poll();
        reminders.add(reminder);
        queue.offer(new MaintenanceReminder(reminder.schedule,
                reminder.schedule.getNextMaintenanceDate(reminder.date)));
    }

    public List<MaintenanceReminder> getReminders(int days) {
        Date now = Date.from(Instant.now());
        Date until = Date.from(Instant.now().plus(Duration.ofDays(days)));
        while (reminders.size() != 0 && reminders.get(0).date.before(now))
            reminders.remove(0);
//        for (int i = reminders.size(); i < count; i++)
        while (queue.peek().date.before(until))
            appendDate();
        return Collections.unmodifiableList(reminders);
    }
}
