package pie.edu.touristguide.Controller.APICall;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import cz.msebera.android.httpclient.HttpConnection;

/**
 * @author BoTao Yu
 *
 * get recommendation tasks from bored api
 */
public class GetRecommendationTask extends AsyncTask<Void, Void, String> {

    private static final String TAG = GetRecommendationTask.class.getSimpleName();
    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL("https://www.boredapi.com/api/activity");
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            String output = "";

            while (scanner.hasNext())
                output += scanner.nextLine();
            Log.d(TAG, output);

            JSONObject rootJSONObj = new JSONObject(output);
            String activity = rootJSONObj.getString("activity");
            return activity;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "Error";
    }
}
