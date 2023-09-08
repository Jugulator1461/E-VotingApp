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
import android.widget.ArrayAdapter;
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
import com.IAU.myapplication.R;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
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

public class VoteSelectActivity extends AppCompatActivity {
    AnyChartView anyChartView;
    private VoteEntity voteEntity;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    //Oylanan seçeneğin arttırıldığı fonksiyon
    private void incrementOptionVoteCount(String optionNameToIncrement, String voteName2) {
        DatabaseReference optionsRef = FirebaseDatabase.getInstance("https://finishingproject-553e9-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference()
                .child(voteName2)
                .child("voteOption");
        // Query the database to find the option by its name
        Log.d("Firebase", "Before query");
        optionsRef.orderByChild("optionName").equalTo(optionNameToIncrement)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot optionSnapshot : dataSnapshot.getChildren()) {
                            // Get the current optionVoteCount

                            Long optionVoteCount = optionSnapshot.child("optionVoteCount").getValue(Long.class);
                            Log.d("Firebase", "Before null check: optionVoteCount = " + optionVoteCount);
                            // Increment the optionVoteCount by 1
                            if (optionVoteCount != null) {
                                optionVoteCount++;

                                // Update the optionVoteCount in the database
                                optionSnapshot.child("optionVoteCount").getRef().setValue(optionVoteCount);
                                Log.d("Firebase", "OptionVoteCount retrieved: " + optionVoteCount);

                                // Notify your adapter to update the ListView
                                setupPieChartView();
                                adapter.notifyDataSetChanged();

                                return;
                            }else {
                                // Handle the case where optionVoteCount is null
                                Log.d("Firebase", "OptionVoteCount is null");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error
                        Log.e("Firebase", "Error querying options: " + databaseError.getMessage());
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setTheme(R.style.Theme_MyApplication);
        setContentView(R.layout.activity_vote);
        listView = findViewById(R.id.listView);
        anyChartView = findViewById(R.id.pieChartView);
        Intent intent = getIntent();
        voteEntity = (VoteEntity) intent.getSerializableExtra("voteEnt");
        String voteName2 = voteEntity.getVoteName();
        setupPieChartView();
        List<String> voteOptionNames = new ArrayList<>();
        List<VoteOptionEntity> voteOptionEntities = voteEntity.getVoteOption();
        for (VoteOptionEntity optionEntity : voteOptionEntities) {
            voteOptionNames.add(optionEntity.getOptionName());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, voteOptionNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedOptionName = adapter.getItem(position);

                incrementOptionVoteCount(selectedOptionName,voteName2);
            }
        });


    }

    public void setupPieChartView() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        for (VoteOptionEntity optionEntity : voteEntity.getVoteOption()) {
            dataEntries.add(new ValueDataEntry(optionEntity.getOptionName(), optionEntity.getOptionVoteCount()));
        }
        pie.data(dataEntries);
        pie.title(voteEntity.getVoteName());
        anyChartView.setChart(pie);
    }
}