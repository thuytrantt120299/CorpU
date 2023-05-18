package com.example.corpu.permanentStaff;
import com.example.corpu.appuser.AppUser;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Data
@Table(name="permanent_staff")
public class PermanentStaff {
    @Id
    private String id;

    @OneToOne
    private AppUser appUser;

}
