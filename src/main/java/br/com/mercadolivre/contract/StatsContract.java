package br.com.mercadolivre.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class StatsContract {

    @JsonProperty("count_mutant_dna")
    private Long countMutantDna;

    @JsonProperty("count_human_dna")
    private Long countHumanDna;

    private BigDecimal ratio;

}
