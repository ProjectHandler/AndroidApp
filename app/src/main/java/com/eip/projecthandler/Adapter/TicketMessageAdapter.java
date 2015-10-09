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
import com.eip.projecthandler.models.TicketMessage;

import java.util.List;

public class TicketMessageAdapter extends BaseAdapter {

    Context context;
    List<TicketMessage> rowItems;

    /* private view holder class */
    private class ViewHolder {
        TextView message;
        TextView date;
        TextView autor;
    }

    public TicketMessageAdapter(Context context, List<TicketMessage> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    public void setRowItems(List<TicketMessage> rowItems) {
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
            convertView = mInflater.inflate(R.layout.message_ticket_item, null);
            holder = new ViewHolder();

            holder.message = (TextView) convertView.findViewById(R.id.message);
            holder.date = (TextView) convertView.findViewById(R.id.updatedAt);
            holder.autor = (TextView) convertView.findViewById(R.id.autor);


            setViewHolder(convertView, holder, position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            setViewHolder(convertView, holder, position);
        }
        return convertView;
    }

    private void setViewHolder(View convertView, ViewHolder holder, int position) {
        TicketMessage row_pos = rowItems.get(position);

        holder.message.setText(row_pos.getText());
        holder.date.setText(util.getDateString(context, row_pos.getUpdatedAt()));
        holder.autor.setText(row_pos.getUser().getFirstName() + " " + row_pos.getUser().getLastName());
    }
}
