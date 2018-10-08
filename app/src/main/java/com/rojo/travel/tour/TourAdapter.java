package com.rojo.travel.tour;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangyasin on 30/11/2017.
 */

public class TourAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<TourDataSet> list;
    private ArrayList<TourDataSet> arrayList;

    public TourAdapter(Activity activity, LayoutInflater inflater, List<TourDataSet> list, ArrayList<TourDataSet> arrayList) {
        this.activity = activity;
        this.inflater = inflater;
        this.list = list;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
