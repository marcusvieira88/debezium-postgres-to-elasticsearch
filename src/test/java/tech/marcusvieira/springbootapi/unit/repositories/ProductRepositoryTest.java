package tech.marcusvieira.springbootapi.unit.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.marcusvieira.springbootpostgreselastic.models.ProductEntity;
import tech.marcusvieira.springbootpostgreselastic.repositories.ProductRepository;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void cleanData() {
        productRepository.deleteAll();
    }

    @Test
    public void shouldCreateProductWhenProductProvided() {
        ProductEntity product = new ProductEntity("product1", 1000);
        final ProductEntity productSaved = productRepository.save(product);
        assertEquals(product.getId(), productSaved.getId());
        assertEquals(product.getDescription(), productSaved.getDescription());
        assertEquals(product.getUnits(), productSaved.getUnits());
    }

    @Test
    public void shouldFindProductWhenProductIdProvided() {
        ProductEntity product = new ProductEntity("product2", 2000);
        final ProductEntity productSaved = productRepository.save(product);
        assertEquals(product.getId(), productSaved.getId());
        assertEquals(product.getDescription(), productSaved.getDescription());
        assertEquals(product.getUnits(), productSaved.getUnits());
    }

    @Test
    public void shouldUpdateProductWhenProductProvided() {
        ProductEntity product = new ProductEntity("product3", 3000);
        final ProductEntity productSaved = productRepository.save(product);
        //update product
        productSaved.setDescription("product4");
        productSaved.setUnits(4000);
        final ProductEntity productUpdated = productRepository.save(productSaved);
        assertEquals(product.getId(), productUpdated.getId());
        assertEquals(product.getDescription(), productUpdated.getDescription());
        assertEquals(product.getUnits(), productUpdated.getUnits());
    }

    @Test
    public void shouldDeleteProductWhenProductIdProvided() {
        ProductEntity product = new ProductEntity("product5", 5000);
        final ProductEntity productSaved = productRepository.save(product);
        //delete product
        productRepository.deleteById(productSaved.getId());
        //try to find deleted data
        final Optional<ProductEntity> productDeleted = productRepository.findById(productSaved.getId());
        assertFalse(productDeleted.isPresent());
    }

    @Test
    public void shouldFindAllProducts() {
        ProductEntity product = new ProductEntity("product6", 6000);
        ProductEntity product2 = new ProductEntity("product7", 7000);
        ProductEntity product3 = new ProductEntity("product8", 8000);
        productRepository.save(product);
        productRepository.save(product2);
        productRepository.save(product3);
        //get all products
        final List<ProductEntity> products = productRepository.findAll();
        assertNotNull(products);
        assertEquals(products.size(), 3);
    }
}
