package tech.marcusvieira.springbootpostgreselastic.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.marcusvieira.springbootpostgreselastic.models.ProductEntity;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

    public List<ProductEntity> findAll();
}
