package br.com.mercadolivre.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class SimianContract {

    @ApiModelProperty(required = true)
    @NotEmpty(message = "'dna' property cannot be empty or null")
    private List<String> dna;

    @JsonIgnore
    @AssertTrue(message = "'dna' chain is not valid, check number and size of the strings")
    public boolean isValidDnaArray(){
        return dna.stream().noneMatch(dnaStream -> dnaStream.length() != dna.size());
    }

}
