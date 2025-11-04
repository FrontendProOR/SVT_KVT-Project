package fitpass.fitpass.service;

import fitpass.fitpass.model.entity.Discipline;

import java.util.Set;

public interface DisciplineService {
    Discipline findByName(String name);
    void deleteByFacilityId(Long id);
    void deleteAllByIds(Set<Discipline> disciplines);
    void deleteById(Long id);
}
