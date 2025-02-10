package com.example.renatocouto_avaliacaobimestral_parte1.ui.listarpokemon;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.renatocouto_avaliacaobimestral_parte1.repository.DadosRepository;
import com.example.renatocouto_avaliacaobimestral_parte1.util.Mensagens;

import java.util.List;

public class ListarPokemonFragment extends Fragment {

    private FragmentPokemonListarBinding binding;
    private ListarPokemonViewModel listarPokemonViewModel;
    private List<Result> resultList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //para passar o objeto repositorio para a viewModel
        DadosRepository dadosRepository = new DadosRepository(requireActivity().getApplication());
        ListarPokemonViewModelFactory factory = new ListarPokemonViewModelFactory(dadosRepository);

        // aqui passo viewModelFactory ja com repositorio
        listarPokemonViewModel = new ViewModelProvider(this, factory)
                .get(ListarPokemonViewModel.class);

        binding = FragmentPokemonListarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        exibeProgresso(true);

        listarPokemonViewModel.listar50Pokemons();

        observeLista();

        configuraButtons();

        return root;
    }

    private void observeLista() {
        listarPokemonViewModel.getPokemons().observe(getViewLifecycleOwner(), results -> {
            if (results != null) {
                resultList = results;
                if (resultList.isEmpty() || resultList == null || resultList.size() == 0) {
                    binding.recyclerViewPokemons.setVisibility(View.GONE);
                    binding.tvEmptyState.setVisibility(View.VISIBLE);
                } else {
                    binding.tvEmptyState.setVisibility(View.GONE);
                    binding.recyclerViewPokemons.setVisibility(View.VISIBLE);
                    configurarRecyclerViews(resultList);
                }
            } else {
                Mensagens.showErro(requireView(), getString(R.string.erro_ao_baixar_dados));
            }
        });
    }

    private void configuraButtons() {
        binding.buttonSalvar.setOnClickListener(view -> {
            salvarListaBanco();
        });
        binding.buttonBaixar.setOnClickListener(view -> {
            atualizarListaApi();
        });
        binding.buttonLimpar.setOnClickListener(view -> {
            limparBandoDados();

        });
    }

    private void limparBandoDados() {

        new AlertDialog.Builder(getContext()).setTitle(R.string.confirmar_exclusao)
                .setMessage(getString(R.string.realmente_deseja_deletar))
                .setPositiveButton(getString(R.string.confirma), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //sim
                        listarPokemonViewModel.limparBandoDados();
                    }
                }).setNegativeButton(R.string.confirma, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //não
                        dialog.dismiss();
                    }
                }).create().show();
    }

    private void atualizarListaApi() {
//        listarPokemonViewModel.listarBanco();
        listarPokemonViewModel.listar50Pokemons();
    }

    private void salvarListaBanco() {
        listarPokemonViewModel.salvarListaPokemons(resultList);
        listarPokemonViewModel.getMensagem().observe(getViewLifecycleOwner(), s -> {
            if (s.equals("sucesso")) {
                Mensagens.showSucesso(requireView(), getString(R.string.lista_salva_com_sucesso));
            }
        });
    }


    private void configurarRecyclerViews(List<Result> results) {
        Log.e("tamanho lista", String.valueOf(results.size()));

        if (results.isEmpty() || results == null || results.size() == 0) {
            binding.tvEmptyState.setVisibility(View.VISIBLE);
        } else {

            RecyclerView recyclerViewPokemon = binding.recyclerViewPokemons;

//         * o problema estava na orientação do Layout, que deveria ser VERTICAL,
//         * recyclerViewPokemon.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewPokemon.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

            recyclerViewPokemon.setAdapter(new ItemListarPokemonAdapter(results));
        }

        exibeProgresso(false);
    }

    private void exibeProgresso(boolean exibir) {

        if (exibir) {
            binding.loadingContainer.setVisibility(View.VISIBLE);
            binding.recyclerViewPokemons.setVisibility(View.GONE);
            binding.tvEmptyState.setVisibility(View.GONE);
        } else {
            binding.loadingContainer.setVisibility(View.GONE);
            binding.recyclerViewPokemons.setVisibility(View.VISIBLE);
            binding.tvEmptyState.setVisibility(View.GONE);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}