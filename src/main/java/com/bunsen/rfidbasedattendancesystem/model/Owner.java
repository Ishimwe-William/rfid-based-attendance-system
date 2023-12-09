//package com.bunsen.rfidbasedattendancesystem.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.GenericGenerator;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Data
//@NoArgsConstructor
//public class Owner {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid",strategy = "uuid2")
//    private String id;
//
//    @Column(name = "first_name")
//    private String firstName;
//
//    @Column(name = "last_name")
//    private String lastName;
//
//    private String gender;
//    private String phone;
//    private String email;
//
//    private LocalDateTime created;
//    private LocalDateTime modified;
//
//    @OneToOne
//    private Attendance attendance;
//
//    @PrePersist
//    void onCreate(){
//        this.created=LocalDateTime.now();
//    }
//
//    @PreUpdate
//    void onUpdate(){
//        this.modified=LocalDateTime.now();
//    }
//}
