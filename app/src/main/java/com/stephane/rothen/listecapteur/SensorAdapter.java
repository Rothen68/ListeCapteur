package com.stephane.rothen.listecapteur;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stéphane on 08/12/2014.
 */
public class SensorAdapter extends BaseAdapter {
    private List<Sensor> lstCapteurs;
    private List<Valeurs> listValeurs;
    protected Context c;
    MainActivity main;
    LayoutInflater inflater;
    View v;
    SensorManager manager;


    public SensorAdapter(List<Sensor> l,Context context, SensorManager manager,MainActivity m, ArrayList<Valeurs> v)
    {
        lstCapteurs=l;
        c=context;
        this.manager=manager;
        listValeurs=v;
        main=m;
    }

    @Override
    public int getCount() {
        return lstCapteurs.size();
    }

    @Override
    public Object getItem(int position) {
        return lstCapteurs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater==null)
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if ( convertView==null)
            v = inflater.inflate(R.layout.item_layout,null);
        else
            v=convertView;

        if ( position <lstCapteurs.size())
            ((TextView)v.findViewById(R.id.textView)).setText(lstCapteurs.get(position).getName());

        if ( position <listValeurs.size()) {
            ((TextView) v.findViewById(R.id.textView2)).setText(String.valueOf(listValeurs.get(position).i));
            ((TextView) v.findViewById(R.id.textView3)).setText(String.valueOf(listValeurs.get(position).j));
            ((TextView) v.findViewById(R.id.textView4)).setText(String.valueOf(listValeurs.get(position).k));
        }



        return v;
    }
    public void setEvents(List<Valeurs> list)
    {
        listValeurs=list;
    }


}
