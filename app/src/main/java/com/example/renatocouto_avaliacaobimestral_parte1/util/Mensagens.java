package com.example.renatocouto_avaliacaobimestral_parte1.util;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;

// Classe para mensagens de erro, alerta e sucesso,
// deixei tudo comentado para consultas futuras
public class Mensagens {

    // Tempo padrão do Snackbar (em milissegundos)
    private static final int TEMPO_PADRAO = 1200;

    public static void showErro(View view, String mensagem) {
        exibirSnackbar(view, mensagem, "#F44336"); // Vermelho para erro
    }

    public static void showAlerta(View view, String mensagem) {
        exibirSnackbar(view, mensagem, "#FFC107"); // Âmbar para alerta
    }

    public static void showSucesso(View view, String mensagem) {
        exibirSnackbar(view, mensagem, "#4CAF50"); // Verde para sucesso
    }

    // Snackbar com cor personalizada
    private static void exibirSnackbar(View view, String mensagem, String corHex) {
        Snackbar snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_LONG);

        // Configura a cor de fundo do Snackbar usando o código hexadecimal
        snackbar.setBackgroundTint(android.graphics.Color.parseColor(corHex));

        // Centraliza o Snackbar (se necessário)
        View snackbarView = snackbar.getView();
        FrameLayout.LayoutParams params;

        if (snackbarView.getLayoutParams() instanceof FrameLayout.LayoutParams) {
            // Obtém as dimensões para layouts baseados em FrameLayout
            params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
        } else {
            // Fallback para outros tipos de layout
            params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

        params.gravity = Gravity.CENTER;
        snackbarView.setLayoutParams(params);

        // Define a duração do Snackbar
        snackbar.setDuration(TEMPO_PADRAO);

        // Exibe o Snackbar
        snackbar.show();
    }
}
