package com.bunsen.rfidbasedattendancesystem.repository.model;

import com.sun.jdi.Locatable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid2")
    private String id;
    @Column(name = "card_no")
    private String cardNo;
    private LocalDateTime created;

    @OneToOne
    private Owner owner;

    @PrePersist
    void onCreate(){
        this.created=LocalDateTime.now();
    }
}
