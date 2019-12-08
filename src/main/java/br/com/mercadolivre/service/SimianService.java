package br.com.mercadolivre.service;

import br.com.mercadolivre.contract.StatsContract;
import br.com.mercadolivre.repository.*;
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

    @Autowired
    private SimianRepository simianRepository;

    @Autowired
    private StatsRepository statsRepository;

    private final int numLetters;

    @Autowired
    public SimianService(@Value("${number.letters}") Integer numLetters){
        this.numLetters = numLetters;
    }

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
                    .supplyAsync(() -> verifyHorizontalString(finalHorizontalIdx, finalVerticalIdx, dna));

            CompletableFuture<String> verticalString = CompletableFuture
                    .supplyAsync(() -> verifyVerticalString(finalHorizontalIdx, finalVerticalIdx, dna));

            CompletableFuture<String> diagonalString = CompletableFuture
                    .supplyAsync(() -> verifyDiagonalString(finalHorizontalIdx, finalVerticalIdx, dna));

            CompletableFuture<String> inverseDiagonalString = CompletableFuture
                    .supplyAsync(() -> verifyInverseDiagonalString(finalHorizontalIdx, finalVerticalIdx, dna));

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
            else if((dnaLength - horizontalIdx) >= numLetters){
                horizontalIdx++;
                verticalIdx = 0;
            }
            else if(horizontalIdx < dnaLength){
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

    private String verifyHorizontalString(int h, int v, String[] sArray){

        String result = "not apply";

        if(sArray.length - h >= numLetters) {

            char comparable = ' ';

            result = "true";

            for(int index = h; index < h + numLetters; index++){

                if(comparable == ' '){
                    comparable = sArray[v].charAt(index);
                }
                else if(sArray[v].charAt(index) != comparable){
                    result = "false";
                    break;
                }
            }
        }

        return result;
    }

    private String verifyVerticalString(int h, int v, String[] sArray){

        String result = "not apply";

        if(sArray.length - v >= numLetters && h < sArray.length){

            char comparable = ' ';

            result = "true";

            for(int index = v; index < v + numLetters; index++){

                if(comparable == ' '){
                    comparable = sArray[v].charAt(index);
                }
                else if(sArray[v].charAt(index) != comparable){
                    result = "false";
                    break;
                }
            }
        }

        return result;
    }

    private String verifyDiagonalString(int h, int v, String[] sArray){

        String result = "not apply";

        if(sArray.length - h >= numLetters && sArray.length - v >= numLetters){

            char comparable = ' ';

            result = "true";

            int letter = numLetters;

            while (letter > 0){

                if(comparable == ' '){
                    comparable = sArray[v].charAt(h);
                }
                else if(sArray[v].charAt(h) != comparable){
                    result = "false";
                    break;
                }

                letter--;
                h++;
                v++;
            }
        }

        return result;
    }

    private String verifyInverseDiagonalString(int h, int v, String[] sArray){

        String result = "not apply";

        if(sArray.length - h < numLetters && sArray.length - v >= numLetters && h < sArray.length){

            char comparable = ' ';

            result = "true";

            int letter = numLetters;

            while (letter > 0){

                if(comparable == ' '){
                    comparable = sArray[v].charAt(h);
                }
                else if(sArray[v].charAt(h) != comparable){
                    result = "false";
                    break;
                }

                letter--;
                h--;
                v++;
            }
        }

        return result;
    }

    private void saveDna(SimianEntity newDna) {

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

    private void calculateRatio(String dnaType){

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
                    BigDecimal.valueOf(statsEntity.getCountMutantDna() / statsEntity.getCountHumanDna()) :
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
