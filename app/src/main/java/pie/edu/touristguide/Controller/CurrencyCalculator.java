package pie.edu.touristguide.Controller;

import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import pie.edu.touristguide.Controller.APICall.GetCurrencyRateTask;
import pie.edu.touristguide.Model.Currency;

public class CurrencyCalculator {
    private final static String TAG = CurrencyCalculator.class.getSimpleName();
    private static double currentRate;
    private static double convertToRate;
    private static double convertedRate;
    private static double convertedAmount;
    private static DecimalFormat format = new DecimalFormat("#.00");


    /**
     * Returns a list of currencies that are provided from the API.
     * @return
     */
    public static List<Currency> getCurrencies(){
        List<Currency> currencies = new ArrayList<>();

        currencies.add(new Currency("CAD", "Canadian Dollar"));
        currencies.add(new Currency("USD", "American Dollar"));
        currencies.add(new Currency("EUR", "European Euro"));
        currencies.add(new Currency("CNY", "Chinese Renminbi"));
        currencies.add(new Currency("INR", "Indian Rupee"));
        currencies.add(new Currency("PHP", "Philippine Peso"));
        currencies.add(new Currency("AUD", "Australian Dollar"));
        currencies.add(new Currency("HKD", "Hong Kong Dollar"));
        currencies.add(new Currency("MXN", "Mexican Peso"));
        currencies.add(new Currency("BGN", "Bulgarian Lev"));
        currencies.add(new Currency("NZD", "New Zealand Dollar"));
        currencies.add(new Currency("ILS", "Israeli New Shekels"));
        currencies.add(new Currency("RUB", "Russian Ruble"));
        currencies.add(new Currency("CHF", "Swiss Franc"));
        currencies.add(new Currency("ZAR", "South African Rand"));
        currencies.add(new Currency("JPY", "Japanese Yen"));
        currencies.add(new Currency("TRY", "Turkish Lira"));
        currencies.add(new Currency("MYR", "Malaysian Ringgit"));
        currencies.add(new Currency("HRK", "Croatian Kuna"));
        currencies.add(new Currency("NOK", "Norwegian Krone"));
        currencies.add(new Currency("IDR", "Indonesian Rupiah"));
        currencies.add(new Currency("DKK", "Danish Krone"));
        currencies.add(new Currency("CZK", "Czech Koruna"));
        currencies.add(new Currency("HUF", "Hungarian Forint"));
        currencies.add(new Currency("GBP", "Pound Sterling"));
        currencies.add(new Currency("THB", "Thai Baht"));
        currencies.add(new Currency("KRW", "South Korean Won"));
        currencies.add(new Currency("ISK", "Icelandic Krona"));
        currencies.add(new Currency("SGD", "Singapore Dollar"));
        currencies.add(new Currency("BRL", "Brazilian Real"));
        currencies.add(new Currency("PLN", "Polish Zloty"));
        currencies.add(new Currency("RON", "Romanian Leu"));

        return currencies;
    }

    /**
     * Takes the current currency, currency to convert to, and the amount to be converted
     * and calculates for the currency conversion.
     * @param currencyName
     * @param convertToCurrencyName
     * @param amount
     * @return
     */
    public static String getConvertedAmount(String currencyName, String convertToCurrencyName, double amount){
        currentRate = getRateWith(currencyName);
        convertToRate = getRateWith(convertToCurrencyName);

        convertedAmount = (amount * convertToRate) / currentRate;
        Log.d(TAG, "Converted Amount: "  + convertedAmount);

        return format.format(convertedAmount);
    }

    /**
     * Calculates the "convert to currency"'s rate based on the selected current currency.
     * @param currencyName
     * @param convertToCurrencyName
     * @return
     */
    public static String getRateComparison(String currencyName, String convertToCurrencyName){
        currentRate = getRateWith(currencyName);
        convertToRate = getRateWith(convertToCurrencyName);

        convertedRate = convertToRate/currentRate;
        Log.d(TAG, "Converted Rate: " + convertedRate);

        return format.format(convertedRate);
    }

    /**
     * Retrieves currency rate based on the currency name.
     * @param currencyName
     * @return
     */
    private static double getRateWith(String currencyName){
        double rate = 1;
        try {
            rate = new GetCurrencyRateTask().execute(currencyName).get();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        Log.d(TAG, currencyName + " rate: " + rate);

        return rate;
    }
}
