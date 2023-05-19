package com.example.corpu.sessionalStaff;
import com.example.corpu.appuser.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sessional_staff")
public class SessionalStaff {
    @Id
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String rate;
    private String workingDays;
    private Integer unitCount = 0;
    //    @OneToMany
//    List<UnitSessionalStaff> sessionalStaffList;
    @Type(type = "json")
    @Column(name = "availability", columnDefinition = "json")
    private List<String> availability;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id")
    AppUser appUser;

    @Type(type = "json")
    @Column(name = "preference", columnDefinition = "json")
    private List<String> preference;
}
