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
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
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

public class VoteSelectActivity extends AppCompatActivity {
    AnyChartView anyChartView;
    VoteEntity voteEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        anyChartView = findViewById(R.id.pieChartView);
        Intent intent = getIntent();
        voteEntity = (VoteEntity) intent.getSerializableExtra("voteEnt");
        setupPieChartView();
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

