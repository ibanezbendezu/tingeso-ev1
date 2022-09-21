package cl.tingeso.mueblesstgo.services;

import cl.tingeso.mueblesstgo.entities.*;
import cl.tingeso.mueblesstgo.entities.enums.DetailType;
import cl.tingeso.mueblesstgo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
public class HRMService {

    @Autowired
    WageRepository wageRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    WorkingDaysRepository workingDaysRepository;
    @Autowired
    WageDetailRepository wageDetailRepository;

    // Calcula descuento según minutos de atraso.
    // > 10: 1%, > 25: 3%, >= 45: 6%
    public double lateDiscount(EmployeeEntity employee) {

        double lateDiscount = 0;

        if (employee.getWorked_days() != null) {

            long fixedMonthlyWage = employee.getCategory().getFixed_monthly_wage();
            for (int i = 0; i < employee.getWorked_days().size(); i++) {
                if (employee.getWorked_days().get(i).getMinutes_late() > 10 &
                        employee.getWorked_days().get(i).getMinutes_late() <= 25) {
                    lateDiscount = lateDiscount + (fixedMonthlyWage * 0.01);
                } else if (employee.getWorked_days().get(i).getMinutes_late() > 25 &
                        employee.getWorked_days().get(i).getMinutes_late() <= 45) {
                    lateDiscount = lateDiscount + (fixedMonthlyWage * 0.03);
                } else if (employee.getWorked_days().get(i).getMinutes_late() > 45) {
                    lateDiscount = lateDiscount + (fixedMonthlyWage * 0.06);
                }
            }
        } else {
            return lateDiscount;
        }

        return lateDiscount;
    }

    // Calcula descuento según dias de inasistencia.
    // 15% de sueldo base por cada día.
    public double absenceDiscount(EmployeeEntity employee, int workingDays) {

        return ( workingDays - employee.getWorked_days().size() ) * (employee.getCategory().getFixed_monthly_wage() * 0.15 );
    }

    // Calcula bonificacion según horas extra.
    // 'A': 25000, 'B': 20000, 'C': 10000
    public double overtimeBonus(EmployeeEntity employee) {

        double overtime = totalOvertime(employee);
        double overtimeBonus;

        if (employee.getCategory().getType() == 'A') {
            overtimeBonus = overtime * 25000;
        } else if (employee.getCategory().getType() == 'B') {
            overtimeBonus = overtime * 20000;
        } else {
            overtimeBonus = overtime * 10000;
        }

        return overtimeBonus;
    }

    // Calcula bonificacion según tiempo de servicio.
    // < 5: 0%, >= 5: 5%, >= 10: 8%, >= 15: 11%, >= 20: 14% >= 25: 17%
    public double serviceYearsBonus(EmployeeEntity employee) {

        double serviceYearsBonus = 0;

        if (serviceYears(employee.getHire_date()) >= 5) {
            serviceYearsBonus = employee.getCategory().getFixed_monthly_wage() * 0.05;
        } else if (serviceYears(employee.getHire_date()) >= 10) {
            serviceYearsBonus = employee.getCategory().getFixed_monthly_wage() * 0.08;
        } else if (serviceYears(employee.getHire_date()) >= 15) {
            serviceYearsBonus = employee.getCategory().getFixed_monthly_wage() * 0.11;
        } else if (serviceYears(employee.getHire_date()) >= 20) {
            serviceYearsBonus = employee.getCategory().getFixed_monthly_wage() * 0.14;
        } else if (serviceYears(employee.getHire_date()) >= 25) {
            serviceYearsBonus = employee.getCategory().getFixed_monthly_wage() * 0.17;
        }

        return serviceYearsBonus;
    }


    public void createWage(EmployeeEntity employee) {

        WageEntity newWage = new WageEntity();

        if (employee.getWorked_days() != null) {
            newWage.setDate(LocalDate.now());
            newWage.setEmployee(employee);
            this.wageRepository.save(newWage);

            WageDetailEntity fixedMonthlyWage = new WageDetailEntity();
            fixedMonthlyWage.setName("FMW");
            fixedMonthlyWage.setWage(newWage);
            fixedMonthlyWage.setType(DetailType.POSITIVE_NI);
            fixedMonthlyWage.setAmount(BigDecimal.valueOf(employee.getCategory().getFixed_monthly_wage()));
            wageDetailRepository.save(fixedMonthlyWage);

            WageDetailEntity serviceYearsBonus = new WageDetailEntity();
            serviceYearsBonus.setName("SY-B");
            serviceYearsBonus.setWage(newWage);
            serviceYearsBonus.setType(DetailType.POSITIVE_NI);
            serviceYearsBonus.setAmount(BigDecimal.valueOf(serviceYearsBonus(employee)));
            wageDetailRepository.save(serviceYearsBonus);

            WageDetailEntity overtimeBonus = new WageDetailEntity();
            overtimeBonus.setName("OT-B");
            overtimeBonus.setWage(newWage);
            overtimeBonus.setType(DetailType.POSITIVE_NI);
            if (employee.getOvertime_approval().stream()
                    .filter(a -> newWage.getDate().getMonth().equals(a.getApproval_date().getMonth()))
                    .findAny()
                    .orElse(null) != null ) {

                overtimeBonus.setAmount(BigDecimal.valueOf(overtimeBonus(employee)));
            } else { overtimeBonus.setAmount(BigDecimal.valueOf(0)); }
            wageDetailRepository.save(overtimeBonus);

            WageDetailEntity lateDiscount = new WageDetailEntity();
            lateDiscount.setName("L-D");
            lateDiscount.setWage(newWage);
            lateDiscount.setType(DetailType.NEGATIVE_NI);
            lateDiscount.setAmount(BigDecimal.valueOf(lateDiscount(employee)));
            wageDetailRepository.save(lateDiscount);

            WageDetailEntity absenceDiscount = new WageDetailEntity();
            absenceDiscount.setName("A-D");
            absenceDiscount.setWage(newWage);
            absenceDiscount.setType(DetailType.NEGATIVE_NI);
            if (employee.getAbsence_justification().stream()
                    .filter(j -> newWage.getDate().getMonth().equals(j.getJustification_date().getMonth()))
                    .findAny()
                    .orElse(null) == null ) {

                WorkingDaysEntity days = workingDaysRepository.findByMonth((newWage.getDate().getMonth().getValue()));
                absenceDiscount.setAmount(BigDecimal.valueOf(absenceDiscount(employee, days.getAmount())));
            } else { absenceDiscount.setAmount(BigDecimal.valueOf(0)); }
            wageDetailRepository.save(absenceDiscount);

            WageDetailEntity grossWage = new WageDetailEntity();
            grossWage.setName("GW");
            grossWage.setWage(newWage);
            grossWage.setType(DetailType.POSITIVE_I);
            BigDecimal bonus = fixedMonthlyWage.getAmount().add(serviceYearsBonus.getAmount());
            BigDecimal discount = ( overtimeBonus.getAmount().add(lateDiscount.getAmount()) ).add(absenceDiscount.getAmount());
            grossWage.setAmount(bonus.subtract(discount));
            wageDetailRepository.save(grossWage);

            WageDetailEntity pensionContribution = new WageDetailEntity();
            pensionContribution.setName("PC");
            pensionContribution.setWage(newWage);
            pensionContribution.setType(DetailType.NEGATIVE_I);
            pensionContribution.setAmount(grossWage.getAmount().multiply(BigDecimal.valueOf(0.1)));
            wageDetailRepository.save(pensionContribution);

            WageDetailEntity healthContribution = new WageDetailEntity();
            healthContribution.setName("HC");
            healthContribution.setWage(newWage);
            healthContribution.setType(DetailType.NEGATIVE_I);
            healthContribution.setAmount(grossWage.getAmount().multiply(BigDecimal.valueOf(0.08)));
            wageDetailRepository.save(healthContribution);

            WageDetailEntity net_pay = new WageDetailEntity();
            net_pay.setName("NP");
            net_pay.setWage(newWage);
            net_pay.setType(DetailType.POSITIVE_I);
            net_pay.setAmount(grossWage.getAmount().subtract(pensionContribution.getAmount().add(healthContribution.getAmount())));
            wageDetailRepository.save(net_pay);
        }
    }

    public void generateWages() {
        this.employeeRepository.findAll().forEach(this::createWage);
    }



    public long serviceYears(LocalDate from) {

        LocalDate to = LocalDate.now();
        Period period = Period.between(from, to);

        return period.getYears() + (period.getMonths() / 12) + (period.getDays() / 365);
    }

    public double totalOvertime(EmployeeEntity employee) {

        double totalOvertime = 0;

        if (employee.getWorked_days() != null) {
            totalOvertime = employee.getWorked_days().stream().mapToDouble(WorkedDayEntity::getOvertime).sum();
        }

        return totalOvertime;
    }
}
