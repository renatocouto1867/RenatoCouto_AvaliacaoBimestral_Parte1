package com.example.renatocouto_avaliacaobimestral_parte1.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;


public class Auxilia {//pesquisa biblioteca retrofit

    public String converter(InputStream inputStream) {
        InputStreamReader inputStreamReader =
                new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String conteudo = "";
        try {
            while (((conteudo = bufferedReader.readLine()) != null)) {
                stringBuilder.append(conteudo).append("\n");
            }//while
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }//while

}

