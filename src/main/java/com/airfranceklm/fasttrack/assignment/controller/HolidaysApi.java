package com.airfranceklm.fasttrack.assignment.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.airfranceklm.fasttrack.assignment.Entity.Employee;
import com.airfranceklm.fasttrack.assignment.Entity.EmployeeHoliday;
import com.airfranceklm.fasttrack.assignment.dto.EmployeeDto;
import com.airfranceklm.fasttrack.assignment.dto.EmployeeHolidayDto;
import com.airfranceklm.fasttrack.assignment.exception.EmployeeNotFoundException;
import com.airfranceklm.fasttrack.assignment.exception.HolidayOverlapException;
import com.airfranceklm.fasttrack.assignment.exception.InvalidHolidayStartDateException;
import com.airfranceklm.fasttrack.assignment.service.EmployeeHolidayService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.airfranceklm.fasttrack.assignment.resources.Holiday;

@Controller
@RequestMapping("/holidays")
public class HolidaysApi {

    @Autowired
    private EmployeeHolidayService employeeHolidayService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        List<Employee> allEmployees = employeeHolidayService.getAllEmployees();
        List<EmployeeDto> list = allEmployees.stream().map(EmployeeDto::new).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/{employeeId}",method = RequestMethod.GET)
    public ResponseEntity<List<EmployeeHolidayDto>> getEmployeeHolidays(@PathVariable String employeeId) throws EmployeeNotFoundException {
        List<EmployeeHoliday> allEmployeeHolidays = employeeHolidayService.getEmployeeHolidaysByEmployeeId(employeeId);
        List<EmployeeHolidayDto> list = allEmployeeHolidays.stream().map(EmployeeHolidayDto::new).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @RequestMapping(value = "/{employeeId}",method = RequestMethod.POST)
    public ResponseEntity<List<EmployeeHolidayDto>> saveEmployeeHolidays(@PathVariable String employeeId, EmployeeHolidayDto employeeHolidayDto) throws EmployeeNotFoundException, HolidayOverlapException, InvalidHolidayStartDateException {
        employeeHolidayService.saveEmployeeHoliday(employeeHolidayDto);
        List<EmployeeHoliday> allEmployeeHolidays = employeeHolidayService.getEmployeeHolidaysByEmployeeId(employeeId);
        List<EmployeeHolidayDto> list = allEmployeeHolidays.stream().map(EmployeeHolidayDto::new).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @RequestMapping(value = "/{employeeId}",method = RequestMethod.DELETE)
    public ResponseEntity<List<EmployeeHolidayDto>> deleteEmployeeHolidays(@PathVariable String employeeId, EmployeeHolidayDto employeeHolidayDto) throws EmployeeNotFoundException, HolidayOverlapException, InvalidHolidayStartDateException {
        employeeHolidayService.deleteHoliday(employeeHolidayDto);
        List<EmployeeHoliday> allEmployeeHolidays = employeeHolidayService.getEmployeeHolidaysByEmployeeId(employeeId);
        List<EmployeeHolidayDto> list = allEmployeeHolidays.stream().map(EmployeeHolidayDto::new).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
