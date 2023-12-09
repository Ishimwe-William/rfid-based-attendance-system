package com.bunsen.rfidbasedattendancesystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    private String id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    private LocalDateTime created;

    private String formattedDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="attendances_users",
            joinColumns={@JoinColumn(name="ATTENDANCE_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")})
    private List<User> users = new ArrayList<>();

    @PrePersist
    void onCreate(){
        this.created=LocalDateTime.now();
    }
}
