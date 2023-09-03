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
import android.widget.AdapterView;
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
import com.anychart.AnyChartView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VoteListActivity extends AppCompatActivity {
    AnyChartView anyChartView;
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
                for (DataSnapshot anketSnapshot : snapshot.getChildren()) {
                    VoteEntity voteEnt = new VoteEntity();
                    voteEnt.setVoteName((String) anketSnapshot.child("voteName").getValue());
                    voteEnt.setStatus((long) anketSnapshot.child("status").getValue());
                    List<VoteOptionEntity> voteOptionEntityList = new ArrayList<>();
                    DataSnapshot voteOptionSnapshot = anketSnapshot.child("voteOption");
                    for (DataSnapshot optionSnapshot : voteOptionSnapshot.getChildren()) {
                        VoteOptionEntity optionEntity = new VoteOptionEntity();
                        optionEntity.setOptionName((String) optionSnapshot.child("optionName").getValue());
                        optionEntity.setOptionVoteCount((long) optionSnapshot.child("optionVoteCount").getValue());
                        voteOptionEntityList.add(optionEntity);
                    }
                    voteEnt.setVoteOption(voteOptionEntityList);
                    voteEntityList.getVoteEntityList().add(voteEnt);
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VoteListActivity.this, "Veri Aktarımı Başarısız", Toast.LENGTH_SHORT).show();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VoteEntity selectedVoteEntity = (VoteEntity) parent.getItemAtPosition(position);
                Intent intent = new Intent(VoteListActivity.this, VoteSelectActivity.class);
                intent.putExtra("voteEnt", selectedVoteEntity);
                startActivity(intent);
            }
        });

    }
}
