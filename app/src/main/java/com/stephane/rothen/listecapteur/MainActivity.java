package com.stephane.rothen.listecapteur;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    ListView lv;
    List<Sensor> capteurs;
    ArrayList<Valeurs> valeurs;
    SensorManager manager;
    SensorAdapter adapter;


    final SensorEventListener mSensorEventListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Que faire en cas de changement de précision ?

        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            // Que faire en cas d'évènements sur le capteur ?
            int pos = capteurs.indexOf(sensorEvent.sensor);
            valeurs.get(pos).i = sensorEvent.values[0];
            valeurs.get(pos).j = sensorEvent.values[1];
            valeurs.get(pos).k = sensorEvent.values[2];
            lv.invalidateViews();



        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        valeurs = new ArrayList<Valeurs>() ;
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        capteurs = manager.getSensorList(Sensor.TYPE_ALL);

        for (int i = 0 ; i < capteurs.size(); i++)
        {
            valeurs.add(new Valeurs());
        }


        lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(this);

        adapter = new SensorAdapter(capteurs,getApplicationContext(),manager,this,valeurs);



        lv.setAdapter(adapter);

    }




    @Override
    protected void onPause() {
        super.onPause();
        for (Sensor s : capteurs)
        {
            manager.unregisterListener(mSensorEventListener,s);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        for (Sensor s : capteurs)
        {
            manager.registerListener(mSensorEventListener,s,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int type =capteurs.get(position).getType();
        for (Sensor s : capteurs)
        {
            manager.unregisterListener(mSensorEventListener,s);
        }
        Intent i = new Intent(this,GraphActivity.class);
        i.putExtra("type",type);
        startActivity(i);

    }
}
