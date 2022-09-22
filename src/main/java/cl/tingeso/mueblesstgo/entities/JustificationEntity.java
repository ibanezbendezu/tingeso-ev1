package cl.tingeso.mueblesstgo.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "absence_justification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class JustificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate justification_date;

    private String details;
    private Boolean status;
    private String employee_rut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;
}
