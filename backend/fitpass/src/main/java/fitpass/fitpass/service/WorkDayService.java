package fitpass.fitpass.service;

import fitpass.fitpass.model.entity.WorkDay;

import java.util.Set;

public interface WorkDayService {
    void deleteAllById(Set<WorkDay> workDays);
    void deleteById(Long id);

}
