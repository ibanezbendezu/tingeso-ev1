package cl.tingeso.mueblesstgo.controllers.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmployeeVo {
    private Long id;
    private String name;
    private String rut;
    private Integer service_years;
    private Character category;
    private Long fixed_monthly_wage;
}
