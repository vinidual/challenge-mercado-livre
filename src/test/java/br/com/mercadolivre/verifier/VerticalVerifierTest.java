package br.com.mercadolivre.verifier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class VerticalVerifierTest {

    @InjectMocks
    private VerticalVerifier verticalVerifier;

    @Test
    public void shouldReturnTrueLongDNA(){

        String[] dna = new String[]{
                "GTTCGGTGAG",
                "AAGTACGCCA",
                "TGTCAGTTAG",
                "ATAAACAATG",
                "AGATGCCGAA",
                "ATCCTCGAGC",
                "CAGGGAAGAA",
                "TGTGCTTAGA",
                "CGCCCACCGA",
                "GGTGAGGAAA"
        };

        assertEquals("true", verticalVerifier.verifyVerticalString(9, 6, dna, 4));
    }

    @Test
    public void shouldReturnTrue(){



    }

    @Test
    public void shouldReturnFalse(){
        String[] dna = new String[]{
                "ABCD", "EFGH", "IJKL", "MNOP"
        };

        assertEquals("false", verticalVerifier.verifyVerticalString(0, 0, dna, 4));
    }

}
