package code.uz.repository;

import code.uz.entity.TaskGradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TaskGradeRepository extends JpaRepository<TaskGradeEntity, String> {
    Optional<TaskGradeEntity> findById(String id);
}
