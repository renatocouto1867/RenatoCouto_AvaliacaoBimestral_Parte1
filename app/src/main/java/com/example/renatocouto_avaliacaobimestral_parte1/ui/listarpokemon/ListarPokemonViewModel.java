package com.example.renatocouto_avaliacaobimestral_parte1.ui.listarpokemon;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.renatocouto_avaliacaobimestral_parte1.entity.Result;
import com.example.renatocouto_avaliacaobimestral_parte1.repository.DadosRepository;

import java.util.ArrayList;
import java.util.List;

public class ListarPokemonViewModel extends ViewModel {

    private final DadosRepository dadosRepository;
    private final MutableLiveData<List<Result>> liveDataRecebido;
    private final MutableLiveData<String> liveDataMensagem;

    public ListarPokemonViewModel(DadosRepository dadosRepository) {
        this.dadosRepository = dadosRepository;
        liveDataMensagem = new MutableLiveData<>();
        liveDataRecebido = new MutableLiveData<>();
    }

    public void listar50Pokemons() {

        dadosRepository.obter50Pokemons(new DadosRepository.OnBaixarListener() {
            @Override
            public void sucesso(List<Result> results) {
                List<Result> pokemonList = new ArrayList<>();
                pokemonList.addAll(results);
                liveDataRecebido.postValue(new ArrayList<>(pokemonList));
            }

            @Override
            public void erro(String erro) {
                liveDataRecebido.postValue(new ArrayList<>());
            }
        });
    }

    public LiveData<List<Result>> getPokemons() {
        return liveDataRecebido;
    }

    public LiveData<String> getMensagem() {
        return liveDataMensagem;
    }

    public void listarBanco() {

        dadosRepository.bancoGetAllResults(new DadosRepository.OnBaixarListener() {
            @Override
            public void sucesso(List<Result> results) {
                List<Result> pokemonList = new ArrayList<>();
                pokemonList.addAll(results);
                Log.e("tamanho lista Banco", String.valueOf(pokemonList.size()));
                if (pokemonList.size() == 0 || pokemonList == null || pokemonList.isEmpty()) {
                    Log.e("tamanho lista Banco", "lista vazia");
                    liveDataRecebido.postValue(new ArrayList<Result>());
                }
                liveDataRecebido.postValue(new ArrayList<>(pokemonList));
            }

            @Override
            public void erro(String erro) {
                liveDataMensagem.postValue(erro);
                liveDataRecebido.postValue(new ArrayList<>());
            }
        });
    }

    public void salvarListaPokemons(List<Result> pokemons) {
        dadosRepository.bancoSalvarListaPokemons(pokemons, new DadosRepository.OnSalvarListaListener() {
            @Override
            public void sucesso(long[] ids) {
                if (ids.length > 0) {
                    liveDataMensagem.postValue("sucesso");
                }
            }

            @Override
            public void erro(String erro) {
                liveDataMensagem.postValue(erro);
            }
        });
    }

    public void limparBandoDados() {
        dadosRepository.bancoDeleteAll(new DadosRepository.OnDeleteAllListener() {
            @Override
            public void onSuccess(int registrosAfetados) {
                if (registrosAfetados == 0) {
                    liveDataMensagem.postValue("vazio");
                }
                if (registrosAfetados > 0) {
                    liveDataMensagem.postValue("sucesso");
                }
            }

            @Override
            public void onError(String error) {
                liveDataMensagem.postValue(error);
            }
        });

    }

}// listarPokemonViewModel




