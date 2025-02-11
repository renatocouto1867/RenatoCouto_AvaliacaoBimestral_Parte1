package com.example.renatocouto_avaliacaobimestral_parte1.ClienteHttp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;

public class Conexao {


    public InputStream obterRespostaHTTP(String end) {
        try {
            URL url = new URL(end);

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            conexao.setRequestMethod("GET");

            return new BufferedInputStream(conexao.getInputStream());

        } catch (MalformedInputException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}//class
