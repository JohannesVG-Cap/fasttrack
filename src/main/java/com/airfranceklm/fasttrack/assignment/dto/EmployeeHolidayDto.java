package com.airfranceklm.fasttrack.assignment.dto;

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

}
