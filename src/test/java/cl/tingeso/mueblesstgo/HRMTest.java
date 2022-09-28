package cl.tingeso.mueblesstgo;

import cl.tingeso.mueblesstgo.entities.CategoryEntity;
import cl.tingeso.mueblesstgo.entities.EmployeeEntity;
import cl.tingeso.mueblesstgo.entities.WorkedDayEntity;
import cl.tingeso.mueblesstgo.services.HRMService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HRMTest {

    HRMService HRM= new HRMService();
    EmployeeEntity employee = new EmployeeEntity();
    CategoryEntity category = new CategoryEntity();

    @Test
    void lateDiscountTest() {
        category.setType('A');
        category.setFixedMonthlyWage(1300000L);
        employee.setCategory(category);

        List<WorkedDayEntity> wds = new ArrayList<>();
        WorkedDayEntity wd1 = new WorkedDayEntity();
        wd1.setDate(LocalDate.of(2022, 9, 27));
        wd1.setMinutesLate(20L);
        wds.add(wd1);
        WorkedDayEntity wd2 = new WorkedDayEntity();
        wd2.setDate(LocalDate.of(2022, 9, 28));
        wd2.setMinutesLate(5L);
        wds.add(wd2);

        employee.setWorkedDays(wds);
        YearMonth yearMonth = YearMonth.of(2022, 9);

        double lateDiscount = HRM.lateDiscount(employee, yearMonth);
        assertEquals(13000, lateDiscount, 0.0);
    }

    @Test
    void totalAbsencesTest() {
        List<WorkedDayEntity> wds = new ArrayList<>();
        WorkedDayEntity wd1 = new WorkedDayEntity();
        wd1.setDate(LocalDate.of(2022, 9, 27));
        wds.add(wd1);
        WorkedDayEntity wd2 = new WorkedDayEntity();
        wd2.setDate(LocalDate.of(2022, 9, 28));
        wds.add(wd2);

        employee.setWorkedDays(wds);
        YearMonth yearMonth = YearMonth.of(2022, 9);

        double totalAbsences = HRM.totalAbsences(employee, 4, yearMonth);
        assertEquals(2, totalAbsences, 0.0);
    }

    @Test
    void absenceDiscountTest() {
        category.setType('A');
        category.setFixedMonthlyWage(1300000L);
        employee.setCategory(category);
        long totalAbsences = 2;

        double absenceDiscount = HRM.absenceDiscount(employee, totalAbsences);
        assertEquals(390000, absenceDiscount, 0.0);
    }

    @Test
    void totalOvertimeTest() {
        List<WorkedDayEntity> wds = new ArrayList<>();
        WorkedDayEntity wd1 = new WorkedDayEntity();
        wd1.setDate(LocalDate.of(2022, 9, 27));
        wd1.setOvertime(0.5);
        wds.add(wd1);
        WorkedDayEntity wd2 = new WorkedDayEntity();
        wd2.setDate(LocalDate.of(2022, 9, 28));
        wd2.setOvertime(0.1);
        wds.add(wd2);

        employee.setWorkedDays(wds);
        YearMonth yearMonth = YearMonth.of(2022, 9);

        double totalOvertime = HRM.totalOvertime(employee, yearMonth);
        assertEquals(0.6, totalOvertime, 0.0);
    }

    @Test
    void overtimeBonusTest() {
        category.setType('A');
        employee.setCategory(category);
        double totalOvertime = 0.6;

        double overtimeBonus = HRM.overtimeBonus(employee, totalOvertime);
        assertEquals(15000, overtimeBonus, 0.0);
    }

    @Test
    void serviceYearsTest() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate hire_date = LocalDate.parse("2020/04/22", dateFormatter);
        employee.setHireDate(hire_date);

        double serviceYears = HRM.serviceYears(employee);
        assertEquals(2, serviceYears, 0.0);
    }

    @Test
    void serviceYearsBonusTest() {
        CategoryEntity category = new CategoryEntity();
        category.setType('A');
        category.setFixedMonthlyWage(1300000L);
        employee.setCategory(category);
        employee.setHireDate(LocalDate.of(2020, 4, 22));

        double serviceYearsBonus = HRM.serviceYearsBonus(employee, 2L);
        assertEquals(0, serviceYearsBonus, 0.0);
    }
}
