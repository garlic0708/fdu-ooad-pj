package application.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Duration;
import java.util.Date;

@Entity
public class MaintenanceRecord {
    @Id
    @GeneratedValue
    private Long id;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "scheduleId")
    private MaintenanceSchedule schedule;

    public MaintenanceRecord() {
    }

    public MaintenanceRecord(Date startTime, Date endTime, Employee employee, MaintenanceSchedule schedule) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.employee = employee;
        this.schedule = schedule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public MaintenanceSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(MaintenanceSchedule schedule) {
        this.schedule = schedule;
    }

    public long getDurationInMinutes() {
        return Duration.between(startTime.toInstant(), endTime.toInstant()).toMinutes();
    }

    @Override
    public String toString() {
        return "MaintenanceRecord{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", employee=" + employee +
                ", schedule=" + schedule +
                '}';
    }
}
