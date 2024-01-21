package com.asa.cafe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId", query = "SELECT u FROM User u WHERE u.email=:email")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "contact_number")
    private String contactNumber;

    private String email;//email is the username for user login
    private String password;
    private String status;
    private String role;
}
