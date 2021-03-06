package com.marcellohaddeman.programmeren3project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    private double mLatitude;
    private double mLongitude;

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


        //Maak de logica van de knop klaar.
        this.mToonOpKaart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "geo:0,0?q=%f,%f", mLatitude, mLongitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                DetailActivity.this.startActivity(intent);
            }
        });

        //Haal de text op en zet ze in de bijbehorende TextViews.
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

        if(intent.getLongExtra("plaatsingsdatum", 0) == 0){
            this.mPlaatsingsdatumInvoer.setText(R.string.onbekend);
        }else{
            SimpleDateFormat plaatsingsDatum = new SimpleDateFormat("dd/MM/yyyy");
            this.mPlaatsingsdatumInvoer.setText(plaatsingsDatum.format(new Date(intent.getLongExtra("plaatsingsdatum", 0) * 1000)));
        }

        this.mLatitude = intent.getDoubleExtra("geoY", 0);
        this.mLongitude = intent.getDoubleExtra("geoX", 0);
        if(this.mLatitude == 0 && this.mLongitude == 0){
            this.mToonOpKaart.setVisibility(View.GONE);
        }

        //Voeg een ActionBar toe met een back knop.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Log.v(TAG, "onCreate: Finished method.");
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }
}
