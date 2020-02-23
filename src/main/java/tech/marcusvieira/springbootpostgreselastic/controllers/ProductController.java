package tech.marcusvieira.springbootpostgreselastic.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.marcusvieira.springbootpostgreselastic.errorhandlers.exceptions.ProductNotFoundException;
import tech.marcusvieira.springbootpostgreselastic.mappers.ProductMapper;
import tech.marcusvieira.springbootpostgreselastic.models.ProductEntity;
import tech.marcusvieira.springbootpostgreselastic.resources.ProductResource;
import tech.marcusvieira.springbootpostgreselastic.services.ProductService;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Post product")
    @ApiImplicitParam(name = "productResource", dataType = "ProductResource",
        value = "Product information")
    @PostMapping(path = "/products")
    public Resource<ProductEntity> create(@Valid @RequestBody ProductResource productResource) {

        ProductEntity product = ProductMapper.INSTANCE.resourceToEntity(productResource);
        final ProductEntity productSaved = productService.create(product);

        Resource<ProductEntity> response = new Resource<>(productSaved);
        addFindByIdLink(productSaved, response);
        addUpdateLink(productSaved, response);
        addDeleteLink(response);

        return response;
    }

    @ApiOperation(value = "Put product")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", dataType = "long",
            value = "Product id"),
        @ApiImplicitParam(name = "productResource", dataType = "ProductResource",
            value = "Product information")
    })
    @PutMapping(path = "/products/{id}")
    public Resource<ProductEntity> update(@NotNull @PathVariable Long id,
        @Valid @RequestBody ProductResource productResource) {

        final Optional<ProductEntity> product = productService.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found.");
        }

        ProductEntity productUpdate = ProductMapper.INSTANCE.resourceToEntity(productResource);
        productUpdate.setId(id);
        final ProductEntity productSaved = productService.update(productUpdate);

        Resource<ProductEntity> response = new Resource<>(productSaved);
        addFindByIdLink(productSaved, response);
        addUpdateLink(product.get(), response);
        addDeleteLink(response);

        return response;
    }

    @ApiOperation(value = "Delete product")
    @ApiImplicitParam(name = "id", dataType = "long",
        value = "Product id")
    @DeleteMapping(path = "/products/{id}")
    public Resource<ProductEntity> delete(@NotNull @PathVariable Long id) {

        final Optional<ProductEntity> product = productService.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found.");
        }

        productService.delete(id);

        return new Resource<>(product.get());
    }

    @ApiOperation(value = "Get product")
    @ApiImplicitParam(name = "id", dataType = "long",
        value = "Product id")
    @GetMapping(path = "/products/{id}")
    public Resource<ProductEntity> findById(@NotNull @PathVariable Long id) {

        final Optional<ProductEntity> product = productService.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found.");
        }

        Resource<ProductEntity> response = new Resource<>(product.get());
        addUpdateLink(product.get(), response);
        addDeleteLink(response);

        return response;
    }

    @ApiOperation(value = "Get all products")
    @GetMapping(path = "/products")
    public List<ProductEntity> findAll() {

        final List<ProductEntity> products = productService.findAll();
        if (products == null || products.size() == 0) {
            throw new ProductNotFoundException("Products not found.");
        }

        return products;
    }

    private void addFindByIdLink(ProductEntity productSaved, Resource<ProductEntity> response) {
        ControllerLinkBuilder linkBuilder = linkTo(methodOn(this.getClass()).findById(productSaved.getId()));
        response.add(linkBuilder.withRel("get-product"));
    }

    private void addUpdateLink(ProductEntity product, Resource<ProductEntity> productResponse) {
        ProductResource productResource = ProductMapper.INSTANCE.entityToResource(product);
        ControllerLinkBuilder updateLink = linkTo(methodOn(this.getClass()).update(product.getId(), productResource));
        productResponse.add(updateLink.withRel("update-product"));
    }

    private void addDeleteLink(Resource<ProductEntity> productResponse) {
        ControllerLinkBuilder deleteLink = linkTo(methodOn(this.getClass()).delete(1L));
        productResponse.add(deleteLink.withRel("delete-product"));
    }
}
