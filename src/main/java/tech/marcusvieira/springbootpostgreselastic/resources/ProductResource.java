package tech.marcusvieira.springbootpostgreselastic.resources;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Represents a product information")
public class ProductResource {

    @Size(max = 60)
    @ApiModelProperty(notes = "Product Description")
    private String description;
    @NotNull
    @ApiModelProperty(notes = "Amount of units")
    private Integer units;
}
