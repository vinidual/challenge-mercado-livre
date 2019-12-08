package br.com.mercadolivre.verifier;

import org.springframework.stereotype.Component;

@Component
public class HorizontalVerifier {

    public String verifyHorizontalString(int h, int v, String[] sArray, int numLetters){

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

}
