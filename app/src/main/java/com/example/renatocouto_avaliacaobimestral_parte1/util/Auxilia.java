package com.example.renatocouto_avaliacaobimestral_parte1.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Auxilia {//pesquisa biblioteca retrofit

    public String converter(InputStream inputStream) {
        InputStreamReader inputStreamReader =
                new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();

        try {
            String linha;
            while (((linha = bufferedReader.readLine()) != null)) {
                stringBuilder.append(linha).append("\n");
            }//while
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }//converter

}

