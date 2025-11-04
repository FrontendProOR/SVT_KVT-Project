package fitpass.fitpass.service.implementation;

import fitpass.fitpass.model.entity.WorkDay;
import fitpass.fitpass.repository.WorkDayRepository;
import fitpass.fitpass.service.WorkDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WorkDayServiceImpl implements WorkDayService {
    @Autowired
    private WorkDayRepository workDayRepository;

    @Override
    public void deleteAllById(Set<WorkDay> workDays) {
        for(WorkDay w:workDays){
            deleteById(w.getId());
        }
    }

    @Override
    public void deleteById(Long id) {
        workDayRepository.deleteById(id);
    }


}