package code.uz.repository;

import code.uz.dto.ProfileFilterDTO;
import code.uz.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProfileFilterRepository {
    private final EntityManager entityManager;

    public PageImpl<ProfileEntity> filter(ProfileFilterDTO profileFilterDTO, int page, int size) {
        StringBuilder baseQuery = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (profileFilterDTO.getName() != null) {
            baseQuery.append(" and lower(p.name) like :name ");
            params.put("name", "%" + profileFilterDTO.getName().toLowerCase() + "%");
        }
        if (profileFilterDTO.getSurname() != null) {
            baseQuery.append(" and lower(p.surname) like :surname ");
            params.put("surname", "%" + profileFilterDTO.getSurname().toLowerCase() + "%");
        }
        if (profileFilterDTO.getPhone() != null) {
            baseQuery.append(" and p.phone = :phone ");
        }
        if (profileFilterDTO.getBirthdayDateFrom() != null && profileFilterDTO.getBirthdayDateTo() != null) {
            baseQuery.append(" and p.birthdayDate between :birthdayDateFrom and :birthdayDateTo ");
            params.put("birthdayDateFrom", profileFilterDTO.getBirthdayDateFrom());
            params.put("birthdayDateTo", profileFilterDTO.getBirthdayDateTo());
        } else if (profileFilterDTO.getBirthdayDateFrom() != null) {
            baseQuery.append(" and p.birthdayDate >= :birthdayDateFrom ");
            params.put("birthdayDateFrom", profileFilterDTO.getBirthdayDateFrom());
        } else if (profileFilterDTO.getBirthdayDateTo() != null) {
            baseQuery.append(" and p.birthdayDate <= :birthdayDateTo ");
            params.put("birthdayDateTo", profileFilterDTO.getBirthdayDateTo());
        }

        String whereClause = baseQuery.toString();
        String order = " order by p.createdDate desc ";

        Query selectQuery = entityManager.createQuery("select p from ProfileEntity p where p.visible = true " + whereClause + order, ProfileEntity.class);
        params.forEach(selectQuery::setParameter);
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);

        List<ProfileEntity> profileEntities = selectQuery.getResultList();

        Query countQuery = entityManager.createQuery("select count(p) from ProfileEntity p where p.visible = true " + whereClause);
        params.forEach(countQuery::setParameter);

        Long totalElements = (Long) countQuery.getSingleResult();

        PageRequest pageRequest = PageRequest.of(page, size);

        return new PageImpl<>(profileEntities, pageRequest, totalElements);
    }
}
