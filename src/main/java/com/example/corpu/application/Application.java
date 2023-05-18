package com.example.corpu.application;

import com.example.corpu.sessionalStaff.SessionalStaff;
import com.example.corpu.unit.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="application")
public class Application {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "sessional_staff_id", updatable = false)
    private SessionalStaff sessionalStaff;
    @ManyToOne
    @JoinColumn(name = "unit_id", updatable = false)
    private Unit unit;
    @Column(name = "application_date")
    private Instant applicationDate;
    @Column(name="applicant_email")
    String applicantEmail;
    @Column(name="unit_name")
    String unitName;
    //Status = 0 - pending, 1 - accept, 2 - denied
    @Column(name="status")
    private Integer status;

}
