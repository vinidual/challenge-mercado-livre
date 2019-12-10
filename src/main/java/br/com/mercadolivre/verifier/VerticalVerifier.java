package br.com.mercadolivre.verifier;

import org.springframework.stereotype.Component;

@Component
public class VerticalVerifier {

    public String verifyVerticalString(Integer h, Integer v, String[] sArray, Integer numLetters){

        String result = "not apply";

        if(sArray.length - v >= numLetters && h < sArray.length){

            char comparable = ' ';

            result = "true";

            for(int index = v; index < v + numLetters; index++){

                if(comparable == ' '){
                    comparable = sArray[index].charAt(h);
                }
                else if(sArray[index].charAt(h) != comparable){
                    result = "false";
                    break;
                }
            }
        }

        return result;
    }

}
