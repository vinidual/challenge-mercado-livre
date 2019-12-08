package br.com.mercadolivre.service;

import br.com.mercadolivre.contract.StatsContract;
import br.com.mercadolivre.repository.*;
import br.com.mercadolivre.verifier.DiagonalVerifier;
import br.com.mercadolivre.verifier.HorizontalVerifier;
import br.com.mercadolivre.verifier.InverseDiagonalVerifier;
import br.com.mercadolivre.verifier.VerticalVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = "number.letters=4")
public class SimianServiceTest {

    @InjectMocks
    private SimianService simianService;

    @Mock
    private SimianRepository simianRepository;

    @Mock
    private StatsRepository statsRepository;

    @Mock
    private HorizontalVerifier horizontalVerifier;

    @Mock
    private VerticalVerifier verticalVerifier;

    @Mock
    private DiagonalVerifier diagonalVerifier;

    @Mock
    private InverseDiagonalVerifier inverseDiagonalVerifier;

    @Test
    public void shouldBeSimianInHorizontal() throws ExecutionException, InterruptedException {
        String[] dna = new String[]{
                "ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"
        };

        when(horizontalVerifier.verifyHorizontalString(any(), any(), any(), any())).thenReturn("true");

        assertTrue(simianService.isSimian(dna));
    }

    @Test
    public void shouldBeSimianInVertical() throws ExecutionException, InterruptedException {
        String[] dna = new String[]{
                "ATGCGA","AAGTGC","ATATTT","AGACGG","GCGTCA","TCACTG"
        };

        when(horizontalVerifier.verifyHorizontalString(any(), any(), any(), any())).thenReturn("not apply");
        when(verticalVerifier.verifyVerticalString(any(), any(), any(), any())).thenReturn("true");

        assertTrue(simianService.isSimian(dna));
    }

    @Test
    public void shouldBeSimianInDiagonal() throws ExecutionException, InterruptedException {
        String[] dna = new String[]{
                "ATGCGA","AAGTGC","ATATTT","AGAAGG","GCGTCA","TCACTG"
        };

        when(horizontalVerifier.verifyHorizontalString(any(), any(), any(), any())).thenReturn("not apply");
        when(verticalVerifier.verifyVerticalString(any(), any(), any(), any())).thenReturn("not apply");
        when(diagonalVerifier.verifyDiagonalString(any(), any(), any(), any())).thenReturn("true");

        assertTrue(simianService.isSimian(dna));
    }

    @Test
    public void shouldBeSimianInInverseDiagonal() throws ExecutionException, InterruptedException {

        String[] dna = new String[]{
                "ATGCGA","AAGTGC","ATATTT","AGACGG","GCGTCA","TCACTG"
        };

        when(horizontalVerifier.verifyHorizontalString(any(), any(), any(), any())).thenReturn("not apply");
        when(verticalVerifier.verifyVerticalString(any(), any(), any(), any())).thenReturn("not apply");
        when(diagonalVerifier.verifyDiagonalString(any(), any(), any(), any())).thenReturn("not apply");
        when(inverseDiagonalVerifier.verifyInverseDiagonalString(any(), any(), any(), any())).thenReturn("true");

        assertTrue(simianService.isSimian(dna));
    }

    @Test
    public void shouldBeHuman() throws ExecutionException, InterruptedException {
        String[] dna = new String[]{
                "ATGCGA","AAGTGC","ATATTT","AGACGG","GCGTCA","TCACTG"
        };

        when(horizontalVerifier.verifyHorizontalString(any(), any(), any(), any())).thenReturn("not apply");
        when(verticalVerifier.verifyVerticalString(any(), any(), any(), any())).thenReturn("not apply");
        when(diagonalVerifier.verifyDiagonalString(any(), any(), any(), any())).thenReturn("not apply");
        when(inverseDiagonalVerifier.verifyInverseDiagonalString(any(), any(), any(), any())).thenReturn("not apply");
        when(simianRepository.findFirstByDnaString(any())).thenReturn(Optional.of(SimianEntity.builder().build()));

        assertFalse(simianService.isSimian(dna));
    }

    @Test
    public void shouldCalculateRatioHuman() {
        String[] dna = new String[]{};

        when(statsRepository.findById(any())).thenReturn(Optional.of(StatsEntity.builder()
                .countMutantDna(0L)
                .countHumanDna(0L)
                .build()));

        simianService.calculateRatio(DnaType.HUMAN.name());

        verify(statsRepository).findById(1);
    }

    @Test
    public void shouldCalculateRatioSimian() {
        String[] dna = new String[]{};

        when(statsRepository.findById(any())).thenReturn(Optional.of(StatsEntity.builder()
                .countMutantDna(0L)
                .countHumanDna(0L)
                .build()));

        simianService.calculateRatio(DnaType.SIMIAN.name());

        verify(statsRepository).findById(1);
    }

    @Test
    public void shouldGetStats(){

        when(statsRepository.findById(1)).thenReturn(Optional.of(StatsEntity.builder().build()));

        StatsContract statsContract = simianService.getStats();

        assertNotNull(statsContract);
        assertNull(statsContract.getCountHumanDna());
        assertNull(statsContract.getCountMutantDna());
        assertNull(statsContract.getRatio());
    }

    @Test
    public void shouldNotGetStats(){

        when(statsRepository.findById(1)).thenReturn(Optional.empty());

        StatsContract statsContract = simianService.getStats();

        assertNotNull(statsContract);
        assertNull(statsContract.getCountHumanDna());
        assertNull(statsContract.getCountMutantDna());
        assertNull(statsContract.getRatio());
    }

}
