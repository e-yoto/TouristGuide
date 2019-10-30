package pie.edu.touristguide.View.CurrencyConversion;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pie.edu.touristguide.Controller.CurrencyCalculator;
import pie.edu.touristguide.Model.Currency;
import pie.edu.touristguide.R;

public class CurrencyConversionFragment extends Fragment implements View.OnClickListener{
    View rootView;
    EditText etAmount;
    AutoCompleteTextView actvCurrency;
    Button btnConvert;
    EditText etSearchCurrency;
    CurrencyRvAdapter adapter;
    RecyclerView rvCurrencies;

    String selectedCurrency;
    private static String currencies[];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflates View
        rootView = inflater.inflate(R.layout.currency_conversion_layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currencies = getResources().getStringArray(R.array.currencies);

        //Gets the data entered from the user inputs.
        etAmount = (EditText) rootView.findViewById(R.id.et_amount);
        actvCurrency = (AutoCompleteTextView) rootView.findViewById(R.id.actv_currency);
        btnConvert = (Button) rootView.findViewById(R.id.btn_convert);
        etSearchCurrency = (EditText) rootView.findViewById(R.id.et_search_currency);

        rvCurrencies = rootView.findViewById(R.id.rv_currencies);
        rvCurrencies.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CurrencyRvAdapter(getContext());
        rvCurrencies.setAdapter(adapter);

        btnConvert.setOnClickListener(this);

        //Search bar for currencies
        etSearchCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search(s.toString());
            }
        });
    }

    /**
     * Fills the recyclerview with searched currencies.
     * @param text
     */
    public void search(String text){
        List<Currency> currencies = new ArrayList<>();

        for (Currency currency : CurrencyCalculator.getCurrencies()) {
            if (currency.getName().toLowerCase().contains(text.toLowerCase())) {
                currencies.add(currency);
            }
        }

        adapter.searchCurrencies(currencies);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Autocomplete selector for the current currency
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, currencies);
        actvCurrency.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View v) {
        selectedCurrency = actvCurrency.getText().toString();
        String amountString = etAmount.getText().toString();

        if (selectedCurrency.equalsIgnoreCase("")
                || amountString.equalsIgnoreCase("")){
            Toast.makeText(getContext(), "Please enter fields correctly.", Toast.LENGTH_LONG).show();
        }
        else {
            Double amountInput = Double.parseDouble(amountString);

            //Fills the recyclerview with currencies with newly converted rates and amounts.
            adapter.updateCurrencies(selectedCurrency, amountInput);
        }
    }
}
