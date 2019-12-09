package br.com.mercadolivre.verifier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HorizontalVerifierTest {

    @InjectMocks
    private HorizontalVerifier horizontalVerifier;

    @Test
    public void shouldReturnTrue(){

        String[] dna = new String[]{
                "AAAA", "AAAA", "AAAA", "AAAA"
        };

        assertEquals("true", horizontalVerifier.verifyHorizontalString(0, 0, dna, 4));
    }

    @Test
    public void shouldReturnFalse(){
        String[] dna = new String[]{
                "ABCD", "EFGH", "IJKL", "MNOP"
        };

        assertEquals("false", horizontalVerifier.verifyHorizontalString(0, 0, dna, 4));
    }

}
