package application.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
public class MaintenanceRule {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @JsonIgnore
    private int intervalDays;

    @ManyToOne
    @JoinColumn(name = "deviceId")
    private Device device;

    @OneToMany(mappedBy = "rule")
    @JsonIgnore
    private List<MaintenanceSchedule> schedules;

    public MaintenanceRule() {
    }

    public MaintenanceRule(String name, int intervalDays, Device device) {
        this.name = name;
        this.intervalDays = intervalDays;
        this.device = device;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIntervalDays() {
        return intervalDays;
    }

    public void setIntervalDays(int intervalDays) {
        this.intervalDays = intervalDays;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public List<MaintenanceSchedule> getSchedules() {
        return Collections.unmodifiableList(schedules);
    }
}
