package cl.tingeso.mueblesstgo;

import cl.tingeso.mueblesstgo.entities.CategoryEntity;
import cl.tingeso.mueblesstgo.entities.EmployeeEntity;
import cl.tingeso.mueblesstgo.services.HRMService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HRMTest {

    HRMService HRM= new HRMService();
    EmployeeEntity employee = new EmployeeEntity();

    @Test
    void serviceYearsTest() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate hire_date = LocalDate.parse("2020/04/22", dateFormatter);

        double serviceYears = HRM.serviceYears(hire_date);
        assertEquals(2.41, serviceYears, 0.0);
    }

    @Test
    void serviceYearsBonusTest() {
        CategoryEntity category = new CategoryEntity();
        category.setType('A');
        category.setFixed_monthly_wage(1300000L);
        employee.setCategory(category);
        employee.setHire_date(LocalDate.of(2020, 4, 22));

        double wage = HRM.serviceYearsBonus(employee);
        assertEquals(0, wage, 0.0);
    }
}
