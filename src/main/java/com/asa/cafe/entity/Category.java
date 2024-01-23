package com.asa.cafe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Category.getAllCategory", query = "SELECT c FROM Category c WHERE c.id IN (SELECT p.category FROM Product p WHERE p.status='true')")//hql query

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

}
