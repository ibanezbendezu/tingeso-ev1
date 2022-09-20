package cl.tingeso.mueblesstgo.services;

import cl.tingeso.mueblesstgo.entities.EmployeeEntity;
import cl.tingeso.mueblesstgo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public ArrayList<EmployeeEntity> obtenerEmpleados(){
        return (ArrayList<EmployeeEntity>) employeeRepository.findAll();
    }

    public EmployeeEntity guardarEmpleado(EmployeeEntity employee){
        return employeeRepository.save(employee);
    }

    public Optional<EmployeeEntity> obtenerPorId(Long id){
        return employeeRepository.findById(id);
    }

    public EmployeeEntity obtenerPorRut(String rut){ return employeeRepository.findByRut(rut); }

    public boolean eliminarEmpleado(Long id) {
        try{
            employeeRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }
}
