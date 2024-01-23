package com.asa.cafe.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Product.getAllProduct", query = "SELECT new com.asa.cafe.wrapper.ProductWrapper(p.id, p.name, p.description, p.price, p.status, p.category.id, p.category.name) FROM Product p")
@NamedQuery(name = "Product.updateProductStatus", query = "UPDATE Product p SET p.status=:status WHERE p.id=:id")
//:status is equal to the updateProductStatus() in ProductDao interface method
@NamedQuery(name = "Product.getProductByCategory", query = "SELECT new com.asa.cafe.wrapper.ProductWrapper(p.id, p.name) FROM Product p WHERE p.category.id=:id AND p.status='true'")
@NamedQuery(name = "Product.getProductById", query = "SELECT new com.asa.cafe.wrapper.ProductWrapper(p.id, p.name, p.description, p.price) FROM Product p WHERE p.id=:id")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "product")
public class Product  implements Serializable {

    public static final Long serialVersionUID = 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private Integer price;
    private String status;


    @ManyToOne(fetch = FetchType.LAZY)//one category can contain many products
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

}
