package com.airfranceklm.fasttrack.assignment.dto;

import com.airfranceklm.fasttrack.assignment.Entity.EmployeeHoliday;
import lombok.Data;

import java.util.Date;

@Data
public class EmployeeHolidayDto {

    private String holidayId;

    private String employeeId;

    private String holidayLabel;

    private Date startOfHoliday;

    private Date endOfHoliday;

    private String status;

    public EmployeeHolidayDto() {}

    public EmployeeHolidayDto(EmployeeHoliday employeeHoliday) {
        this.holidayId = employeeHoliday.getHolidayId();
        this.employeeId = employeeHoliday.getEmployee().getEmployeeId();
        this.holidayLabel = employeeHoliday.getHolidayLabel();
        this.startOfHoliday = employeeHoliday.getStartOfHoliday();
        this.endOfHoliday = employeeHoliday.getEndOfHoliday();
        this.status = employeeHoliday.getStatus().toString();
    }

}
