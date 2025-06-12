package code.uz.service;

import code.uz.dto.*;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public interface ProfileService {
    ProfileResponseDTO register(ProfileRequestDTO requestDTO);
    JwtResponseDTO login(AuthDTO authDTO);
    TokenDTO getAccessNewToken(TokenDTO tokenDTO);
    List<ProfileResponseDTO> getAllProfiles();
    ProfileResponseDTO getProfileById(String id);
    ProfileResponseDTO updateDetailsForAdmin(String id, ProfileRequestDTO requestDTO);
    ProfileResponseDTO updateDetailsForStudent(String id, ProfileRequestDTO requestDTO);
    void deleteProfile(String id);
    void deleteStudent(String id);

}
