package com.example.renatocouto_avaliacaobimestral_parte1.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.renatocouto_avaliacaobimestral_parte1.ClienteHttp.Conexao;
import com.example.renatocouto_avaliacaobimestral_parte1.entity.Pokemons;
import com.example.renatocouto_avaliacaobimestral_parte1.entity.Result;
import com.example.renatocouto_avaliacaobimestral_parte1.util.Auxilia;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DadosRepository {

    private static final String TAG = "DadosRepository";
    private static final String URL = "https://pokeapi.co/api/v2/pokemon?limit=100000&offset=0";
    private final ResultDao resultDao;
    private final MutableLiveData<List<Result>> pokemonLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Result>> pokemon50LiveData = new MutableLiveData<>();
    private final ExecutorService executorService;
    private Pokemons dadosBaixados;

    public DadosRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        resultDao = database.resultDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Para Executar a requisição da API em uma thread separada
     */
    private LiveData<Boolean> apiObterDadosAsync() {
        MutableLiveData<Boolean> resultado = new MutableLiveData<>();

        executorService.execute(() -> {
            try {
                Conexao conexao = new Conexao();
                InputStream inputStream = conexao.obterRespostaHTTP(URL);

                if (inputStream == null) {
                    Log.e(TAG, "Falha ao obter resposta HTTP.");
                    resultado.postValue(false);
                    return;
                }

                Auxilia auxilia = new Auxilia();
                String textoJSON = auxilia.converter(inputStream);

                if (textoJSON.isEmpty()) {
                    Log.e(TAG, "JSON recebido está vazio.");
                    resultado.postValue(false);
                    return;
                }

                Log.d(TAG, "JSON recebido com sucesso");

                Gson gson = new Gson();
                Type type = new TypeToken<Pokemons>() {}.getType();
                dadosBaixados = gson.fromJson(textoJSON, type);

                if (dadosBaixados != null && dadosBaixados.getResults() != null) {
                    atualizaId();
                    pokemonLiveData.postValue(dadosBaixados.getResults());
                    resultado.postValue(true);
                } else {
                    resultado.postValue(false);
                }

            } catch (JsonSyntaxException e) {
                Log.e(TAG, "Erro ao processar JSON", e);
                resultado.postValue(false);
            } catch (Exception e) {
                Log.e(TAG, "Erro inesperado", e);
                resultado.postValue(false);
            }
        });

        return resultado;
    }

    /**
     * Método público para obter todos os pokémons da API
     */
    public LiveData<List<Result>> obterTodosPokemons() {
        apiObterDadosAsync();
        return pokemonLiveData;
    }

    /**
     * Método público para obter apenas os 50 primeiros Pokémons
     */
    public LiveData<List<Result>> obter50Pokemons() {
        MediatorLiveData<List<Result>> mediator = new MediatorLiveData<>();

        if (dadosBaixados == null) {
            mediator.addSource(obterTodosPokemons(), results -> {
                if (results != null) {
                    List<Result> list50 = results.subList(0, Math.min(50, results.size()));
                    pokemon50LiveData.setValue(list50);
                    mediator.setValue(list50);
                }
            });
        } else {
            List<Result> listaGeral = dadosBaixados.getResults();
            List<Result> lista50 = new ArrayList<>(listaGeral.subList(0, Math.min(50, listaGeral.size())));
            pokemon50LiveData.setValue(lista50);
            mediator.setValue(lista50);
        }

        return mediator;
    }


    /**
     * Atualiza os IDs dos pokémons
     */
    private void atualizaId() {
        if (dadosBaixados == null || dadosBaixados.getResults() == null) {
            Log.e("DadosRepository", "Não há dados para atualizar IDs.");
            return;
        }

        for (Result result : dadosBaixados.getResults()) {
            result.setId(getIdPokemon(result.getUrl()));
        }
    }

    /**
     * Extrai o ID do Pokémon a partir da URL
     */
    private int getIdPokemon(String url) {
        if (url == null || url.isEmpty()) {
            return -1;
        }
        String[] parts = url.split("/");
        try {
            return Integer.parseInt(parts[parts.length - 1]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Insere um Pokémon no banco de dados
     */
    public void bancoInsert(Result result) {
        executorService.execute(() -> resultDao.insert(result));
    }

    /**
     * Obtém todos os pokémons do banco de dados
     */
    public LiveData<List<Result>> bancoGetAllResults() {
        MutableLiveData<List<Result>> resultList = new MutableLiveData<>();

        executorService.execute(() -> {
            List<Result> results = resultDao.getAllResults();
            resultList.postValue(results);
        });

        return resultList;
    }

    /**
     * Deleta todos os registros do banco de dados
     */
    public void bancoDeleteAll() {
        executorService.execute(() -> {
            resultDao.deleteAll();
        });
    }

    /**
     * Salva uma lista de pokémons no banco de dados
     */
    public LiveData<Boolean> BancoSalvarListaPokemons(List<Result> pokemons) {
        MutableLiveData<Boolean> salvosComSucesso = new MutableLiveData<>();

        if (pokemons == null || pokemons.isEmpty()) {
            salvosComSucesso.postValue(false);
            return salvosComSucesso;
        }

        executorService.execute(() -> {
            try {
                resultDao.insertAll(pokemons);
                Log.i("DadosRepository", "Pokémons salvos com sucesso: " + pokemons.size());
                salvosComSucesso.postValue(true);
            } catch (Exception e) {
                Log.e("DadosRepository", "Erro ao salvar pokémons: " + e.getMessage());
                salvosComSucesso.postValue(false);
            }
        });

        return salvosComSucesso;
    }

}
