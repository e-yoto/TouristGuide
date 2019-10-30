package pie.edu.touristguide.Controller.APICall;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class GetCurrencyRateTask extends AsyncTask<String, Integer, Double> {
    private static final String TAG = GetCurrencyRateTask.class.getSimpleName();
    private static double currencyRate;

    /**
     * Returns the rate (based on euros) of a specific currency name.
     * @param currencyName
     * @return
     */
    private static double getRate(String currencyName){
        try {
            Uri.Builder currencyUriBuilder = new Uri.Builder();
            currencyUriBuilder.scheme("https")
                    .authority("api.exchangeratesapi.io")
                    .appendPath("latest");

            String rateURL = currencyUriBuilder.build().toString();
            Log.d(TAG, "Rate URL: " + rateURL);

            JSONObject json = JSONUtil.getJSONObjectFrom(rateURL);
            JSONObject allJSONRates = json.getJSONObject("rates");

            String strRate = "";

            if(getCurrencyAbbr(currencyName).equals("EUR"))
                currencyRate = 1;
            else {
                strRate = allJSONRates.getString(getCurrencyAbbr(currencyName));
                currencyRate = Double.parseDouble(strRate);
            }

            Log.d(TAG, currencyName + " rate: " + strRate);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return currencyRate;
    }

    /**
     * Returns the currency's abbreviations.
     * @param currencyName
     * @return
     */
    public static String getCurrencyAbbr(String currencyName){
        String currencyAbbr = null;

        switch (currencyName){
            case "Canadian Dollar":
                currencyAbbr = "CAD";
                break;
            case "American Dollar":
                currencyAbbr = "USD";
                break;
            case "European Euro":
                currencyAbbr = "EUR";
                break;
            case "Chinese Renminbi":
                currencyAbbr = "CNY";
                break;
            case "Indian Rupee":
                currencyAbbr = "INR";
                break;
            case "Philippine Peso":
                currencyAbbr = "PHP";
                break;
            case "Australian Dollar":
                currencyAbbr = "AUD";
                break;
            case "Hong Kong Dollar":
                currencyAbbr = "HKD";
                break;
            case "Mexican Peso":
                currencyAbbr = "MXN";
                break;
            case "Bulgarian Lev":
                currencyAbbr = "BGN";
                break;
            case "New Zealand Dollar":
                currencyAbbr = "NZD";
                break;
            case "Israeli New Shekels":
                currencyAbbr = "ILS";
                break;
            case "Russian Ruble":
                currencyAbbr = "RUB";
                break;
            case "Swiss Franc":
                currencyAbbr = "CHF";
                break;
            case "South African Rand":
                currencyAbbr = "ZAR";
                break;
            case "Japanese Yen":
                currencyAbbr = "JPY";
                break;
            case "Turkish Lira":
                currencyAbbr = "TRY";
                break;
            case "Malaysian Ringgit":
                currencyAbbr = "MYR";
                break;
            case "Croatian Kuna":
                currencyAbbr = "HRK";
                break;
            case "Norwegian Krone":
                currencyAbbr = "NOK";
                break;
            case "Indonesian Rupiah":
                currencyAbbr = "IDR";
                break;
            case "Danish Krone":
                currencyAbbr = "DKK";
                break;
            case "Czech Koruna":
                currencyAbbr = "CZK";
                break;
            case "Hungarian Forint":
                currencyAbbr = "HUF";
                break;
            case "Pound Sterling":
                currencyAbbr = "GBP";
                break;
            case "Thai Baht":
                currencyAbbr = "THB";
                break;
            case "South Korean Won":
                currencyAbbr = "KRW";
                break;
            case "Icelandic Krona":
                currencyAbbr = "ISK";
                break;
            case "Singapore Dollar":
                currencyAbbr = "SGD";
                break;
            case "Brazilian Real":
                currencyAbbr = "BRL";
                break;
            case "Polish Zloty":
                currencyAbbr = "PLN";
                break;
            case "Romanian Leu":
                currencyAbbr = "RON";
                break;
        }

        return currencyAbbr;
    }

    @Override
    protected Double doInBackground(String... strings) {
        return getRate(strings[0]);
    }
}
