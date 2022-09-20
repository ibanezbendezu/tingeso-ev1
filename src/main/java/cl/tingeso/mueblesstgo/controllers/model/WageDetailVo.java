package cl.tingeso.mueblesstgo.controllers.model;

import cl.tingeso.mueblesstgo.entities.enums.DetailType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WageDetailVo {
    private Long id;
    private String name;
    private DetailType type;
    private BigDecimal amount;
}
