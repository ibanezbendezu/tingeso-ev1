package cl.tingeso.mueblesstgo.controllers.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class WageVo {
    private Long id;
    private LocalDate date;
    private List<WageDetailVo> detail;
    private EmployeeVo employee;
}
