package code.uz.mapper;

import code.uz.dto.TaskResponseDTO;
import code.uz.entity.TaskEntity;

public class TaskMapper {

    public static TaskResponseDTO toDTO(TaskEntity entity) {
        TaskResponseDTO responseDTO = new TaskResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setTitle(entity.getTitle());
        responseDTO.setContent(entity.getContent());
        responseDTO.setCreatedBy(entity.getProfile().getName());
        responseDTO.setCreatedDate(entity.getCreatedDate());
        return responseDTO;
    }
}
