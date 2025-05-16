package com.airfranceklm.fasttrack.assignment.Entity;

import com.airfranceklm.fasttrack.assignment.dto.EmployeeHolidayDto;
import com.airfranceklm.fasttrack.assignment.enums.HolidayStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class EmployeeHoliday {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String holidayId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String holidayLabel;

    private Date startOfHoliday;

    private Date endOfHoliday;

    @Enumerated(EnumType.STRING)
    private HolidayStatus status;

    public EmployeeHoliday(EmployeeHolidayDto holidayRequest, Employee employee) {
        this.holidayId = holidayRequest.getHolidayId();
        this.employee = employee;
        this.holidayLabel = holidayRequest.getHolidayLabel();
        this.startOfHoliday = holidayRequest.getStartOfHoliday();
        this.endOfHoliday = holidayRequest.getEndOfHoliday();
        this.status = HolidayStatus.valueOf(holidayRequest.getStatus());
    }

    public EmployeeHoliday() {

    }
}
