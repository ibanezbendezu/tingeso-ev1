package cl.tingeso.mueblesstgo.services;

import cl.tingeso.mueblesstgo.entities.EmployeeEntity;
import cl.tingeso.mueblesstgo.entities.WorkedDayEntity;
import cl.tingeso.mueblesstgo.repositories.EmployeeRepository;
import cl.tingeso.mueblesstgo.repositories.WorkedDayRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.HashMap;
import java.util.Objects;


@Service
public class ClockService {
    private final static DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private final static DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm");
    private final static LocalTime ENTRY_TIME = LocalTime.parse("08:00");
    private final static LocalTime MAX_ENTRY_TIME_ACCEPTED = LocalTime.parse("09:10");
    private final static LocalTime MAX_REGULAR_WORKING_TIME = LocalTime.parse("18:00");

    private final WorkedDayRepository workedDayRepository;
    private final EmployeeRepository employeeRepository;

    public ClockService(WorkedDayRepository workedDayRepository, EmployeeRepository employeeRepository) {
        this.workedDayRepository = workedDayRepository;
        this.employeeRepository = employeeRepository;
    }

    public boolean loadClock(MultipartFile multipartFile) {

        if (!multipartFile.isEmpty() && Objects.equals(multipartFile.getOriginalFilename(), "DATA.txt")) {

            try {
                HashMap<String, WorkedDayEntity> workedDaysByRut = new HashMap<>();

                InputStream inputStream = multipartFile.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

                String line;
                while ((line = br.readLine()) != null) {
                    String[] mark = line.split(";");

                    EmployeeEntity employee = employeeRepository.findByRut(mark[2])
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontr?? empleado"));

                    if (Objects.nonNull(employee)) {
                        String rut = mark[2];
                        LocalDate date = LocalDate.parse(mark[0], DF);
                        String key = rut + "_" + date.getDayOfMonth();
                        LocalTime time = LocalTime.parse(mark[1], TF);

                        if (!workedDaysByRut.containsKey(key))
                            workedDaysByRut.put(key, new WorkedDayEntity());

                        WorkedDayEntity workedDay = workedDaysByRut.get(key);
                        workedDay.setEmployee(employee);
                        workedDay.setDate(date);
                        if (workedDay.getClockIn() == null) {
                            workedDay.setClockIn(time);
                        } else {
                            if (time.compareTo(workedDay.getClockIn()) < 0) {
                                LocalTime aux = workedDay.getClockIn();
                                workedDay.setClockIn(time);
                                workedDay.setClockOut(aux);
                            } else {
                                workedDay.setClockOut(time);
                            }
                        }
                    }

                    workedDaysByRut.values().stream()
                            .filter(d -> d.getClockOut() != null)
                            .filter(d -> d.getClockIn().compareTo(MAX_ENTRY_TIME_ACCEPTED) <= 0)
                            .forEach(d -> {
                                if (d.getClockOut().compareTo(MAX_REGULAR_WORKING_TIME) > 0) {
                                    d.setOvertime(MINUTES.between(MAX_REGULAR_WORKING_TIME, d.getClockOut()) / 60.0);
                                } else {
                                    d.setOvertime(0.0);
                                }

                                if (d.getClockIn().compareTo(ENTRY_TIME) > 0) {
                                    d.setMinutesLate(MINUTES.between(ENTRY_TIME, d.getClockIn()));
                                } else {
                                    d.setMinutesLate(0L);
                                }

                                workedDayRepository.save(d);
                            });
                }
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }
}
