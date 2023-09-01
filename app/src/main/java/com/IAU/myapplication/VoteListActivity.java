package com.IAU.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.IAU.Adapters.OptionAdapter;
import com.IAU.Adapters.VoteDisplayAdapter;
import com.IAU.Entities.VoteEntity;
import com.IAU.Entities.VoteEntityList;
import com.IAU.Entities.VoteOptionEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VoteListActivity extends AppCompatActivity {
    VoteEntityList voteEntityList;
    VoteEntity vote;

    private ListView listView;
    private VoteDisplayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_list);
        voteEntityList = new VoteEntityList();
        List<VoteEntity> voteList = new ArrayList<>();
        voteEntityList.setVoteEntityList(voteList);
        adapter = new VoteDisplayAdapter(this, voteEntityList.getVoteEntityList());
        listView = findViewById(R.id.listViewVote);
        listView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://finishingproject-553e9-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference databaseReference = database.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Map<String, VoteEntity> td = (HashMap<String, VoteEntity>) snapshot.getValue();
                    td.get("0");
                    for (int i = 0; i < td.size(); i++) {
                        if(td.containsKey(String.valueOf(i)) && td.get(String.valueOf(i)) instanceof VoteEntity){
                            VoteEntity vote = (VoteEntity) td.get(String.valueOf(i));
                            Toast.makeText(VoteListActivity.this, vote.getVoteName()+"Veri Aktarımı Başarısız", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VoteListActivity.this, "Veri Aktarımı Başarısız", Toast.LENGTH_SHORT).show();

            }
        });

        //VAR OLAN ANKETLER LİSTE OLARAK ÇEKİLECEK VE ÖNYÜZE AKTARILACAK.
    }
}
