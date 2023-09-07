package com.IAU.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_option_select, parent, false);
        }

        TextView optionNameTextView = convertView.findViewById(R.id.optionNameTextView2);
        ToggleButton toggleButton = convertView.findViewById(R.id.voteToggleButton);

        optionNameTextView.setText(option.getOptionName());
        toggleButton.setTag(option.getOptionName());

        return convertView;
    }
}
