package code.uz.service.Impl;

import code.uz.config.CustomUserDetails;
import code.uz.dto.*;
import code.uz.entity.ProfileEntity;
import code.uz.exception.CustomException;
import code.uz.mapper.ProfileMapper;
import code.uz.model.Status;
import code.uz.repository.ProfileRepository;
import code.uz.service.ProfileService;
import code.uz.util.SecurityUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ProfileServiceImpl(ProfileRepository profileRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public ResponseDTO<ProfileResponseDTO> register(ProfileRequestDTO requestDTO) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(requestDTO.getPhone());
        if (optional.isPresent()) {
            throw new CustomException("This phone is profile already exists");
        }
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(requestDTO.getName());
        profileEntity.setSurname(requestDTO.getSurname());
        profileEntity.setPhone(requestDTO.getPhone());
        profileEntity.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        profileEntity.setBirthdayDate(requestDTO.getBirthdayDate());

        profileRepository.save(profileEntity);
        return ProfileMapper.toDTO(profileEntity);
    }

    public ResponseDTO<JwtResponseDTO> login(AuthDTO authDTO) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getPhone(), authDTO.getPassword()));

            if (authenticate.isAuthenticated()) {
                CustomUserDetails profile = (CustomUserDetails) authenticate.getPrincipal();

                JwtResponseDTO response = new JwtResponseDTO();
                response.setName(profile.getName());
                response.setSurname(profile.getSurname());
                response.setPhone(profile.getPhone());
                response.setRole(profile.getRole());
                response.setAccessToken(code.uz.utils.JwtUtil.encode(profile.getPhone(), profile.getRole().name()));
                response.setRefreshToken(code.uz.utils.JwtUtil.refreshToken(profile.getPhone(), profile.getRole().name()));

                return ResponseDTO.ok(response);
            }
        } catch (BadCredentialsException e) {
            throw new CustomException("Invalid phone or password");
        }
        throw new CustomException("Invalid phone or password");
    }

    public TokenDTO getAccessNewToken(TokenDTO tokenDTO) {
        try {
            JwtDTO jwtDTO = code.uz.utils.JwtUtil.decode(tokenDTO.getRefreshToken());

            Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(jwtDTO.getPhone());
            if (optional.isPresent() && optional.get().getStatus().equals(Status.ACTIVE)) {
                ProfileEntity profileEntity = optional.get();

                TokenDTO dto = new TokenDTO();
                dto.setAccessToken(code.uz.utils.JwtUtil.encode(profileEntity.getPhone(), profileEntity.getRole().name()));
                dto.setRefreshToken(code.uz.utils.JwtUtil.refreshToken(profileEntity.getPhone(), profileEntity.getRole().name()));
                return dto;
            }

        } catch (JwtException e) {
            throw new JwtException("Invalid token");
        }
        throw new CustomException("Invalid token");
    }

    public ResponseDTO<List<ProfileResponseDTO>> getAllProfiles() {
        List<ProfileEntity> all = profileRepository.findAll();
        List<ProfileResponseDTO> list = new ArrayList<>();
        for (ProfileEntity entity : all) {
            ProfileResponseDTO dto = ProfileMapper.toDTO(entity).getData();
            list.add(dto);
        }
        return ResponseDTO.ok(list);
    }


    public ResponseDTO<ProfileResponseDTO> getProfileById(String id) {
        Optional<ProfileEntity> byId = profileRepository.findById(id);
        if (byId.isPresent()) {
            return ProfileMapper.toDTO(byId.get());
        }
        throw new CustomException("Profile not found");
    }

    public void deleteProfile(String id) {
        Optional<ProfileEntity> byId = profileRepository.findById(id);
        if (byId.isEmpty()) {
            throw new CustomException("Profile not found");
        }
        String profileId = SecurityUtil.getID();
        if (profileId.equals(id)) {
            ProfileEntity entity = byId.get();
            entity.setVisible(Boolean.FALSE);
            profileRepository.save(entity);
            return;
        }
        throw new CustomException("Profile does not belong to this");
    }

    public void deleteStudent(String id) {
        Optional<ProfileEntity> byId = profileRepository.findById(id);
        if (byId.isEmpty()) {
            throw new CustomException("Profile not found");
        }
        ProfileEntity entity = byId.get();
        profileRepository.delete(entity);
    }

    public ResponseDTO<ProfileResponseDTO> updateDetailsForAdmin(String id, ProfileRequestDTO requestDTO) {
        Optional<ProfileEntity> byId = profileRepository.findById(id);
        if (byId.isEmpty()) {
            throw new CustomException("Profile not found");
        }
        ProfileEntity profileEntity = byId.get();
        if (requestDTO.getName() != null) {
            profileEntity.setName(requestDTO.getName());
        }
        if (requestDTO.getSurname() != null) {
            profileEntity.setSurname(requestDTO.getSurname());
        }
        if (requestDTO.getPhone() != null) {
            profileEntity.setPhone(requestDTO.getPhone());
        }
        if (requestDTO.getPassword() != null) {
            profileEntity.setPassword(requestDTO.getPassword());
        }
        if (requestDTO.getBirthdayDate() != null) {
            profileEntity.setBirthdayDate(requestDTO.getBirthdayDate());
        }
        profileRepository.save(profileEntity);
        return ProfileMapper.toDTO(profileEntity);
    }

    public ResponseDTO<ProfileResponseDTO> updateDetailsForStudent(String id, ProfileRequestDTO requestDTO) {
        Optional<ProfileEntity> byId = profileRepository.findByIdAndVisibleTrue(id);
        if (byId.isEmpty()) {
            throw new CustomException("Profile not found");
        }
        String profileId = SecurityUtil.getID();
        if (profileId.equals(id)) {
            ProfileEntity profileEntity = byId.get();
            if (requestDTO.getName() != null) {
                profileEntity.setName(requestDTO.getName());
            }
            if (requestDTO.getSurname() != null) {
                profileEntity.setSurname(requestDTO.getSurname());
            }
            if (requestDTO.getPhone() != null) {
                profileEntity.setPhone(requestDTO.getPhone());
            }
            if (requestDTO.getPassword() != null) {
                profileEntity.setPassword(requestDTO.getPassword());
            }
            if (requestDTO.getBirthdayDate() != null) {
                profileEntity.setBirthdayDate(requestDTO.getBirthdayDate());
            }
            profileRepository.save(profileEntity);
            return ProfileMapper.toDTO(profileEntity);
        }
        throw new CustomException("Profile does not belong to this");
    }
}
