package com.eip.projecthandler.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eip.projecthandler.R;
import com.eip.projecthandler.models.Data;

import java.util.List;

public class CustomAdapterData extends BaseAdapter {

    Context context;
    List<Data> data;

    /* private view holder class */
    private class ViewHolder {
        TextView id;
        TextView value;
    }

    public CustomAdapterData(Context context, List<Data> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.data_item, null);
            holder = new ViewHolder();

            holder.value = (TextView) convertView.findViewById(R.id.dataValue);
            holder.id = (TextView) convertView.findViewById(R.id.dataId);

            setViewHolder(convertView, holder, position);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            setViewHolder(convertView, holder, position);
        }

        return convertView;
    }

    private void setViewHolder(View convertView, ViewHolder holder, int position) {
        Data row_pos = data.get(position);

        holder.id.setText(row_pos.getId().toString());
        holder.value.setText(row_pos.getValue());
    }
}
