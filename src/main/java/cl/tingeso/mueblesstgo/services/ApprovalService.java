package cl.tingeso.mueblesstgo.services;

import cl.tingeso.mueblesstgo.entities.EmployeeEntity;
import cl.tingeso.mueblesstgo.entities.ApprovalEntity;
import cl.tingeso.mueblesstgo.repositories.EmployeeRepository;
import cl.tingeso.mueblesstgo.repositories.ApprovalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

@Service
public class ApprovalService {
    @Autowired
    ApprovalRepository approvalRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    private final Logger logg = LoggerFactory.getLogger(ApprovalService.class);

    public void saveApproval(Map request) {
        if (!request.isEmpty()) {
            EmployeeEntity employee = employeeRepository.findByRut(request.get("rut").toString());

            if (Objects.nonNull(employee)) {
                ApprovalEntity newJust = new ApprovalEntity();
                newJust.setEmployee(employee);

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(request.get("date").toString(), dateFormatter);

                newJust.setApproval_date(date);
                newJust.setEmployee_rut(request.get("rut").toString());
                newJust.setDetails(request.get("details").toString());

                approvalRepository.save(newJust);
            }
            logg.info("data saved");
        }
    }
}
