package com.lab.ecommercebackend.model;

import com.lab.ecommercebackend.dto.ProductDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;

@Entity
@Table(name = "tbproduct")
public class Product {
    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long id;

    @Getter @Setter
    @NotNull
    @Max(value = 150)
    private String name;

    @Getter @Setter
    @NotNull
    @Max(value = 1000)
    private String description;

    @Getter @Setter
    private String category;

    @Getter @Setter
    private Integer stock_quantity;

    @Getter @Setter
    private Double price;

    public Product() {
    }

    public Product(Long id, String name, String description, String category, Integer stock_quantity, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.stock_quantity = stock_quantity;
        this.price = price;
    }

    public Product(ProductDto product) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
