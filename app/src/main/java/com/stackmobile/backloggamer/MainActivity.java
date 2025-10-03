package com.stackmobile.backloggamer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.stackmobile.backloggamer.adapter.GameAdapter;
import com.stackmobile.backloggamer.model.Game;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private List<Game> gameList;
    private GameAdapter gameAdapter;

    private static final int ADD_GAME_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        gameList = new ArrayList<>();
        gameAdapter = new GameAdapter(gameList);


        RecyclerView recyclerView = findViewById(R.id.recyclerViewGames);
        recyclerView.setAdapter(gameAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fabAddGame = findViewById(R.id.fabAddGame);
        fabAddGame.setOnClickListener(v -> {
            startActivityForResult(
                    new Intent(MainActivity.this, AddGameActivity.class),
                    ADD_GAME_REQUEST
            );
        });


        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });


        loadGamesFromFirestore();
    }

    private void loadGamesFromFirestore() {
        if (mAuth.getCurrentUser() == null) return;

        String userId = mAuth.getCurrentUser().getUid();
        db.collection("games")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    gameList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Game game = doc.toObject(Game.class);
                        if (game != null) gameList.add(game);
                    }
                    gameAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(MainActivity.this, "Erro ao carregar jogos", Toast.LENGTH_SHORT).show()
                );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_GAME_REQUEST && resultCode == RESULT_OK && data != null) {
            String nome = data.getStringExtra("nome");
            String plataforma = data.getStringExtra("plataforma");
            String status = data.getStringExtra("status");

            String userId = mAuth.getCurrentUser().getUid();
            Game newGame = new Game(nome, plataforma, status, null, userId);

            db.collection("games").add(newGame)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Jogo adicionado!", Toast.LENGTH_SHORT).show();
                        loadGamesFromFirestore();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Erro ao adicionar jogo", Toast.LENGTH_SHORT).show()
                    );
        }
    }
}
