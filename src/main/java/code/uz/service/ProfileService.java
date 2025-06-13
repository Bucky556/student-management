package code.uz.service;

import code.uz.dto.*;

import java.util.List;

public interface ProfileService {
    ResponseDTO<ProfileResponseDTO> register(ProfileRequestDTO requestDTO);
    ResponseDTO<JwtResponseDTO> login(AuthDTO authDTO);
    TokenDTO getAccessNewToken(TokenDTO tokenDTO);
    ResponseDTO<List<ProfileResponseDTO>> getAllProfiles();
    ResponseDTO<ProfileResponseDTO> getProfileById(String id);
    ResponseDTO<ProfileResponseDTO> updateDetailsForAdmin(String id, ProfileRequestDTO requestDTO);
    ResponseDTO<ProfileResponseDTO> updateDetailsForStudent(String id, ProfileRequestDTO requestDTO);
    void deleteProfile(String id);
    void deleteStudent(String id);

}
