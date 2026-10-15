package com.example.oficinaimpossivel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oficinaimpossivel.databinding.ItemInvencaoGradeBinding;
import com.example.oficinaimpossivel.databinding.ItemInvencaoListaBinding;

import java.util.List;

public class InvencaoAdapter extends RecyclerView.Adapter<InvencaoAdapter.InvencaoViewHolder> {

    public interface OnInvencaoListener {
        void onInvencaoClick(Invencao invencao);
        void onInvencaoLongClick(Invencao invencao, int posicao);
    }

    private static final int TIPO_LISTA = 0;
    private static final int TIPO_GRADE = 1;

    private final List<Invencao> invencoes;
    private final OnInvencaoListener listener;
    private boolean modoGrade = false;

    public InvencaoAdapter(List<Invencao> invencoes, OnInvencaoListener listener) {
        this.invencoes = invencoes;
        this.listener = listener;
    }

    public void setModoGrade(boolean modoGrade) {
        this.modoGrade = modoGrade;
        notifyDataSetChanged(); // troca o tipo de layout de todos os itens
    }

    @Override
    public int getItemViewType(int position) {
        return modoGrade ? TIPO_GRADE : TIPO_LISTA;
    }

    @NonNull
    @Override
    public InvencaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TIPO_GRADE) {
            ItemInvencaoGradeBinding b = ItemInvencaoGradeBinding.inflate(inflater, parent, false);
            return new InvencaoViewHolder(b.getRoot(), b.ivInvencao, b.tvNomeInvencao, b.tvCategoriaInvencao);
        }
        ItemInvencaoListaBinding b = ItemInvencaoListaBinding.inflate(inflater, parent, false);
        return new InvencaoViewHolder(b.getRoot(), b.ivInvencao, b.tvNomeInvencao, b.tvCategoriaInvencao);
    }

    @Override
    public void onBindViewHolder(@NonNull InvencaoViewHolder holder, int position) {
        Invencao invencao = invencoes.get(position);
        holder.ivInvencao.setImageResource(invencao.getImagemRes());
        holder.ivInvencao.setContentDescription(
                holder.itemView.getContext().getString(R.string.cd_imagem_invencao, invencao.getNome()));
        holder.tvNome.setText(invencao.getNome());
        holder.tvCategoria.setText(invencao.getCategoria());

        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                listener.onInvencaoClick(invencoes.get(pos));
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                listener.onInvencaoLongClick(invencoes.get(pos), pos);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return invencoes.size();
    }

    static class InvencaoViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivInvencao;
        final TextView tvNome;
        final TextView tvCategoria;

        InvencaoViewHolder(@NonNull View raiz, ImageView ivInvencao, TextView tvNome, TextView tvCategoria) {
            super(raiz);
            this.ivInvencao = ivInvencao;
            this.tvNome = tvNome;
            this.tvCategoria = tvCategoria;
        }
    }
}
