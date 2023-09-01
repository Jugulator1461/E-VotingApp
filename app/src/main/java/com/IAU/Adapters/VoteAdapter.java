package com.IAU.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.IAU.Entities.VoteEntity;
import com.IAU.Entities.VoteOptionEntity;
import com.IAU.myapplication.R;

import java.util.List;

public class VoteAdapter extends ArrayAdapter<VoteEntity> {

    public VoteAdapter(Context context, List<VoteEntity> voteEntities) {
        super(context, 0, voteEntities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VoteEntity vote = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_vote_list, parent, false);
        }
        TextView optionNameTextView = convertView.findViewById(R.id.optionNameTextView);
        optionNameTextView.setText(vote.getVoteName());

        return convertView;

        //VOTE ADAPTER YAZILACAK...
    }
}
