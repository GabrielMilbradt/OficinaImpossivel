package com.example.oficinaimpossivel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.oficinaimpossivel.databinding.ActivityMainBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements InvencaoAdapter.OnInvencaoListener {

    private ActivityMainBinding binding;
    private final List<Invencao> invencoes = new ArrayList<>();
    private InvencaoAdapter adapter;
    private boolean modoGrade = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d(Constantes.TAG, "MainActivity.onCreate");

        carregarInvencoesIniciais();
        configurarSpinner();
        configurarLista();

        binding.btnCadastrar.setOnClickListener(v -> cadastrar());
        binding.btnAlternarLayout.setOnClickListener(v -> alternarLayout());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Constantes.TAG, "MainActivity.onResume");
        atualizarQuantidade();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Constantes.TAG, "MainActivity.onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Constantes.TAG, "MainActivity.onDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.d(Constantes.TAG, "MainActivity.onNewIntent");

        String nome = intent.getStringExtra(Constantes.EXTRA_ULTIMO_NOME);
        String resultado = intent.getStringExtra(Constantes.EXTRA_ULTIMO_RESULTADO);
        if (nome != null && resultado != null) {
            binding.tvUltimoTeste.setText(getString(R.string.ultimo_teste, nome, resultado));
            binding.tvUltimoTeste.setVisibility(View.VISIBLE);
        }
    }

    private void carregarInvencoesIniciais() {
        invencoes.add(new Invencao(getString(R.string.inv1_nome), getString(R.string.inv1_inventor),
                getString(R.string.categoria_clima), getString(R.string.inv1_descricao),
                R.drawable.chapeu_solar));
        invencoes.add(new Invencao(getString(R.string.inv2_nome), getString(R.string.inv2_inventor),
                getString(R.string.categoria_cozinha), getString(R.string.inv2_descricao),
                R.drawable.torradeira));
        invencoes.add(new Invencao(getString(R.string.inv3_nome), getString(R.string.inv3_inventor),
                getString(R.string.categoria_clima), getString(R.string.inv3_descricao),
                R.drawable.guarda_chuva));
        invencoes.add(new Invencao(getString(R.string.inv4_nome), getString(R.string.inv4_inventor),
                getString(R.string.categoria_transporte), getString(R.string.inv4_descricao),
                R.drawable.sapato_foguete));
        invencoes.add(new Invencao(getString(R.string.inv5_nome), getString(R.string.inv5_inventor),
                getString(R.string.categoria_diversao), getString(R.string.inv5_descricao),
                R.drawable.bolhas));
    }

    private void configurarSpinner() {
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.categorias, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCategoria.setAdapter(spinnerAdapter);
    }

    private void configurarLista() {
        adapter = new InvencaoAdapter(invencoes, this);
        binding.rvInvencoes.setLayoutManager(new LinearLayoutManager(this));
        binding.rvInvencoes.setAdapter(adapter);
    }

    private void cadastrar() {
        String nome = texto(binding.etNomeInvencao);
        String inventor = texto(binding.etNomeInventor);
        boolean valido = true;

        if (nome.length() < 3) {
            binding.tilNomeInvencao.setError(getString(R.string.erro_minimo_tres));
            valido = false;
        } else {
            binding.tilNomeInvencao.setError(null);
        }

        if (inventor.length() < 3) {
            binding.tilNomeInventor.setError(getString(R.string.erro_minimo_tres));
            valido = false;
        } else {
            binding.tilNomeInventor.setError(null);
        }

        if (binding.spinnerCategoria.getSelectedItemPosition() == 0) {
            Toast.makeText(this, R.string.erro_categoria, Toast.LENGTH_SHORT).show();
            valido = false;
        }

        if (!valido) {
            return;
        }

        String categoria = binding.spinnerCategoria.getSelectedItem().toString();
        Invencao nova = new Invencao(nome, inventor, categoria,
                getString(R.string.descricao_padrao_usuario, inventor),
                R.drawable.padrao);

        invencoes.add(nova);
        int posicao = invencoes.size() - 1;
        adapter.notifyItemInserted(posicao);
        binding.rvInvencoes.smoothScrollToPosition(posicao);

        binding.etNomeInvencao.setText("");
        binding.etNomeInventor.setText("");
        binding.spinnerCategoria.setSelection(0);
        binding.etNomeInvencao.requestFocus();
        atualizarQuantidade();
        Toast.makeText(this, R.string.invencao_cadastrada, Toast.LENGTH_SHORT).show();
    }

    private void alternarLayout() {
        modoGrade = !modoGrade;
        binding.rvInvencoes.setLayoutManager(
                modoGrade ? new GridLayoutManager(this, 2) : new LinearLayoutManager(this));
        adapter.setModoGrade(modoGrade);
        binding.btnAlternarLayout.setText(modoGrade ? R.string.botao_ver_lista : R.string.botao_ver_grade);
    }

    private void atualizarQuantidade() {
        binding.tvQuantidade.setText(getString(R.string.quantidade_prototipos, invencoes.size()));
    }

    private String texto(TextInputEditText campo) {
        return campo.getText() == null ? "" : campo.getText().toString().trim();
    }

    @Override
    public void onInvencaoClick(Invencao invencao) {
        Intent intent = new Intent(this, FichaActivity.class);
        intent.putExtra(Constantes.EXTRA_NOME, invencao.getNome());
        intent.putExtra(Constantes.EXTRA_INVENTOR, invencao.getInventor());
        intent.putExtra(Constantes.EXTRA_CATEGORIA, invencao.getCategoria());
        intent.putExtra(Constantes.EXTRA_DESCRICAO, invencao.getDescricao());
        intent.putExtra(Constantes.EXTRA_IMAGEM, invencao.getImagemRes());
        startActivity(intent);
    }

    @Override
    public void onInvencaoLongClick(Invencao invencao, int posicao) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.titulo_remover)
                .setMessage(getString(R.string.mensagem_remover, invencao.getNome()))
                .setPositiveButton(R.string.botao_remover, (dialog, which) -> {
                    invencoes.remove(posicao);
                    adapter.notifyItemRemoved(posicao);
                    atualizarQuantidade();
                    Toast.makeText(this, R.string.invencao_removida, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.botao_cancelar, null)
                .show();
    }
}
