package cl.tingeso.mueblesstgo.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rut;
    private String last_names;
    private String first_names;
    private LocalDate birth_date;
    private LocalDate hire_date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<WorkedDayEntity> worked_days;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<JustificationEntity> absence_justification;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<ApprovalEntity> overtime_approval;
}
