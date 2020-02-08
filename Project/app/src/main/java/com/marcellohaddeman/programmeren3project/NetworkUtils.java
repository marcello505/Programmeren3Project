package com.marcellohaddeman.programmeren3project;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {
    private static final String TAG = "NetworkUtils";

    private static final String DATA_BREDA_BASE_URL = "https://services7.arcgis.com/21GdwfcLrnTpiju8/arcgis/rest/services/Sierende_elementen/FeatureServer/0/query";
    private static final String where = "1=1";
    private static final String format = "json";
    private static final String outFields = "*"; //De velden die hij moet teruggeven.
    private static final String outSR = "4326"; //Geen idee wtf dit is.

    final static String WHERE_PARAM = "where";
    final static String FORMAT_PARAM = "f";
    final static String OUT_FIELDS_PARAM = "outfields";
    final static String OUT_SPATIAL_REFERENCE_PARAM = "outSR";

    public static URL buildURL(){
        Uri builtUri = Uri.parse(DATA_BREDA_BASE_URL).buildUpon()
                .appendQueryParameter(WHERE_PARAM, where)
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(OUT_FIELDS_PARAM, outFields)
                .appendQueryParameter(OUT_SPATIAL_REFERENCE_PARAM, outSR)
                .build();

        URL url = null;
        try{
            url = new URL(builtUri.toString());

        }catch (MalformedURLException e){
            Log.e(TAG, "buildURL: " + e.getMessage());
        }
        Log.v(TAG, "Built URL: " + url);

        return url;
    }

    public static String getResponseFromHttpUrl (URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext()){
                Log.v(TAG, "getResponseFromHttpUrl: Connection was succesful");
                return scanner.next();
            }else{
                return null;
            }
        }finally {
            urlConnection.disconnect();
        }
    }
}
