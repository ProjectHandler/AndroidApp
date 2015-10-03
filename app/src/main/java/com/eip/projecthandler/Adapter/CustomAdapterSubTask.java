package com.eip.projecthandler.Adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.eip.projecthandler.R;
import com.eip.projecthandler.models.SubTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomAdapterSubTask  extends BaseAdapter {

    Context context;
    List<SubTask> rowItems = new ArrayList<SubTask>();

    /* private view holder class */
    private class ViewHolder {
        CheckBox description;
    }

    public CustomAdapterSubTask(Context context, Set<SubTask> rowItems) {
        this.context = context;
        this.rowItems.addAll(rowItems);
        setRowItems(rowItems);
    }

    public void setRowItems(Set<SubTask> rowItems) {
        this.rowItems.clear();
        this.rowItems.addAll(rowItems);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.subtask_item, null);
            holder = new ViewHolder();
            holder.description = (CheckBox) convertView.findViewById(R.id.checkBox_subTask);

            setViewHolder(convertView, holder, position);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            setViewHolder(convertView, holder, position);
        }

        return convertView;
    }

    private void setViewHolder(View convertView, ViewHolder holder, int position) {
        SubTask row_pos = rowItems.get(position);

        holder.description.setChecked(row_pos.isValidated());
        holder.description.setText(row_pos.getDescription());
    }
}
