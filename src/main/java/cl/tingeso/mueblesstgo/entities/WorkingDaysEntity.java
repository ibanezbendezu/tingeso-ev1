package cl.tingeso.mueblesstgo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "working_days")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkingDaysEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private int year;
    private int month;
    private int amount;
}
