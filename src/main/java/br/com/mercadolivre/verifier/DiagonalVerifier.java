package br.com.mercadolivre.verifier;

import org.springframework.stereotype.Component;

@Component
public class DiagonalVerifier {

    public String verifyDiagonalString(int h, int v, String[] sArray, int numLetters){

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

}
