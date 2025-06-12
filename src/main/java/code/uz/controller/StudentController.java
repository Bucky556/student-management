package code.uz.controller;


import code.uz.dto.*;
import code.uz.group_interface.OnCreate;
import code.uz.group_interface.OnUpdate;
import code.uz.service.ProfileService;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final ProfileService profileService;

    public StudentController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/register")
    public ResponseEntity<ProfileResponseDTO> registerProfile(@Validated(OnCreate.class) @RequestBody ProfileRequestDTO profileRequestDTO) {
        return ResponseEntity.ok(profileService.register(profileRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> authorization(@RequestBody AuthDTO dto) {
        return ResponseEntity.ok(profileService.login(dto));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody TokenDTO dto) {
        return ResponseEntity.ok(profileService.getAccessNewToken(dto));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable("id") String id, @Validated(OnUpdate.class) @RequestBody ProfileRequestDTO profileRequestDTO) {
        return ResponseEntity.ok(profileService.updateDetailsForStudent(id, profileRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<Boolean> deleteProfileById(@PathVariable("id") String id) {
        profileService.deleteProfile(id);
        return ResponseEntity.ok(true);
    }
}
