package code.uz.mapper;

import code.uz.dto.ProfileResponseDTO;
import code.uz.entity.ProfileEntity;

public class ProfileMapper {
    public static ProfileResponseDTO toDTO(ProfileEntity entity) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPhone(entity.getPhone());
        dto.setStatus(entity.getStatus());
        dto.setBirthdayDate(entity.getBirthdayDate());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setRole(entity.getRole());
        return dto;
    }
}
