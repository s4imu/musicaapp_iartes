package com.example.musicaiartes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.musicaiartes.R;
import com.example.musicaiartes.activities.TrackDetailActivity;
import com.example.musicaiartes.models.TrackItem;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {
    private Context context;
    private List<TrackItem> trackList;

    public TrackAdapter(Context context, List<TrackItem> trackList) {
        this.context = context;
        this.trackList = trackList;
    }

    public void updateList(List<TrackItem> newList) {
        this.trackList = newList;
        notifyDataSetChanged();
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_track, parent, false);
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        TrackItem item = trackList.get(position);
        holder.txtTrack.setText(item.trackName);
        holder.txtArtist.setText("Artista: " + item.artistName);
        holder.txtAlbum.setText("Álbum: " + item.albumName);
        holder.txtDuration.setText("Duração: " + item.duration);

        Glide.with(context).load(item.imageUrl).into(holder.imgAlbum);

        // 👉 Abre a tela de detalhes da música
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TrackDetailActivity.class);
            intent.putExtra("track", item); // TrackItem deve implementar Serializable
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public static class TrackViewHolder extends RecyclerView.ViewHolder {
        TextView txtTrack, txtArtist, txtAlbum, txtDuration;
        ImageView imgAlbum;

        public TrackViewHolder(View itemView) {
            super(itemView);
            txtTrack = itemView.findViewById(R.id.txtTrack);
            txtArtist = itemView.findViewById(R.id.txtArtist);
            txtAlbum = itemView.findViewById(R.id.txtAlbum);
            txtDuration = itemView.findViewById(R.id.txtDuration);
            imgAlbum = itemView.findViewById(R.id.imgAlbum);
        }
    }
}
