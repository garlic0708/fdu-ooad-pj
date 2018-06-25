package application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Device {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @JsonIgnore
    private String type;
    @JsonIgnore
    private String modelNumber;
    @JsonIgnore
    private String location;

    @OneToMany(mappedBy = "device")
    @JsonIgnore
    private List<MaintenanceRule> maintenanceRules;

    public Device() {
    }

    public Device(String name, String type, String modelNumber, String location) {
        this.name = name;
        this.type = type;
        this.modelNumber = modelNumber;
        this.location = location;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addRule(MaintenanceRule rule) {
        maintenanceRules.add(rule);
    }

    public List<MaintenanceRule> getMaintenanceRules() {
        return Collections.unmodifiableList(maintenanceRules);
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", modelNumber='" + modelNumber + '\'' +
                ", location='" + location + '\'' +
                ", maintenanceRules=" + maintenanceRules +
                '}';
    }
}
