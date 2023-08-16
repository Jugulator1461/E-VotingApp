package com.IAU.DAO;

import com.IAU.Entities.VoteEntity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DAOVote {
    private DatabaseReference databaseReference;
    public DAOVote(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("VOTE");
    }

    public Task<Void> add(VoteEntity voteEnt){
       return databaseReference.push().setValue(voteEnt);
    }
}
