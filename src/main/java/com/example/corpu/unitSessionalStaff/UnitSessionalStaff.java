package com.example.corpu.unitSessionalStaff;

import com.example.corpu.sessionalStaff.SessionalStaff;
import com.example.corpu.unit.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="unit_sessional_staff")
public class UnitSessionalStaff {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "sessional_staff_id", updatable = false)
    private SessionalStaff sessionalStaff;
    @ManyToOne
    @JoinColumn(name = "unit_id", updatable = false)
    private Unit unit;
}
