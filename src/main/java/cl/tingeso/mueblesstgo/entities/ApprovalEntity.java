package cl.tingeso.mueblesstgo.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "overtime_approval")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ApprovalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate approvalDate;

    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;
}
