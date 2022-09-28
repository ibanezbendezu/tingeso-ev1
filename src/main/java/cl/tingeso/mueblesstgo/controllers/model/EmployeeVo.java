package cl.tingeso.mueblesstgo.controllers.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeVo {
    private Long id;
    private String name;
    private String rut;
    private Integer serviceYears;
    private Character category;
    private Long fixedMonthlyWage;
}
