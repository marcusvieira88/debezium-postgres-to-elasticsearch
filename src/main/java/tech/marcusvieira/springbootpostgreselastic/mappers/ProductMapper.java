package tech.marcusvieira.springbootpostgreselastic.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tech.marcusvieira.springbootpostgreselastic.models.ProductEntity;
import tech.marcusvieira.springbootpostgreselastic.resources.ProductResource;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );

    ProductEntity resourceToEntity(ProductResource productResource);

    ProductResource entityToResource(ProductEntity productEntity);
}
