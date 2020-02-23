package tech.marcusvieira.springbootapi.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tech.marcusvieira.springbootpostgreselastic.models.ProductEntity;
import tech.marcusvieira.springbootpostgreselastic.repositories.ProductRepository;
import tech.marcusvieira.springbootpostgreselastic.resources.ProductResource;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void cleanData() {
        productRepository.deleteAll();
    }

    @Test
    public void shouldCreateProductWhenProductSent() throws Exception {

        ProductResource product = new ProductResource("product1", 1000);

        mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andExpect(jsonPath("$.units").value(product.getUnits()));
    }

    @Test
    public void shouldUpdateProductWhenProductSent() throws Exception {

        ProductResource product = new ProductResource("product1", 3000);

        final MvcResult resultSave = mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andExpect(jsonPath("$.units").value(product.getUnits()))
            .andReturn();

        ProductEntity responseSave = objectMapper
            .readValue(resultSave.getResponse().getContentAsString(), ProductEntity.class);

        ProductResource productUpdated = new ProductResource("product3", 4500);

        mockMvc.perform(put("/products/{id}", responseSave.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productUpdated)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value(productUpdated.getDescription()))
            .andExpect(jsonPath("$.units").value(productUpdated.getUnits()));
    }

    @Test
    public void shouldNotUpdateProductWhenProductNotExist() throws Exception {

        ProductResource productUpdated = new ProductResource("product4", 3500);

        mockMvc.perform(put("/products/{id}", 999999999)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productUpdated)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldFindProductWhenProductRequested() throws Exception {

        ProductResource product = new ProductResource("product1", 3000);

        final MvcResult resultSave = mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andExpect(jsonPath("$.units").value(product.getUnits()))
            .andReturn();

        ProductEntity responseSave = objectMapper
            .readValue(resultSave.getResponse().getContentAsString(), ProductEntity.class);

        mockMvc.perform(get("/products/{id}", responseSave.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andExpect(jsonPath("$.units").value(product.getUnits()));
    }

    @Test
    public void shouldNotFindProductWhenProductNotExist() throws Exception {

        mockMvc.perform(get("/products/{id}", 999999999)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteProductWhenProductRequested() throws Exception {

        ProductResource product = new ProductResource("product1", 3000);

        final MvcResult resultSave = mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andExpect(jsonPath("$.units").value(product.getUnits()))
            .andReturn();

        ProductEntity responseSave = objectMapper
            .readValue(resultSave.getResponse().getContentAsString(), ProductEntity.class);

        mockMvc.perform(delete("/products/{id}", responseSave.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        mockMvc.perform(get("/products/{id}", responseSave.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotDeleteProductWhenProductNotExist() throws Exception {

        mockMvc.perform(delete("/products/{id}", 999999999)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldFindAllProductsWhenProductRequested() throws Exception {

        ProductResource product = new ProductResource("product1", 3000);
        ProductResource product2 = new ProductResource("product12", 4000);

        mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andExpect(jsonPath("$.units").value(product.getUnits()))
            .andReturn();

        mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product2)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value(product2.getDescription()))
            .andExpect(jsonPath("$.units").value(product2.getUnits()))
            .andReturn();

        mockMvc.perform(get("/products")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldNotFindAllProductWhenProductsNotExist() throws Exception {

        mockMvc.perform(get("/products")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}
