package cl.tingeso.mueblesstgo.services;

import cl.tingeso.mueblesstgo.entities.EmployeeEntity;
import cl.tingeso.mueblesstgo.entities.JustificationEntity;
import cl.tingeso.mueblesstgo.repositories.EmployeeRepository;
import cl.tingeso.mueblesstgo.repositories.JustificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

@Service
public class JustificationService {
    @Autowired
    JustificationRepository justificationRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    private final Logger logg = LoggerFactory.getLogger(JustificationService.class);

    public void saveJustification(Map request) {
        if (!request.isEmpty()) {
            EmployeeEntity employee = employeeRepository.findByRut(request.get("rut").toString());

            if (Objects.nonNull(employee)) {
                JustificationEntity newJust = new JustificationEntity();
                newJust.setEmployee(employee);

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(request.get("date").toString(), dateFormatter);

                newJust.setJustification_date(date);
                newJust.setEmployee_rut(request.get("rut").toString());
                newJust.setDetails(request.get("details").toString());

                justificationRepository.save(newJust);
            }
            logg.info("data saved");
        }
    }
}
