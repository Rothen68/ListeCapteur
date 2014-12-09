package com.stephane.rothen.listecapteur;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by stéphane on 09/12/2014.
 */
public class GraphActivity extends ActionBarActivity {

    SensorManager manager;
    SensorAdapter adapter;
    Sensor capteur;
    public Graphique graph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);
        Intent i = getIntent();
        int type = i.getIntExtra("type",0);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        capteur =  manager.getDefaultSensor(type);
        graph=(Graphique) findViewById(R.id.graphique);
        ((TextView) findViewById(R.id.txtNomCapteur)).setText(capteur.getName());






    }


    final SensorEventListener mSensorEventListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Que faire en cas de changement de précision ?



        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            // Que faire en cas d'évènements sur le capteur ?
            Valeurs v = new Valeurs();
            v.i=sensorEvent.values[0];
            v.j=sensorEvent.values[1];
            v.k=sensorEvent.values[2];

            if(graph!=null) {
                graph.ajouterValeurs(v);
                graph.invalidate();
            }




        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(mSensorEventListener,capteur);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        manager.registerListener(mSensorEventListener,capteur, SensorManager.SENSOR_DELAY_NORMAL);

    }
}
