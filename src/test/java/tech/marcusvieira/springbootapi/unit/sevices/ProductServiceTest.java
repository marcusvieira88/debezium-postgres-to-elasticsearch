package tech.marcusvieira.springbootapi.unit.sevices;

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
import tech.marcusvieira.springbootpostgreselastic.services.ProductService;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @BeforeEach
    public void cleanData() {
        productService.deleteAll();
    }

    @Test
    public void shouldCreateProductWhenProductProvided() {
        ProductEntity product = new ProductEntity("product1", 1000);
        final ProductEntity productSaved = productService.create(product);
        assertEquals(product.getId(), productSaved.getId());
        assertEquals(product.getDescription(), productSaved.getDescription());
        assertEquals(product.getUnits(), productSaved.getUnits());
    }

    @Test
    public void shouldFindProductWhenProductIdProvided() {
        ProductEntity product = new ProductEntity("product2", 2000);
        final ProductEntity productSaved = productService.create(product);
        assertEquals(product.getId(), productSaved.getId());
        assertEquals(product.getDescription(), productSaved.getDescription());
        assertEquals(product.getUnits(), productSaved.getUnits());
    }

    @Test
    public void shouldUpdateProductWhenProductProvided() {
        ProductEntity product = new ProductEntity("product1", 3000);
        final ProductEntity product1 = productService.create(product);
        //update product
        product1.setDescription("product2");
        product1.setUnits(4000);
        final ProductEntity productUpdated = productService.update(product);
        assertEquals(product.getId(), productUpdated.getId());
        assertEquals(product.getDescription(), productUpdated.getDescription());
        assertEquals(product.getUnits(), productUpdated.getUnits());
    }

    @Test
    public void shouldDeleteProductWhenProductIdProvided() {
        ProductEntity product = new ProductEntity("product5", 5000);
        final ProductEntity product1 = productService.create(product);
        //delete product
        productService.delete(product1.getId());
        //try to find deleted data
        final Optional<ProductEntity> productDeleted = productService.findById(product1.getId());
        assertFalse(productDeleted.isPresent());
    }

    @Test
    public void shouldFindAllProducts() {
        ProductEntity product = new ProductEntity("product6", 6000);
        ProductEntity product2 = new ProductEntity("product7", 7000);
        ProductEntity product3 = new ProductEntity("product8", 8000);
        productService.create(product);
        productService.create(product2);
        productService.create(product3);
        //get all products
        final List<ProductEntity> products = productService.findAll();
        assertNotNull(products);
        assertEquals(products.size(), 3);
    }
}
