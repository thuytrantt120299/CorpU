package com.example.corpu.unit;
import com.example.corpu.permanentStaff.PermanentStaff;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name="unit")
public class Unit {
    @Id
    private String id;
    private String name;
    private String code;
    private String semester;
    @Column(name="number_of_students")
    private Integer numStudents;
    @ManyToOne
    @JoinColumn(name = "permanent_staff_id", insertable = false, updatable = false)
    private PermanentStaff permanentStaff;
}
