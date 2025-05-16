package com.airfranceklm.fasttrack.assignment.service;

import com.airfranceklm.fasttrack.assignment.Entity.Employee;
import com.airfranceklm.fasttrack.assignment.Entity.EmployeeHoliday;
import com.airfranceklm.fasttrack.assignment.dto.EmployeeHolidayDto;
import com.airfranceklm.fasttrack.assignment.enums.HolidayStatus;
import com.airfranceklm.fasttrack.assignment.exception.EmployeeNotFoundException;
import com.airfranceklm.fasttrack.assignment.exception.HolidayOverlapException;
import com.airfranceklm.fasttrack.assignment.exception.InvalidHolidayStartDateException;
import com.airfranceklm.fasttrack.assignment.repository.EmployeeHolidayRepository;
import com.airfranceklm.fasttrack.assignment.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EmployeeHolidayService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeHolidayRepository employeeHolidayRepository;

    public EmployeeHolidayService(EmployeeRepository employeeRepository, EmployeeHolidayRepository employeeHolidayRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeHolidayRepository = employeeHolidayRepository;
    }

    public Employee getEmployeeById(String employeeId) throws EmployeeNotFoundException {
        return this.employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    public List<Employee> getAllEmployees() {
        Iterable<Employee> employeeIterable = this.employeeRepository.findAll();
        return StreamSupport.stream(employeeIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<EmployeeHoliday> getEmployeeHolidaysByEmployeeId(String employeeId) throws EmployeeNotFoundException {
        return employeeHolidayRepository.getEmployeeHolidayByEmployee(getEmployeeById(employeeId));
    }

    public List<EmployeeHoliday> getAllEmployeeHolidays()   {
        return employeeHolidayRepository.findEmployeeHolidaysByAvailability();
    }

    public EmployeeHoliday saveEmployeeHoliday(EmployeeHolidayDto holidayRequest) throws EmployeeNotFoundException, HolidayOverlapException, InvalidHolidayStartDateException {

        Date today = new Date();
        Employee employee = getEmployeeById(holidayRequest.getEmployeeId());

        EmployeeHoliday employeeHoliday = new EmployeeHoliday(holidayRequest,employee);

        //At least 5 days before the start of the holiday
        if (employeeHoliday.getStartOfHoliday().compareTo(addDaysToDate(today,5)) > 0)
        {
            throw new InvalidHolidayStartDateException("Your holiday was requested less than 5 days before the start date.");
        }

        //No overlap of other crew
        List<EmployeeHoliday> employeeHolidaysOverlap = employeeHolidayRepository.findEmployeeHolidaysByStartOfHolidayBetween(employeeHoliday.getEndOfHoliday(), employeeHoliday.getStartOfHoliday());
        if (!employeeHolidaysOverlap.isEmpty()) {
            throw new HolidayOverlapException("An holiday has already been scheduled/requested in your timeframe.");
        }

        //At least 3 days between holidays.
        List<EmployeeHoliday> employeeHolidaysThreeDaysBefore = employeeHolidayRepository.findEmployeeHolidaysByStartOfHolidayBetween(employeeHoliday.getStartOfHoliday(), addDaysToDate(employeeHoliday.getEndOfHoliday(), -3));
        List<EmployeeHoliday> employeeHolidaysThreeAfter = employeeHolidayRepository.findEmployeeHolidaysByStartOfHolidayBetween(employeeHoliday.getEndOfHoliday(), addDaysToDate(employeeHoliday.getEndOfHoliday(), 3));

        if (!employeeHolidaysThreeDaysBefore.isEmpty()) {
            throw new InvalidHolidayStartDateException("Your holiday that was requested falls in 3 days before an existing holiday.");
        }

        if (!employeeHolidaysThreeAfter.isEmpty()) {
            throw new InvalidHolidayStartDateException("Your holiday that was requested ends in 3 days before an existing holiday.");
        }

        return this.employeeHolidayRepository.save(employeeHoliday);
    }

    public void deleteHoliday(EmployeeHolidayDto holidayRequest) throws EmployeeNotFoundException {
        Employee employee = getEmployeeById(holidayRequest.getEmployeeId());

        EmployeeHoliday employeeHoliday = employeeHolidayRepository.getEmployeeHolidayByHolidayId(holidayRequest.getHolidayId()).orElseThrow(() -> new IllegalArgumentException("Holiday not found"));

        if (!HolidayStatus.isValidForRemoval(employeeHoliday.getStatus()))
        {
            throw new IllegalArgumentException("Holiday is already archived and cannot be deleted.");
        }

        employeeHolidayRepository.delete(employeeHoliday);

    }


    private Date addDaysToDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
}
