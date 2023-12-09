package com.bunsen.rfidbasedattendancesystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@NoArgsConstructor
@Data
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    private String id;

    @Column(name = "card_number", unique = true, nullable = false)
    private String cardNumber;

    @JsonIgnore
    @OneToOne(mappedBy = "card")
    private User user;
}