package com.example.renatocouto_avaliacaobimestral_parte1.ui.listarpokemon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renatocouto_avaliacaobimestral_parte1.R;
import com.example.renatocouto_avaliacaobimestral_parte1.entity.Result;

import java.util.List;

public class ItemListarPokemonAdapter extends RecyclerView.Adapter<ItemListarPokemonAdapter.ViewHolder> {

    private final List<Result> results;

    /**
     *acrescentei a checagem de resultado nulo, para evitar um erro de NullPointerException
     * */
    public ItemListarPokemonAdapter(List<Result> results) {
        this.results = results != null ? results : List.of();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = results.get(position);
        holder.textViewNome.setText(result.getName());
        holder.textViewUrl.setText(result.getUrl());
        holder.textViewId.setText(String.valueOf(result.getId()));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textViewNome;
        final TextView textViewUrl;
        final TextView textViewId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.text_view_nome);
            textViewUrl = itemView.findViewById(R.id.text_view_url);
            textViewId = itemView.findViewById(R.id.text_view_id);
        }
    }
}
