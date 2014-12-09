package com.stephane.rothen.listecapteur;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by stéphane on 09/12/2014.
 */
public class Graphique extends View {

    private ArrayList<Valeurs> val;
    private String nomCapteur;
    private Paint p;
    private int maxValeurs;

    public String getNomCapteur() {
        return nomCapteur;
    }

    public void setNomCapteur(String nomCapteur) {
        this.nomCapteur = nomCapteur;

    }

    public Graphique(Context context) {
        super(context);
        init(context);
    }

    public Graphique(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Graphique(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float mY = canvas.getHeight()/2;
        float mX = canvas.getWidth();
        float stepX=mX/val.size();
        float maxValY=0;
        float minValY=0;
        maxValeurs = (int) canvas.getWidth()/3;

        p.setColor(Color.BLACK);
        canvas.drawLine(0,0,0,canvas.getHeight(),p);
        canvas.translate(0,mY);

        canvas.drawLine(0,0,mX,0,p);

        for (int i = 0 ; i < val.size() ; i++)
        {
            if ( val.get(i).i>maxValY)
                maxValY=val.get(i).i;
            if ( val.get(i).j>maxValY )
                maxValY=val.get(i).j;
            if ( val.get(i).k>maxValY)
                maxValY=val.get(i).k;
            if ( val.get(i).i<minValY)
                minValY=val.get(i).i;
            if ( val.get(i).j<minValY )
                minValY=val.get(i).j;
            if ( val.get(i).k<minValY)
                minValY=val.get(i).k;

        }

        float stepY = ((canvas.getHeight()-20)/2)/( Math.abs(maxValY)+Math.abs(minValY));
        // si plus de valeurs que de pixel sur l'ecran, on prend les dernieres valeurs de la lsite
        if(stepX<3 && val!=null)
        {
            stepX=3;


            for (int i = val.size()-1 ; i >0 ; i++)
            {
                if (i==0) {
                    p.setColor(Color.RED);
                    canvas.drawPoint(0,-val.get(i).i*stepY,p);
                    p.setColor(Color.GREEN);
                    canvas.drawPoint(0,-val.get(i).j*stepY,p);
                    p.setColor(Color.BLUE);
                    canvas.drawPoint(0,-val.get(i).k*stepY,p);
                }
                else
                {
                    p.setColor(Color.RED);
                    canvas.drawLine((i-1)*(stepX),-val.get(i-1).i*stepY,i*stepX,-val.get(i).i*stepY,p);
                    p.setColor(Color.GREEN);
                    canvas.drawLine((i-1)*(stepX),-val.get(i-1).j*stepY,i*stepX,-val.get(i).j*stepY,p);
                    p.setColor(Color.BLUE);
                    canvas.drawLine((i-1)*(stepX),-val.get(i-1).k*stepY,i*stepX,-val.get(i).k*stepY,p);
                }

            }

        }
        else
        {
            for (int i = 0 ; i < val.size() ; i++)
            {
                if (i==0) {
                    p.setColor(Color.RED);
                    canvas.drawPoint(0,val.get(i).i*stepY,p);
                    p.setColor(Color.GREEN);
                    canvas.drawPoint(0,val.get(i).j*stepY,p);
                    p.setColor(Color.BLUE);
                    canvas.drawPoint(0,val.get(i).k*stepY,p);
                }
                else
                {
                    p.setColor(Color.RED);
                    canvas.drawLine((i-1)*(stepX),-val.get(i-1).i*stepY,i*stepX,-val.get(i).i*stepY,p);
                    p.setColor(Color.GREEN);
                    canvas.drawLine((i-1)*(stepX),-val.get(i-1).j*stepY,i*stepX,-val.get(i).j*stepY,p);
                    p.setColor(Color.BLUE);
                    canvas.drawLine((i-1)*(stepX),-val.get(i-1).k*stepY,i*stepX,-val.get(i).k*stepY,p);
                }

            }
        }





    }

    private void init(Context c)
    {
        val = new ArrayList<Valeurs>();
        p= new Paint();
        p.setAntiAlias(true);
        maxValeurs=0;

    }

    public void ajouterValeurs(Valeurs v)
    {
        if (val.size()<maxValeurs)
            val.add(v);
        else if ( val.size()>0)
        {
            val.remove(0);
            val.add(v);
        }
    }


}
