package com.IAU.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.IAU.Entities.VoteOptionEntity;
import com.IAU.myapplication.R;

import java.util.List;

public class OptionAdapter extends ArrayAdapter<VoteOptionEntity> {

    public OptionAdapter(Context context, List<VoteOptionEntity> options) {
        super(context, 0, options);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VoteOptionEntity option = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_option_create_row, parent, false);
        }

        TextView optionNameTextView = convertView.findViewById(R.id.optionNameTextView);
        TextView optionVoteCountTextView = convertView.findViewById(R.id.optionVoteCountTextView);

        optionNameTextView.setText(option.getOptionName());
        optionVoteCountTextView.setText(String.valueOf(option.getOptionVoteCount()));

        return convertView;
    }
}
