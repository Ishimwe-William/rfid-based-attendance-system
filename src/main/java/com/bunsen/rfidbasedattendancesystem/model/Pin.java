package com.bunsen.rfidbasedattendancesystem.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "pin")
public class Pin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    private String id;

    @Column(nullable = false)
    private String pinCode;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime expirationTime;

}

