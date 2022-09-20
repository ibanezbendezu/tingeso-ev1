package cl.tingeso.mueblesstgo.entities;

import cl.tingeso.mueblesstgo.entities.enums.DetailType;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wage_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class WageDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private DetailType type;

    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wage_id", nullable = false)
    private WageEntity wage;
}