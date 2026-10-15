package com.example.oficinaimpossivel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oficinaimpossivel.databinding.ActivityFichaBinding;

public class FichaActivity extends AppCompatActivity {

    private ActivityFichaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFichaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d(Constantes.TAG, "FichaActivity.onCreate");

        Intent intent = getIntent();
        String nome = intent.getStringExtra(Constantes.EXTRA_NOME);
        String inventor = intent.getStringExtra(Constantes.EXTRA_INVENTOR);
        String categoria = intent.getStringExtra(Constantes.EXTRA_CATEGORIA);
        String descricao = intent.getStringExtra(Constantes.EXTRA_DESCRICAO);
        int imagem = intent.getIntExtra(Constantes.EXTRA_IMAGEM, 0);

        if (vazio(nome) || vazio(inventor) || vazio(categoria) || imagem == 0) {
            Toast.makeText(this, R.string.erro_dados_invalidos, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        if (vazio(descricao)) {
            descricao = getString(R.string.descricao_padrao_usuario, inventor);
        }

        binding.ivFicha.setImageResource(imagem);
        binding.ivFicha.setContentDescription(getString(R.string.cd_imagem_invencao, nome));
        binding.tvNomeFicha.setText(nome);
        binding.tvInventorFicha.setText(getString(R.string.rotulo_inventor, inventor));
        binding.tvCategoriaFicha.setText(getString(R.string.rotulo_categoria, categoria));
        binding.tvDescricaoFicha.setText(descricao);

        final String nomeFinal = nome;
        final String inventorFinal = inventor;
        final String categoriaFinal = categoria;
        final String descricaoFinal = descricao;
        final int imagemFinal = imagem;

        binding.btnVoltarFeira.setOnClickListener(v -> finish());
        binding.btnTestarPrototipo.setOnClickListener(v -> {
            Intent proxima = new Intent(this, CamaraTestesActivity.class);
            proxima.putExtra(Constantes.EXTRA_NOME, nomeFinal);
            proxima.putExtra(Constantes.EXTRA_INVENTOR, inventorFinal);
            proxima.putExtra(Constantes.EXTRA_CATEGORIA, categoriaFinal);
            proxima.putExtra(Constantes.EXTRA_DESCRICAO, descricaoFinal);
            proxima.putExtra(Constantes.EXTRA_IMAGEM, imagemFinal);
            startActivity(proxima);
        });

        Animation entrada = AnimationUtils.loadAnimation(this, R.anim.ficha_entrada);
        binding.scrollFicha.startAnimation(entrada);
    }

    private boolean vazio(String s) {
        return s == null || s.trim().isEmpty();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Constantes.TAG, "FichaActivity.onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Constantes.TAG, "FichaActivity.onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Constantes.TAG, "FichaActivity.onDestroy");
    }
}
