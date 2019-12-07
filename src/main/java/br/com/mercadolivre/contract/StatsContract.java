package br.com.mercadolivre.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StatsContract {

    @JsonProperty("count_mutant_dna")
    private Integer countMutantDna;

    @JsonProperty("count_human_dna")
    private Integer countHumanDna;

    private BigDecimal ratio;

}
