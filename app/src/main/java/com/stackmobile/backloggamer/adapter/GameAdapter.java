package com.stackmobile.backloggamer.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stackmobile.backloggamer.R;
import com.stackmobile.backloggamer.model.Game;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private final List<Game> gameList;

    public GameAdapter(List<Game> gameList) {
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = gameList.get(position);

        holder.tvNome.setText(game.getNome());
        holder.tvPlataforma.setText(game.getPlataforma());
        holder.tvStatus.setText(game.getStatus());


        GradientDrawable bg = (GradientDrawable) holder.tvStatus.getBackground();

        switch (game.getStatus()) {
            case "Jogando":
                bg.setColor(Color.parseColor("#2196F3")); // Azul
                break;
            case "Zerado":
                bg.setColor(Color.parseColor("#4CAF50")); // Verde
                break;
            case "Quero Jogar":
                bg.setColor(Color.parseColor("#FFC107")); // Amarelo
                break;
            default:
                bg.setColor(Color.parseColor("#666666")); // Cinza
                break;
        }


        if (game.getCapaUrl() != null && !game.getCapaUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(game.getCapaUrl())
                    .into(holder.imgCapa);
        } else {
            holder.imgCapa.setImageResource(R.drawable.default_cover);
        }
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvPlataforma, tvStatus;
        ImageView imgCapa;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNome);
            tvPlataforma = itemView.findViewById(R.id.tvPlataforma);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            imgCapa = itemView.findViewById(R.id.imgCapa);
        }
    }
}
