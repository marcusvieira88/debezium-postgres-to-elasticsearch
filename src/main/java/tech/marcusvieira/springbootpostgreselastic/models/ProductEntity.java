package tech.marcusvieira.springbootpostgreselastic.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@Table(name = "products")
@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    private Integer units;

    public ProductEntity(){}

    public ProductEntity(String description, Integer units) {
        this.description = description;
        this.units = units;
    }
}