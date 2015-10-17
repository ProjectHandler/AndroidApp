package com.eip.projecthandler.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.blunderer.materialdesignlibrary.activities.Activity;
import com.eip.projecthandler.R;
import com.eip.projecthandler.helpers.util;
import com.eip.projecthandler.models.Ticket;

import java.util.List;

public class CustomAdapterTicket extends BaseAdapter {

    Context context;
    List<Ticket> rowItems;

    /* private view holder class */
    private class ViewHolder {
        TextView title;
        TextView status;
        TextView priority;
        TextView updatedAt;
        ImageButton send;
    }

    public CustomAdapterTicket(Context context, List<Ticket> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    public void setRowItems(List<Ticket> rowItems) {
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

            convertView = mInflater.inflate(R.layout.ticket_item, null);
            holder = new ViewHolder();

            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.status = (TextView) convertView.findViewById(R.id.updatedAt);
            holder.priority = (TextView) convertView.findViewById(R.id.priority);
            holder.updatedAt = (TextView) convertView.findViewById(R.id.updatedAt);

            holder.send = (ImageButton) convertView.findViewById(R.id.buttonSend);


            setViewHolder(convertView, holder, position);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            setViewHolder(convertView, holder, position);
        }

        return convertView;
    }

    private void setViewHolder(View convertView, ViewHolder holder, int position) {
        Ticket row_pos = rowItems.get(position);
        holder.title.setText(row_pos.getTitle());

        if (row_pos.getTicketStatus() == null)
            holder.status.setText("");
        else
            holder.status.setText(row_pos.getTicketStatus().getValue());

        Log.d("CustomAdapterTicket", "row_pos.getTicketPriority(): " + row_pos.getTicketPriority());
        //if (row_pos.getTicketPriority() == null)
            holder.priority.setText("Medium");
        //else
        //    holder.priority.setText(row_pos.getTicketPriority().getValue());

        holder.updatedAt.setText(util.getDateString(context, row_pos.getUpdatedAt()));

       // holder.send.setImageResource(R);
    }
}
