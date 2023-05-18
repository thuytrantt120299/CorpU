package com.example.corpu.unitSessionalStaff;

import com.example.corpu.sessionalStaff.SessionalStaff;
import com.example.corpu.unit.Unit;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name="unit_sessional_staff")
public class UnitSessionalStaff {
    @Id
    private String id;
    @ManyToOne
    private SessionalStaff sessionalStaff;
    @ManyToOne
    private Unit unit;
}
