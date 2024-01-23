package com.asa.cafe.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId", query = "SELECT u FROM User u WHERE u.email=:email")
@NamedQuery(name = "User.getAllUser", query = "SELECT NEW com.asa.cafe.wrapper.UserWrapper(u.id, u.name, u.email, u.contactNumber, u.status) FROM User u WHERE u.role='user' ")
//by lombok allArgs constructor, this object will not identify
@NamedQuery(name = "User.updateStatus", query = "UPDATE User u SET u.status=:status WHERE u.id=:id")

@NamedQuery(name = "User.getAllAdmin", query = "SELECT u.email FROM User u WHERE u.role='admin' ")


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
