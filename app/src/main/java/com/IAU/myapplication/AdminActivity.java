package com.IAU.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.IAU.Entities.VoteEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    ListView listView;
    List<VoteEntity> voteEntityList;
    VoteEntity vote;
    Button createVoteBtn, showVotesBtn, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        createVoteBtn = findViewById(R.id.createVote);
        showVotesBtn = findViewById(R.id.showVotes);
        logoutBtn = findViewById(R.id.logout);

        createVoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, VoteActivity.class);
                startActivity(intent);
            }
        });

        showVotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TÜM ANKETLERİN GÖSTERİLDİĞİ SAYFA - OY SAYILARIYLA BİRLİKTE - ADMİNDE DE USERDA DA ORTAK CALISACAK
                Intent intent = new Intent(AdminActivity.this, VoteActivity.class);
                startActivity(intent);
            }
        });
    }


}
