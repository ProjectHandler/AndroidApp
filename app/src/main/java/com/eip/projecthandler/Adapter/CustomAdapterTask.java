package com.eip.projecthandler.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eip.projecthandler.R;
import com.eip.projecthandler.models.Task;

import java.util.List;

public class CustomAdapterTask extends BaseAdapter {

    static float density;
    Context context;
    List<Task> rowItems;

    /* private view holder class */
    private class ViewHolder {
        TextView name;
        TextView progress;
        ImageView icon;
    }

    public CustomAdapterTask(Context context, List<Task> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
        density = context.getResources().getDisplayMetrics().density;
    }

    public void setRowItems(List<Task> rowItems) {
        this.rowItems.clear();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.task_item, null);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.progress = (TextView) convertView.findViewById(R.id.progress);
            holder.icon = (ImageView) convertView.findViewById(R.id.task_row_icon);

            setViewHolder(convertView, holder, position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            setViewHolder(convertView, holder, position);
        }
        return convertView;
    }

    private void setViewHolder(View convertView, ViewHolder holder, int position) {
        Task row_pos = rowItems.get(position);

        holder.name.setText(row_pos.getName());
        holder.progress.setText(row_pos.getProgress().toString() + "%");

        if (row_pos.getLevel() == 1) {
            holder.icon.setImageResource(R.drawable.ic_ph_bookmark_2);
            convertView.setPadding((int) (5 * density), (int) (5 * density), (int) (5 * density), (int) (5 * density));
        } else if (row_pos.getLevel() == 2) {
            holder.icon.setImageResource(R.drawable.ic_ph_clipboard);
            convertView.setPadding((int) (30 * density), 0, (int) (5 * density), (int) (5 * density));
        } else {
            convertView.setPadding((int) (5 * density), (int) (5 * density), (int) (5 * density), (int) (5 * density));
        }
    }
}
