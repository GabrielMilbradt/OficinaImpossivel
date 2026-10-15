package com.example.oficinaimpossivel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oficinaimpossivel.databinding.ActivityCamaraTestesBinding;

public class CamaraTestesActivity extends AppCompatActivity {

    private ActivityCamaraTestesBinding binding;
    private String nomeInvencao;
    private String resultadoConfirmado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCamaraTestesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d(Constantes.TAG, "CamaraTestesActivity.onCreate");

        Intent intent = getIntent();
        nomeInvencao = intent.getStringExtra(Constantes.EXTRA_NOME);
        int imagem = intent.getIntExtra(Constantes.EXTRA_IMAGEM, 0);

        if (nomeInvencao == null || nomeInvencao.trim().isEmpty() || imagem == 0) {
            Toast.makeText(this, R.string.erro_dados_invalidos, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        binding.ivCamara.setImageResource(imagem);
        binding.ivCamara.setContentDescription(getString(R.string.cd_imagem_invencao, nomeInvencao));
        binding.tvNomeCamara.setText(nomeInvencao);

        binding.btnConfirmarTeste.setOnClickListener(v -> confirmarTeste());
        binding.btnReverFicha.setOnClickListener(v -> finish());
        binding.btnEncerrarAvaliacao.setOnClickListener(v -> encerrarAvaliacao());
    }

    private void confirmarTeste() {
        int marcado = binding.rgResultado.getCheckedRadioButtonId();
        String observacao = binding.etObservacao.getText() == null
                ? "" : binding.etObservacao.getText().toString().trim();
        boolean valido = true;

        if (marcado == View.NO_ID) {
            Toast.makeText(this, R.string.erro_resultado, Toast.LENGTH_SHORT).show();
            valido = false;
        }
        if (observacao.length() < 10) {
            binding.tilObservacao.setError(getString(R.string.erro_minimo_dez));
            valido = false;
        } else {
            binding.tilObservacao.setError(null);
        }
        if (!valido) {
            return;
        }

        int icone;
        if (marcado == binding.rbAprovada.getId()) {
            resultadoConfirmado = getString(R.string.resultado_aprovada);
            icone = R.drawable.aprovada;
        } else if (marcado == binding.rbPrecisaAjustes.getId()) {
            resultadoConfirmado = getString(R.string.resultado_precisa_ajustes);
            icone = R.drawable.ajustes;
        } else {
            resultadoConfirmado = getString(R.string.resultado_reprovada);
            icone = R.drawable.reprovada;
        }

        mostrarSelo(icone);
        binding.tvResultadoTeste.setText(getString(R.string.resultado_registrado, resultadoConfirmado));
        binding.tvResultadoTeste.setVisibility(View.VISIBLE);
        binding.btnEncerrarAvaliacao.setEnabled(true);
    }

    private void mostrarSelo(int icone) {
        binding.ivSelo.setImageResource(icone);
        binding.ivSelo.setContentDescription(getString(R.string.cd_selo, resultadoConfirmado));
        binding.ivSelo.setAlpha(0f);
        binding.ivSelo.setScaleX(0.2f);
        binding.ivSelo.setScaleY(0.2f);
        binding.ivSelo.setVisibility(View.VISIBLE);
        binding.ivSelo.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(600)
                .setInterpolator(new OvershootInterpolator())
                .start();
    }

    private void encerrarAvaliacao() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Constantes.EXTRA_ULTIMO_NOME, nomeInvencao);
        intent.putExtra(Constantes.EXTRA_ULTIMO_RESULTADO, resultadoConfirmado);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Constantes.TAG, "CamaraTestesActivity.onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Constantes.TAG, "CamaraTestesActivity.onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Constantes.TAG, "CamaraTestesActivity.onDestroy");
    }
}
