package code.uz.controller;


import code.uz.dto.ProfileRequestDTO;
import code.uz.dto.ProfileResponseDTO;
import code.uz.dto.ResponseDTO;
import code.uz.group_interface.OnUpdate;
import code.uz.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ProfileService profileService;

    public AdminController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<List<ProfileResponseDTO>>> getAllProfiles() {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    @GetMapping("/byId/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<ProfileResponseDTO>> getProfileById(@PathVariable("id") String id) {
        return ResponseEntity.ok(profileService.getProfileById(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<ProfileResponseDTO>> updateProfile(@PathVariable("id") String id, @Validated(OnUpdate.class) @RequestBody ProfileRequestDTO profileRequestDTO) {
        return ResponseEntity.ok(profileService.updateDetailsForAdmin(id, profileRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> deleteProfileById(@PathVariable("id") String id) {
        profileService.deleteStudent(id);
        return ResponseEntity.ok(true);
    }
}
