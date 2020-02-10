package com.marcellohaddeman.programmeren3project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";

    private TextView mTitel;
    private TextView mGeografischeLigging;
    private ImageView mImage;
    private TextView mKunstenaar;
    private TextView mKunstenaarInvoer;
    private TextView mBeschrijving;
    private TextView mBeschrijvingInvoer;
    private TextView mMateriaal;
    private TextView mMateriaalInvoer;
    private TextView mOndergrond;
    private TextView mOndergrondInvoer;
    private TextView mPlaatsingsdatum;
    private TextView mPlaatsingsdatumInvoer;
    private Button mToonOpKaart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //De m waarden binden met de view.
        this.mTitel = findViewById(R.id.tv_activity_detail_titel);
        this.mGeografischeLigging = findViewById(R.id.tv_activity_detail_geografische_ligging);
        this.mImage = findViewById(R.id.iv_activity_detail_image);
        this.mKunstenaar = findViewById(R.id.tv_activity_detail_kunstenaar);
        this.mKunstenaarInvoer = findViewById(R.id.tv_activity_detail_kunstenaar_invoer);
        this.mBeschrijving = findViewById(R.id.tv_activity_detail_beschrijving);
        this.mBeschrijvingInvoer = findViewById(R.id.tv_activity_detail_beschrijving_invoer);
        this.mMateriaal = findViewById(R.id.tv_activity_detail_materiaal);
        this.mMateriaalInvoer = findViewById(R.id.tv_activity_detail_materiaal_invoer);
        this.mOndergrond = findViewById(R.id.tv_activity_detail_ondergrond);
        this.mOndergrondInvoer = findViewById(R.id.tv_activity_detail_ondergrond_invoer);
        this.mPlaatsingsdatum = findViewById(R.id.tv_activity_detail_plaatsingsdatum);
        this.mPlaatsingsdatumInvoer = findViewById(R.id.tv_activity_detail_plaatsingsdatum_invoer);
        this.mToonOpKaart = findViewById(R.id.btn_activity_detail_kaart);
        //TODO Maak de ToonOpKaart knop af. Moet nog de GEOX en GEOY doorgeven via de ViewHolder

        Intent intent = getIntent();
        this.mTitel.setText(intent.getStringExtra("titel"));
        this.mGeografischeLigging.setText(intent.getStringExtra("geografischeLigging"));
        Glide.with(this)
                .asBitmap()
                .load(intent.getStringExtra("imageURL"))
                .into(this.mImage);
        this.mKunstenaarInvoer.setText(intent.getStringExtra("kunstenaar"));
        this.mBeschrijvingInvoer.setText(intent.getStringExtra("beschrijving"));
        this.mMateriaalInvoer.setText(intent.getStringExtra("materiaal"));
        this.mOndergrondInvoer.setText(intent.getStringExtra("ondergrond"));
        SimpleDateFormat plaatsingsDatum = new SimpleDateFormat("dd/MM/yyyy");
        this.mPlaatsingsdatumInvoer.setText(plaatsingsDatum.format(new Date(intent.getLongExtra("plaatsingsdatum", 0) * 1000)));
        Log.v(TAG, "onCreate: Finished method.");

    }
}
