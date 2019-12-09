package br.com.mercadolivre.verifier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DiagonalVerifierTest {

    @InjectMocks
    private DiagonalVerifier diagonalVerifier;

    @Test
    public void shouldReturnTrue(){

        String[] dna = new String[]{
            "AAAA", "AAAA", "AAAA", "AAAA"
        };

        assertEquals("true", diagonalVerifier.verifyDiagonalString(0, 0, dna, 4));
    }

    @Test
    public void shouldReturnFalse(){
        String[] dna = new String[]{
            "ABCD", "EFGH", "IJKL", "MNOP"
        };

        assertEquals("false", diagonalVerifier.verifyDiagonalString(0, 0, dna, 4));
    }

}
