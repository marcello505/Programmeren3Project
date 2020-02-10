package com.marcellohaddeman.programmeren3project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    RecyclerView mRecyclerView;
    ArrayList<Element> mElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mRecyclerView = findViewById(R.id.rc_activity_main);
        if(savedInstanceState != null){
            Log.v(TAG, "onCreate: Succesvol een savedInstanceState geladen.");
            this.mElements = savedInstanceState.getParcelableArrayList("elements");
            elementsReceived(this.mElements);
        }else{
            FetchElements fetchElements = new FetchElements();
            fetchElements.execute(NetworkUtils.buildURL());
        }

    }

    protected void elementsReceived(ArrayList<Element> elements){
        if(this.mElements == null){
            this.mElements = elements;
            Toast toast = Toast.makeText(getApplicationContext(), "Aantal resultaten: " + elements.size(), Toast.LENGTH_LONG);
            toast.show();
        }
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(this, this.mElements);
        this.mRecyclerView.setAdapter(recycleViewAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //TODO Als het scherm veranderd van orientatie dan moet hij van layout veranderen.

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("elements", this.mElements);
        super.onSaveInstanceState(outState);
    }

    protected class FetchElements extends AsyncTask<URL, Void, ArrayList<Element>>{

        @Override
        protected ArrayList<Element> doInBackground(URL... urls) {
            String jsonElementResponse = null;
            ArrayList<Element> elementArrayList = new ArrayList<>();

            try{
                jsonElementResponse = NetworkUtils.getResponseFromHttpUrl(urls[0]);

                //JSON Parsing
                JSONObject jsonRootObject = new JSONObject(jsonElementResponse);
                JSONArray jsonFeatureArray = jsonRootObject.getJSONArray("features");
                JSONObject jsonFeatureRoot;
                JSONObject jsonFeatureAttributes;
                JSONObject jsonFeatureGeometry;
                Element element;

                for(int x = 0; x < jsonFeatureArray.length(); x++){
                    jsonFeatureRoot = jsonFeatureArray.getJSONObject(x);
                    jsonFeatureAttributes = jsonFeatureRoot.getJSONObject("attributes");
                    jsonFeatureGeometry = jsonFeatureRoot.getJSONObject("geometry");
                    element = new Element();

                    //Fill element attributes
                    element.setObjectId(jsonFeatureAttributes.getInt("OBJECTID"));
                    element.setIdentificatie(jsonFeatureAttributes.getString("IDENTIFICATIE"));
                    element.setTitel(jsonFeatureAttributes.getString("AANDUIDINGOBJECT"));
                    element.setGeografischeLigging(jsonFeatureAttributes.getString("GEOGRAFISCHELIGGING"));
                    element.setKunstenaar(jsonFeatureAttributes.getString("KUNSTENAAR"));
                    element.setBeschrijving(jsonFeatureAttributes.getString("OMSCHRIJVING"));
                    element.setMateriaal(jsonFeatureAttributes.getString("MATERIAAL"));
                    element.setOndergrond(jsonFeatureAttributes.getString("ONDERGROND"));
                    if(!jsonFeatureAttributes.isNull("PLAATSINGSDATUM")){
                        element.setPlaatsingDatum(jsonFeatureAttributes.getInt("PLAATSINGSDATUM"));
                    }
                    element.setImageUrl(jsonFeatureAttributes.getString("URL"));
                    element.setGeoX(jsonFeatureGeometry.getDouble("x"));
                    element.setGeoY(jsonFeatureGeometry.getDouble("y"));

                    elementArrayList.add(element);
                }
            }catch (Exception e){
                Log.e(TAG, "doInBackground: " + e.getMessage());
            }
            return elementArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Element> elements) {
            elementsReceived(elements);
        }
    }
}
