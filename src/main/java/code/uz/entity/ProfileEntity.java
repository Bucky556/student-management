package code.uz.entity;

import code.uz.model.GeneralRole;
import code.uz.model.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
;import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "phone")
    private String phone;
    @Column(name = "password")
    private String password;
    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;
    @Column(name = "created_date")
    private LocalDate createdDate = LocalDate.now();
    @Column(name = "birthday_date")
    private LocalDate birthdayDate;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private GeneralRole role = GeneralRole.ROLE_STUDENT;


    ///  profile-ni uchirganda unga tegishli bulgan taskni ham uchirib yubirsh uchun
    @OneToMany(mappedBy = "profile", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TaskEntity> taskList;
    // orphanRemoval=true - bu agar parent va child bir-biridan uzilsa bazadan ham uchirib yuboriladi degani.
}
