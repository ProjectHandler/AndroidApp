package com.eip.projecthandler.Adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.eip.projecthandler.R;
import com.eip.projecthandler.models.MobileSubTaskDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomAdapterSubTask  extends BaseAdapter {

    Context context;
    List<MobileSubTaskDTO> rowItems = new ArrayList<MobileSubTaskDTO>();

    public CustomAdapterSubTask(Context context, Set<MobileSubTaskDTO> rowItems) {
        this.context = context;
        this.rowItems.addAll(rowItems);
        setRowItems(rowItems);
    }

    public void setRowItems(Set<MobileSubTaskDTO> rowItems) {
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

    /* private view holder class */
    private class ViewHolder {
        TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.subtask_item, null);
            holder = new ViewHolder();

            holder.name = (CheckBox) convertView.findViewById(R.id.checkBox_subTask);

            MobileSubTaskDTO row_pos = rowItems.get(position);

            holder.name.setText(row_pos.getDescription());

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

}
