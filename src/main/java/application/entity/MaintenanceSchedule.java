package application.entity;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Entity
public class MaintenanceSchedule {
    @Id
    @GeneratedValue
    private Long id;

    private Date firstSchedule;

    @ManyToOne
    @JoinColumn(name = "ruleId")
    private MaintenanceRule rule;

    @OneToMany(mappedBy = "schedule")
    private List<MaintenanceRecord> records;

    public MaintenanceSchedule(Date firstSchedule, MaintenanceRule rule) {
        this.firstSchedule = firstSchedule;
        this.rule = rule;
    }

    public MaintenanceSchedule() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFirstSchedule() {
        return firstSchedule;
    }

    public void setFirstSchedule(Date firstSchedule) {
        this.firstSchedule = firstSchedule;
    }

    public MaintenanceRule getRule() {
        return rule;
    }

    public void setRule(MaintenanceRule rule) {
        this.rule = rule;
    }

    public Date getNextMaintenanceDate() {
        long diffMillis = Date.from(Instant.now()).getTime() - firstSchedule.getTime();
        long days = TimeUnit.MILLISECONDS.toDays(diffMillis);
        int intervalDays = rule.getIntervalDays();
        return calculateDate((days / intervalDays + 1) * intervalDays, firstSchedule);
    }

    public Date getNextMaintenanceDate(Date from) {
        return calculateDate(rule.getIntervalDays(), from);
    }

    private Date calculateDate(long days, Date from) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(from);
        calendar.add(Calendar.DATE, (int) days);
        return calendar.getTime();
    }

    @Override
    public String toString() {
        return "MaintenanceSchedule{" +
                "id=" + id +
                ", firstSchedule=" + firstSchedule +
                ", rule=" + rule +
                ", records=" + records +
                '}';
    }
}
