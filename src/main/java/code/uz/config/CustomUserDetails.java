package code.uz.config;

import code.uz.entity.ProfileEntity;
import code.uz.model.GeneralRole;
import code.uz.model.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {
    private final Status status;
    private final String phone;
    private final String password;
    private final GeneralRole role;
    private final String id;
    private final String name;
    private final String surname;
    private final LocalDate birthday;

    public CustomUserDetails(ProfileEntity profileEntity) {
        this.name = profileEntity.getName();
        this.surname = profileEntity.getSurname();
        this.status = profileEntity.getStatus();
        this.phone = profileEntity.getPhone();
        this.password = profileEntity.getPassword();
        this.id = profileEntity.getId();
        this.role = profileEntity.getRole();
        this.birthday = profileEntity.getBirthdayDate();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(Status.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
