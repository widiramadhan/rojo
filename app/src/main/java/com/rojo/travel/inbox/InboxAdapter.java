package com.rojo.travel.inbox;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rojo.travel.R;
import com.rojo.travel.mobil.MobilDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limadawai on 19/04/18.
 */

public class InboxAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<InboxDataSet> list;
    private ArrayList<InboxDataSet> arrayList;

    public InboxAdapter(Activity activity, List<InboxDataSet> list) {
        this.activity = activity;
        this.list = list;
        this.arrayList = new ArrayList<InboxDataSet>();
        this.arrayList.addAll(list);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_inbox, parent, false);
        TextView subject = (TextView) convertView.findViewById(R.id.subject);
        TextView message = (TextView) convertView.findViewById(R.id.message);
        final InboxDataSet ids = list.get(position);
        subject.setText(ids.getSubject());
        message.setText(ids.getMessage());
        return convertView;
    }
}
