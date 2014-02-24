package com.android.crazywheel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ElementsArrayAdapter extends ArrayAdapter<Element> {

    private final Context context;
    private ArrayList<Element> items;

    static class ViewHolder {
        public TextView title;
        public TextView text;
    }

    public ElementsArrayAdapter(Context context, int id, ArrayList<Element> items) {
        super(context, id, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Element getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.title = (TextView) rowView.findViewById(R.id.title);
            viewHolder.text = (TextView) rowView.findViewById(R.id.text);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.title.setText(items.get(position).getTitle());
        holder.text.setText(items.get(position).getText());

        return rowView;
    }

}
