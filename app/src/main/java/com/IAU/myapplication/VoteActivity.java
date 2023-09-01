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
import com.IAU.Entities.VoteEntity;
import com.IAU.Entities.VoteOptionEntity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VoteActivity extends AppCompatActivity {
    List<VoteOptionEntity> voteEntityList;
    VoteEntity vote;

    private Button addOptionButton;
    private ImageButton confirmVote;
    private EditText txtAddOptionName, voteNameTextView;
    private ListView listView;
    private OptionAdapter adapter;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_create);

        confirmVote = findViewById(R.id.confirmVote);
        addOptionButton = findViewById(R.id.addOption);
        txtAddOptionName = findViewById(R.id.txtAddOption);
        voteNameTextView = findViewById(R.id.voteNameTextView);


        if (voteEntityList == null) {
            voteEntityList = new ArrayList<>();
        }
        adapter = new OptionAdapter(this, voteEntityList);
        listView = findViewById(R.id.listViewVoteOption);
        listView.setAdapter(adapter);

        if (vote != null) {
            final TextView voteNameTextView = (TextView) findViewById(R.id.voteNameTextView);
            voteNameTextView.setText(vote.getVoteName());
        } else {
            vote = new VoteEntity();
            vote.setVoteName(voteNameTextView.getText().toString());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VoteOptionEntity option = new VoteOptionEntity();
                option.setOptionName(txtAddOptionName.getText().toString());
                option.setOptionVoteCount(0l);
                voteEntityList.add(option);
                listView.setAdapter(adapter);
                if (!vote.getVoteName().equals(voteNameTextView.getText().toString())) {
                    vote.setVoteName(voteNameTextView.getText().toString());
                }
                txtAddOptionName.setText("");
            }
        });
        confirmVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://finishingproject-553e9-default-rtdb.europe-west1.firebasedatabase.app/");
                if (!vote.getVoteName().equals(voteNameTextView.getText().toString())) {
                    vote.setVoteName(voteNameTextView.getText().toString());
                }
                vote.setVoteOption(voteEntityList);
                vote.setStatus(1l);

                DatabaseReference databaseReference = database.getReference().child(vote.getVoteName() + "");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            VoteEntity dbVote = snapshot.getValue(VoteEntity.class);
                            if (dbVote.getStatus().equals(1l)) {
                                //BU İSME AİT AKTİF ANKET VAR
                                Toast.makeText(VoteActivity.this, "Anket ismine ait aktif anket var. Anket ismini değiştirip tekrar deneyin.", Toast.LENGTH_SHORT).show();
                            } else {
                                //BU ANKET OLUŞTURULABİLİR.
                                anketOlustur(database);
                            }
                        } else {
                            //BU ANKET OLUŞTURULABİLİR.
                            anketOlustur(database);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("sa", "errrr operatiossss!");
                    }
                });
            }
        });
    }

    public void anketOlustur(FirebaseDatabase database) {
        // Bu kısım write operation yapıyor şuanlık sadece basit mesaj yazdırıldı
        DatabaseReference myRef = database.getReference(vote.getVoteName() + "");
        myRef.setValue(vote)
                .addOnSuccessListener(aVoid -> {
                    // Data was successfully written to the database
                    // This block is executed on success
                    Log.d("Database", "Write operation succeeded!");
                    Toast.makeText(VoteActivity.this, "Anket ismine ait aktif anket var. Anket ismini değiştirip tekrar deneyin.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VoteActivity.this, AdminActivity.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    // An error occurred while writing data to the database
                    // This block is executed on failure
                    Toast.makeText(VoteActivity.this, "addOnFailure", Toast.LENGTH_SHORT).show();
                    Log.e("Database", "Write operation failed: " + e.getMessage());
                });
    }
}
