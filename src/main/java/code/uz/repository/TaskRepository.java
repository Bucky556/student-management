package code.uz.repository;

import code.uz.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TaskRepository extends JpaRepository<TaskEntity, String> {
    List<TaskEntity> findAllByProfileId(String profileId);
}
