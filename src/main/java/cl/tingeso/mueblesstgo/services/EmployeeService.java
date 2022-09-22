package cl.tingeso.mueblesstgo.services;

import cl.tingeso.mueblesstgo.entities.EmployeeEntity;
import cl.tingeso.mueblesstgo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeEntity obtenerPorRut(String rut){
        return employeeRepository.findByRut(rut)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró sueldo")); }


}
