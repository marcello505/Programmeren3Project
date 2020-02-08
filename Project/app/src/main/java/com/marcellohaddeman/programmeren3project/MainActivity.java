package com.marcellohaddeman.programmeren3project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FetchElements fetchElements = new FetchElements();
        fetchElements.execute(NetworkUtils.buildURL());

    }

    protected void elementsReceived(List<Element> elements){
        //TODO Deze elements doorsturen naar de RecycleViewAdapter zodat deze worden neergezet in de bijbehorende items.
        for(Element item : elements){
            System.out.println(item.getTitel());
        }

    }

    protected class FetchElements extends AsyncTask<URL, Void, List<Element>>{

        @Override
        protected List<Element> doInBackground(URL... urls) {
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
        protected void onPostExecute(List<Element> elements) {
            elementsReceived(elements);
        }
    }
}
