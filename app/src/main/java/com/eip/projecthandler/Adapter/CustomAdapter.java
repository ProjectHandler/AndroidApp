package com.eip.projecthandler.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eip.projecthandler.R;
import com.eip.projecthandler.helpers.util;
import com.eip.projecthandler.models.Project;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<Project> rowItems;

    public CustomAdapter(Context context, List<Project> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    public void setRowItems(List<Project> rowItems) {
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    /* private view holder class */
    private class ViewHolder {
        TextView name;
        TextView description;
        TextView date_end;
        TextView progress;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.date_end = (TextView) convertView.findViewById(R.id.date_end);
            holder.progress = (TextView) convertView.findViewById(R.id.progress);

            Project row_pos = rowItems.get(position);

            holder.name.setText(row_pos.getName());
            holder.description.setText(row_pos.getDescription());
            //holder.date_end.setText(row_pos.getDateEnd().toString());
            holder.date_end.setText(util.getDateString(context, row_pos.getDateEnd()));
            holder.progress.setText(row_pos.getProgress().toString() + "%");

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

}