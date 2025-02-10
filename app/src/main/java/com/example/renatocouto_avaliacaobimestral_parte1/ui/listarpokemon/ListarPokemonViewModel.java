package com.example.renatocouto_avaliacaobimestral_parte1.ui.listarpokemon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.renatocouto_avaliacaobimestral_parte1.entity.Result;
import com.example.renatocouto_avaliacaobimestral_parte1.repository.DadosRepository;

import java.util.List;

public class ListarPokemonViewModel extends ViewModel {

    private final DadosRepository dadosRepository;
    private final MutableLiveData<List<Result>> liveDataRecebido = new MutableLiveData<>();

    public ListarPokemonViewModel(DadosRepository dadosRepository) {
        this.dadosRepository = dadosRepository;
    }

    public void listar50Pokemins() {
        dadosRepository.obter50Pokemons().observeForever(results -> {
            liveDataRecebido.postValue(results);
        });
    }

    public void listarBanco() {
        dadosRepository.bancoGetAllResults().observeForever(results -> {
            liveDataRecebido.postValue(results);
        });
    }

    public LiveData<Boolean> savarListaPokemons(List<Result> pokemons) {
        return dadosRepository.BancoSalvarListaPokemons(pokemons);
    }

    public void limparBandoDados(){
        dadosRepository.bancoDeleteAll();
        listarBanco();
    }

    public LiveData<List<Result>> getPokemons() {
        return liveDataRecebido;
    }
}