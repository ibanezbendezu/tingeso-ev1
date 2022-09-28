package cl.tingeso.mueblesstgo.services;

import cl.tingeso.mueblesstgo.entities.ApprovalEntity;
import cl.tingeso.mueblesstgo.entities.EmployeeEntity;
import cl.tingeso.mueblesstgo.repositories.ApprovalRepository;
import cl.tingeso.mueblesstgo.repositories.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final EmployeeRepository employeeRepository;

    public ApprovalService(ApprovalRepository approvalRepository, EmployeeRepository employeeRepository) {
        this.approvalRepository = approvalRepository;
        this.employeeRepository = employeeRepository;
    }

    public ApprovalEntity saveApproval(ApprovalEntity approval, String rut) {
        EmployeeEntity employee = this.employeeRepository.findByRut(rut)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró sueldo"));

        approval.setEmployee(employee);
        return  approvalRepository.save(approval);
    }
}
