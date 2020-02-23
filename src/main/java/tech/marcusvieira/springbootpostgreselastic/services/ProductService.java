package tech.marcusvieira.springbootpostgreselastic.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.marcusvieira.springbootpostgreselastic.models.ProductEntity;
import tech.marcusvieira.springbootpostgreselastic.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductEntity create(ProductEntity product) { return productRepository.save(product); }

    public Optional<ProductEntity> findById(Long productId) {
        return productRepository.findById(productId);
    }

    public ProductEntity update(ProductEntity product) { return productRepository.save(product); }

    public void delete(Long productId) { productRepository.deleteById(productId); }

    public List<ProductEntity> findAll() {
        return new ArrayList<>(productRepository.findAll());
    }

    public void deleteAll() { productRepository.deleteAll(); }
}
