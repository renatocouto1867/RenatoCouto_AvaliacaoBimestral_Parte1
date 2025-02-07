package com.example.renatocouto_avaliacaobimestral_parte1.repository;

import android.util.Log;

import com.example.renatocouto_avaliacaobimestral_parte1.ClienteHttp.Conexao;
import com.example.renatocouto_avaliacaobimestral_parte1.entity.Pokemon;
import com.example.renatocouto_avaliacaobimestral_parte1.entity.Result;
import com.example.renatocouto_avaliacaobimestral_parte1.util.Auxilia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DadosRepository {

    private static final String URL = "https://pokeapi.co/api/v2/pokemon?limit=100000&offset=0";
    private static Pokemon dadosBaixados;

    public static Pokemon obterDados() {

        if (dadosBaixados != null) {
            return dadosBaixados;
        }

        Conexao conexao = new Conexao();
        InputStream inputStream = conexao.obterRespostaHTTP(URL);
        Auxilia auxilia = new Auxilia();
        String textoJSON = auxilia.converter(inputStream);
        Log.i("LitarPokemonFragment", "JSON recebido: " + textoJSON);

        Gson gson = new Gson();

        if (textoJSON != null) {
            Type type = new TypeToken<Pokemon>() {
            }.getType();
            dadosBaixados = gson.fromJson(textoJSON, type);
            atualizaId();
            return dadosBaixados;

        } else {
            return null;
        }//if else

    }

    public static List<Result> obterPokemons() {
        Pokemon dados = obterDados();

        return dados.getResults();
    }
    public static List<Result> get50Pokemons(){
        List<Result> lista50 = new ArrayList<>();
        List<Result> listaBaixada = obterPokemons();

        for (int i = 0; i <50 ; i++) {
            lista50.add(listaBaixada.get(i));
        }
        return lista50;
    }

    public static String getURL() {
        return URL;
    }

    public static void atualizaId(){
        List<Result> results= dadosBaixados.getResults();

        for (int i = 0; i <results.size() ; i++) {
            results.get(i).setId(getIdPokemon(results.get(i).getUrl()));
        }
    }

    public static  int getIdPokemon(String url) {
        if (url == null || url.isEmpty()) {
            return -1;
        }
        String[] parts = url.split("/");
        try {
            //aqui eu pego a ultima posição
            return Integer.parseInt(parts[parts.length - 1]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
