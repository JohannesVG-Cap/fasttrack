package com.airfranceklm.fasttrack.assignment.repository;

import com.airfranceklm.fasttrack.assignment.Entity.Employee;
import com.airfranceklm.fasttrack.assignment.Entity.EmployeeHoliday;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeHolidayRepository extends CrudRepository<EmployeeHoliday, String> {

    @Query(nativeQuery = true,
    value = """
        SELECT * FROM EmployeeHoliday 
        where status in ("DRAFT","REQUESTED","SCHEDULED")
""")
    List<EmployeeHoliday> findEmployeeHolidaysByAvailability();

    List<EmployeeHoliday> findEmployeeHolidaysByStartOfHolidayBetween(Date startOfHolidayAfter, Date startOfHolidayBefore);

    List<EmployeeHoliday> getEmployeeHolidayByEmployee(Employee employee);

    Optional<EmployeeHoliday> getEmployeeHolidayByHolidayId(String holidayId);
}
