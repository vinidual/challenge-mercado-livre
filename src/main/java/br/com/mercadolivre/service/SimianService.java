package br.com.mercadolivre.service;

import br.com.mercadolivre.contract.StatsContract;
import br.com.mercadolivre.repository.*;
import br.com.mercadolivre.verifier.DiagonalVerifier;
import br.com.mercadolivre.verifier.HorizontalVerifier;
import br.com.mercadolivre.verifier.InverseDiagonalVerifier;
import br.com.mercadolivre.verifier.VerticalVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class SimianService {

    @Value("${number.letters}")
    private int numLetters;

    @Autowired
    private SimianRepository simianRepository;

    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private HorizontalVerifier horizontalVerifier;

    @Autowired
    private VerticalVerifier verticalVerifier;

    @Autowired
    private DiagonalVerifier diagonalVerifier;

    @Autowired
    private InverseDiagonalVerifier inverseDiagonalVerifier;

    public Boolean isSimian(String[] dna) throws ExecutionException, InterruptedException {

        boolean isSimian = false;
        boolean finished = false;

        int dnaLength = dna.length;
        int horizontalIdx = 0;
        int verticalIdx = 0;

        log.info("\n");

        while(!finished){

            int finalHorizontalIdx = horizontalIdx;
            int finalVerticalIdx = verticalIdx;

            CompletableFuture<String> horizontalString = CompletableFuture
                    .supplyAsync(() -> horizontalVerifier
                            .verifyHorizontalString(finalHorizontalIdx, finalVerticalIdx, dna, numLetters));

            CompletableFuture<String> verticalString = CompletableFuture
                    .supplyAsync(() -> verticalVerifier
                            .verifyVerticalString(finalHorizontalIdx, finalVerticalIdx, dna, numLetters));

            CompletableFuture<String> diagonalString = CompletableFuture
                    .supplyAsync(() -> diagonalVerifier
                            .verifyDiagonalString(finalHorizontalIdx, finalVerticalIdx, dna, numLetters));

            CompletableFuture<String> inverseDiagonalString = CompletableFuture
                    .supplyAsync(() -> inverseDiagonalVerifier
                            .verifyInverseDiagonalString(finalHorizontalIdx, finalVerticalIdx, dna, numLetters));

            CompletableFuture<Void> completableFuture = CompletableFuture
                    .allOf(horizontalString, verticalString, diagonalString, inverseDiagonalString);

            completableFuture.get();

            String resultHorizontalString = horizontalString.get();
            String resultVerticalString = verticalString.get();
            String resultDiagonalString = diagonalString.get();
            String resultInverseDiagonalString = inverseDiagonalString.get();

            log.info("\nHorizontal string: '{}', verticalString: {}, diagonalString: {}, inverseDiagonalString: {}\n",
                    resultHorizontalString, resultVerticalString, resultDiagonalString, resultInverseDiagonalString);

            if(resultHorizontalString.equals("true")){
                log.info("\nHorizontal sequence found starting at dna[{}][{}]", verticalIdx, horizontalIdx);
                isSimian = true;
                break;
            }
            else if(resultVerticalString.equals("true")){
                log.info("\nVertical sequence found starting at dna[{}][{}]", verticalIdx, horizontalIdx);
                isSimian = true;
                break;
            }
            else if(resultDiagonalString.equals("true")){
                log.info("\nDiagonal sequence found starting at dna[{}][{}]", verticalIdx, horizontalIdx);
                isSimian = true;
                break;
            }
            else if(resultInverseDiagonalString.equals("true")){
                log.info("\nInverse Diagonal sequence found starting at dna[{}][{}]", verticalIdx, horizontalIdx);
                isSimian = true;
                break;
            }

            if(verticalIdx < dnaLength - 1){
                verticalIdx++;
            }
            else if(horizontalIdx < dnaLength - 1){
                horizontalIdx++;
                verticalIdx = 0;
            }
            else {
                finished = true;
            }
        }

        boolean finalIsSimian = isSimian;

        CompletableFuture.runAsync(() -> this.saveDna(SimianEntity.builder()
            .dnaString(Arrays.toString(dna))
            .dnaType(finalIsSimian ? DnaType.SIMIAN.name() : DnaType.HUMAN.name())
            .build()
        ));

        return isSimian;
    }

    protected void saveDna(SimianEntity newDna) {

        Optional<SimianEntity> simianEntity = simianRepository.findFirstByDnaString(newDna.getDnaString());

        if(!simianEntity.isPresent()){
            log.info("new dna recorded:{}", newDna.getDnaString());
            simianRepository.save(newDna);
            this.calculateRatio(newDna.getDnaType());
        }
        else {
            log.warn("dna already recorded, avoiding duplicated unique exception: {}", newDna.getDnaString());
        }
    }

    protected void calculateRatio(String dnaType){

        Optional<StatsEntity> optionalStatsEntity = statsRepository.findById(1);

        if(optionalStatsEntity.isPresent()){

            StatsEntity statsEntity = optionalStatsEntity.get();

            if(DnaType.HUMAN.name().equals(dnaType)){
                log.info("incrementing count human");
                statsEntity.setCountHumanDna(statsEntity.getCountHumanDna() + 1);
            }
            else if(DnaType.SIMIAN.name().equals(dnaType)){
                log.info("incrementing count mutant");
                statsEntity.setCountMutantDna(statsEntity.getCountMutantDna() + 1);
            }

            log.info("recalculating ratio");
            statsEntity.setRatio(statsEntity.getCountHumanDna() != 0L ?
                    BigDecimal.valueOf((double) statsEntity.getCountMutantDna() / statsEntity.getCountHumanDna()) :
                    BigDecimal.ZERO);

            statsRepository.save(statsEntity);
        }
    }

    public StatsContract getStats(){

        Optional<StatsEntity> statsEntity = statsRepository.findById(1);

        if(statsEntity.isPresent()){

            return StatsContract.builder()
                    .countHumanDna(statsEntity.get().getCountHumanDna())
                    .countMutantDna(statsEntity.get().getCountMutantDna())
                    .ratio(statsEntity.get().getRatio())
                    .build();
        }

        return StatsContract.builder().build();
    }
}
