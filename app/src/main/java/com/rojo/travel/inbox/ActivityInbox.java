package com.rojo.travel.inbox;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rojo.travel.MainActivity;
import com.rojo.travel.R;
import com.rojo.travel.functions.Fungsi;
import com.rojo.travel.functions.VolleyObjectResult;
import com.rojo.travel.functions.VolleyObjectService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityInbox extends Fragment {

    VolleyObjectResult vor;
    VolleyObjectService vos;

    InboxAdapter inboxAdapter;
    View view;

    private List<InboxDataSet>  listInbox = new ArrayList<InboxDataSet>();

    ListView list;
    ProgressBar progressBar;
    TextView message;

    Fungsi fungsi = new Fungsi();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static ActivityInbox newInstance() {
        ActivityInbox fragment = new ActivityInbox();
        return fragment;
    }

    public ActivityInbox(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_inbox, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.proBar);
        list = (ListView) getActivity().findViewById(R.id.list);
        message = (TextView) getActivity().findViewById(R.id.noMessage);
        String email = fungsi.getEmail(getActivity());
        String uid = fungsi.getDeviceUniqueID(getActivity());
        String url = "http://m.rojo.id/Apimember/getInbox?email="+email+"&uid="+uid;
        listInbox = new ArrayList<InboxDataSet>();
        vor = new VolleyObjectResult() {
            @Override
            public void resSuccess(String requestType, JSONObject response) {
                //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    //Toast.makeText(getActivity(), jsonArray.toString(), Toast.LENGTH_LONG).show();
                    if (jsonArray.toString().equals("[]")) {
                        Toast.makeText(getActivity(), "No message yet!", Toast.LENGTH_SHORT).show();
                    } else {
                        message.setVisibility(View.GONE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            InboxDataSet ids = new InboxDataSet();
                            ids.setSubject(jsonObject.getString("subject"));
                            ids.setMessage(jsonObject.getString("content"));
                            listInbox.add(ids);
                        }
                        inboxAdapter = new InboxAdapter(getActivity(), listInbox);
                        inboxAdapter.notifyDataSetChanged();
                        list.setAdapter(inboxAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void resError(String requestType, VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        };
        vos = new VolleyObjectService(vor, getActivity());
        vos.getJsonObject("GETCALL", url);
        progressBar.setVisibility(View.VISIBLE);
        return view;
    }
}
