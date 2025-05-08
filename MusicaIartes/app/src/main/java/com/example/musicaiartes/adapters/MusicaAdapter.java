package com.example.musicaiartes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicaiartes.R;
import com.example.musicaiartes.models.Musica;

import java.util.List;

public class MusicaAdapter extends RecyclerView.Adapter<MusicaAdapter.MusicaViewHolder> {

    private final List<Musica> musicas;
    private final OnMusicaClickListener listener;

    public interface OnMusicaClickListener {
        void onMusicaClick(Musica musica);
    }

    public MusicaAdapter(List<Musica> musicas, OnMusicaClickListener listener) {
        this.musicas = musicas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MusicaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_musica, parent, false);
        return new MusicaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicaViewHolder holder, int position) {
        Musica musica = musicas.get(position);
        holder.textNome.setText(musica.getName());
        holder.textArtista.setText(musica.getArtist());


        Glide.with(holder.itemView.getContext())
                .load(musica.getImageUrl())
                .placeholder(R.drawable.placeholder_music) // imagem padrão
                .into(holder.imageAlbum);

        holder.itemView.setOnClickListener(v -> listener.onMusicaClick(musica));
    }

    @Override
    public int getItemCount() {
        return musicas.size();
    }

    public static class MusicaViewHolder extends RecyclerView.ViewHolder {
        TextView textNome, textArtista;
        ImageView imageAlbum;

        public MusicaViewHolder(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.textNomeMusica);
            textArtista = itemView.findViewById(R.id.textArtistaMusica);
            imageAlbum = itemView.findViewById(R.id.imageMusica);
        }
    }
}
