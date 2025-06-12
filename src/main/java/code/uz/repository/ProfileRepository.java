package code.uz.repository;

import code.uz.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {
    Optional<ProfileEntity> findByIdAndVisibleTrue(String id);
    Optional<ProfileEntity> findByPhoneAndVisibleTrue(String phone);
}
