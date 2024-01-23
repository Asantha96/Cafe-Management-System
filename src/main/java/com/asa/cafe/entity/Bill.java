package com.asa.cafe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;


@NamedQuery(name = "Bill.getAllBills", query = "SELECT b FROM Bill b ORDER BY b.id DESC ")
@NamedQuery(name = "Bill.getBillByUserName", query = "SELECT b FROM Bill b WHERE b.createdBy=:username ORDER BY b.id DESC ")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "bill")
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uuid;
    private String name;
    private String email;
    private String contactNumber;
    private String paymentMethod;
    private Integer total;

    @Column(columnDefinition = "json")
    private String productDetails;

    private String createdBy;
}
