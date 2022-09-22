package cl.tingeso.mueblesstgo.services;

import cl.tingeso.mueblesstgo.entities.JustificationEntity;
import cl.tingeso.mueblesstgo.entities.EmployeeEntity;
import cl.tingeso.mueblesstgo.repositories.JustificationRepository;
import cl.tingeso.mueblesstgo.repositories.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class JustificationService {

    private final JustificationRepository justificationRepository;
    private final EmployeeRepository employeeRepository;

    public JustificationService(JustificationRepository justificationRepository, EmployeeRepository employeeRepository) {
        this.justificationRepository = justificationRepository;
        this.employeeRepository = employeeRepository;
    }

    public JustificationEntity saveJustification(JustificationEntity justification) {
        EmployeeEntity employee = this.employeeRepository.findByRut(justification.getEmployee_rut())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró sueldo"));

        justification.setEmployee(employee);
        justification.setStatus(Boolean.FALSE);
        return  justificationRepository.save(justification);
    }
}
