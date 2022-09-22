package cl.tingeso.mueblesstgo.services;

import cl.tingeso.mueblesstgo.entities.*;
import cl.tingeso.mueblesstgo.repositories.*;
import cl.tingeso.mueblesstgo.controllers.model.EmployeeVo;
import cl.tingeso.mueblesstgo.controllers.model.WageDetailVo;
import cl.tingeso.mueblesstgo.controllers.model.WageVo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.YearMonth;
import java.util.stream.Collectors;

@Service
public class WageService {

    private final HRMService hrmService;
    private final WageRepository wageRepository;
    private final EmployeeRepository employeeRepository;

    public WageService(HRMService hrmService, WageRepository wageRepository, EmployeeRepository employeeRepository) {
        this.hrmService = hrmService;
        this.wageRepository = wageRepository;
        this.employeeRepository = employeeRepository;
    }

    public WageVo getById(Long id, boolean withEmployee) {

        WageEntity wage = this.wageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró sueldo"));
        
        WageVo vo = new WageVo();
        vo.setId(wage.getId());
        vo.setDate(wage.getDate());
        vo.setDetail(wage.getDetail().stream().map(wd->{
            WageDetailVo detailVo = new WageDetailVo();
            detailVo.setId(wd.getId());
            detailVo.setName(wd.getName());
            detailVo.setType(wd.getType());
            detailVo.setAmount(wd.getAmount());
            return detailVo;
        }).collect(Collectors.toList()));
        if (withEmployee) {
            EmployeeVo employeeVo = new EmployeeVo();
            employeeVo.setId(wage.getEmployee().getId());
            employeeVo.setName(wage.getEmployee().getFirst_names() + " " + wage.getEmployee().getLast_names());
            employeeVo.setRut(wage.getEmployee().getRut());
            employeeVo.setCategory(wage.getEmployee().getCategory().getType());
            employeeVo.setService_years((int) hrmService.serviceYears(wage.getEmployee().getHire_date()));
            vo.setEmployee(employeeVo);
        }
        return vo;
    }

    public Long findByEmployeeIdAndDate(String employeeRut, String month) {
        try {
            YearMonth yearMonth = YearMonth.parse(month);
            Long employeeId = this.employeeRepository.findByRut(employeeRut).orElseThrow().getId();
            return this.wageRepository.findAll().stream()
                    .filter(w -> w.getDate().getMonth() == yearMonth.getMonth())
                    .filter(w -> w.getDate().getYear() == yearMonth.getYear())
                    .filter(w -> w.getEmployee().getId().equals(employeeId))
                    .findFirst().orElseThrow().getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
