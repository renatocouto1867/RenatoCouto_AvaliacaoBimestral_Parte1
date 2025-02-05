package com.example.renatocouto_avaliacaobimestral_parte1.ui.listarpokemon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.renatocouto_avaliacaobimestral_parte1.entity.Result;
import com.example.renatocouto_avaliacaobimestral_parte1.repository.DadosRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LitarPokemonViewModel extends ViewModel {

    private final MutableLiveData<List<Result>> jsonRecebidoLiveData = new MutableLiveData<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public void baixarJson() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                jsonRecebidoLiveData.postValue(DadosRepository.get50Pokemons());
            }
        });
    }


    public LiveData<List<Result>> get50Pokemons() {
        return jsonRecebidoLiveData;
    }
}