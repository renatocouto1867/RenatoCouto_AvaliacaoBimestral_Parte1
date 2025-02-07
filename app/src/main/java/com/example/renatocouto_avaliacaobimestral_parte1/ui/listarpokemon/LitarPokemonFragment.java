package com.example.renatocouto_avaliacaobimestral_parte1.ui.listarpokemon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renatocouto_avaliacaobimestral_parte1.R;
import com.example.renatocouto_avaliacaobimestral_parte1.databinding.FragmentPokemonListarBinding;
import com.example.renatocouto_avaliacaobimestral_parte1.entity.Result;
import com.example.renatocouto_avaliacaobimestral_parte1.util.Mensagens;

import java.util.List;

public class LitarPokemonFragment extends Fragment {

    private FragmentPokemonListarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LitarPokemonViewModel litarPokemonViewModel = new ViewModelProvider(this).get(LitarPokemonViewModel.class);

        binding = FragmentPokemonListarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        exibeProgresso(true);

        litarPokemonViewModel.get50Pokemons().observe(getViewLifecycleOwner(), results -> {
            if (results != null) {
                configurarRecyclerViews(results);

            } else {
                Mensagens.showErro(requireView(), getString(R.string.erro_ao_baixar_dados));
            }
        });
        litarPokemonViewModel.baixarJson();
        return root;
    }


    private void configurarRecyclerViews(List<Result> results) {
        Log.e("tamanho lista", String.valueOf(results.size()));

        RecyclerView recyclerViewPokemon = binding.recyclerViewPokemons;
        /**
         * o problema estava na orientação do Layout, que deveria ser VERTICAL,
         * recyclerViewPokemon.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
         */
        recyclerViewPokemon.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        recyclerViewPokemon.setAdapter(new ItemListarPokemonAdapter(results));
        exibeProgresso(false);
    }

    private void exibeProgresso(boolean exibir){

        if (exibir){
            binding.progressCircular.setVisibility(View.VISIBLE);
            binding.tvCarregando.setVisibility(View.VISIBLE);
        } else {
            binding.progressCircular.setVisibility(View.GONE);
            binding.tvCarregando.setVisibility(View.GONE);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}