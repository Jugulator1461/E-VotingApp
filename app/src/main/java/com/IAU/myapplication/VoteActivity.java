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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VoteActivity extends AppCompatActivity {
    List<VoteOptionEntity> voteEntityList;
    VoteEntity vote;

    private Button addOptionButton;
    private ImageButton confirmVote;
    private EditText txtAddOptionName;
    private ListView listView;
    private OptionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_create);

        confirmVote = findViewById(R.id.confirmVote);
        addOptionButton = findViewById(R.id.addOption);
        txtAddOptionName = findViewById(R.id.txtAddOption);

        if(voteEntityList == null){
            voteEntityList = new ArrayList<>();
        }
        adapter = new OptionAdapter(this, voteEntityList);
        listView = findViewById(R.id.listViewVoteOption);
        listView.setAdapter(adapter);

        if(vote != null){
            final TextView voteNameTextView = (TextView) findViewById(R.id.voteNameTextView);
            voteNameTextView.setText(vote.getVoteName());
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
            }
        });
        confirmVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vote.setVoteOption(voteEntityList);
                //vote burada db ye commitlenecek.
            }
        });
    }
}
