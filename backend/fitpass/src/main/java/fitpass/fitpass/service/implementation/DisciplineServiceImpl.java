package fitpass.fitpass.service.implementation;

import fitpass.fitpass.model.entity.Discipline;
import fitpass.fitpass.repository.DisciplineRepository;
import fitpass.fitpass.service.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DisciplineServiceImpl implements DisciplineService {
    @Autowired
    DisciplineRepository disciplineRepository;
    @Override
    public Discipline findByName(String name) {
        return this.disciplineRepository.findByName(name);
    }

    @Override
    public void deleteByFacilityId(Long id) {
        disciplineRepository.deleteByFacilityId(id);
    }

    @Override
    public void deleteAllByIds(Set<Discipline> disciplines) {
        for(Discipline d:disciplines){
            deleteById(d.getId());
        }
    }

    @Override
    public void deleteById(Long id) {
        disciplineRepository.deleteById(id);
    }
}
