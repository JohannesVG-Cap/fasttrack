package com.airfranceklm.fasttrack.assignment.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {

    @Id
    @Column(unique = true, nullable = false)
    private String employeeId;

    private String name;

}
