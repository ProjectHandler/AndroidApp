package com.eip.projecthandler.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eip.projecthandler.R;
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
        //ImageView profile_pic;
        //TextView member_name;
        //TextView status;
        //TextView contactType;

        TextView idName;
        TextView description;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.idName = (TextView) convertView
                    .findViewById(R.id.name);
            holder.description = (TextView) convertView
                    .findViewById(R.id.description);

            Project row_pos = rowItems.get(position);

            holder.idName.setText(row_pos.getName());
            holder.description.setText(row_pos.getDescription());

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

}