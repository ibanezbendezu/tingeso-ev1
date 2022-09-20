package cl.tingeso.mueblesstgo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "worked_day")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkedDayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private LocalDate date;
    private LocalTime clock_in;
    private LocalTime clock_out;
    private Double overtime;
    private Long minutes_late;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;
}
