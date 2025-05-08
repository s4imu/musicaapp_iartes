package com.example.musicaiartes.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import com.example.musicaiartes.R;
import com.example.musicaiartes.models.Playlist;

import java.util.List;
import android.util.Log;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    private final Context context;
    private final List<Playlist> playlists;
    private final OnPlaylistClickListener listener;

    public interface OnPlaylistClickListener {
        void onPlaylistClick(Playlist playlist);
    }

    public PlaylistAdapter(Context context, List<Playlist> playlists, OnPlaylistClickListener listener) {
        this.context = context;
        this.playlists = playlists;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_playlist, parent, false);
        return new PlaylistViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        holder.nameTextView.setText(playlist.getName());

        String imageUrl = playlist.getImageUrl();

        // Carregar a imagem da URL usando Glide
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_music)  // Imagem padrão enquanto carrega
                .error(R.drawable.placeholder_music)       // Imagem padrão em caso de erro
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> listener.onPlaylistClick(playlist));
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.playlistName);
            imageView = itemView.findViewById(R.id.playlistImage);
        }
    }
}
