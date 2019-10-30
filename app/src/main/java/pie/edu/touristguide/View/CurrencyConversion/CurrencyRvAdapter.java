package pie.edu.touristguide.View.CurrencyConversion;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pie.edu.touristguide.Controller.CurrencyCalculator;
import pie.edu.touristguide.Model.Currency;
import pie.edu.touristguide.R;

public class CurrencyRvAdapter extends RecyclerView.Adapter<CurrencyRvAdapter.ViewHolder>{
    //Sets the recyclerview results based on Canadian Dollar
    //and based on 0 dollars by default.
    String selectedCurrency = "Canadian Dollar";
    double amountToConvert = 0;

    List<Currency> currencies;
    Context context;

    public CurrencyRvAdapter(Context context){
        this.currencies = CurrencyCalculator.getCurrencies();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.currency_row_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Currency currency = currencies.get(i);
        String initials = currency.getInitials();
        String name = currency.getName();
        String rate = CurrencyCalculator.getRateComparison(selectedCurrency, name);
        String amount = CurrencyCalculator.getConvertedAmount(selectedCurrency, name, amountToConvert);

        viewHolder.tvInitials.setText(initials);
        viewHolder.tvName.setText(name);
        viewHolder.tvRate.setText(rate);
        viewHolder.tvAmount.setText(amount);
    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvInitials;
        TextView tvName;
        TextView tvRate;
        TextView tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInitials = itemView.findViewById(R.id.tv_currency_init);
            tvName = itemView.findViewById(R.id.tv_currency_name);
            tvRate = itemView.findViewById(R.id.tv_currency_rate);
            tvAmount = itemView.findViewById(R.id.tv_currency_amount);
        }
    }

    /**
     * Allows to change the adapter's attributes to update the recyclerview.
     * Specifically to update the rates and amounts.
     * @param selectedCurrency
     * @param amountToConvert
     */
    public void updateCurrencies(String selectedCurrency, double amountToConvert){
        this.selectedCurrency = selectedCurrency;
        this.amountToConvert = amountToConvert;
        notifyDataSetChanged();
    }

    /**
     * Allows to change the adapter's attributes to update the recyclerview.
     * Specifically to filter the currencies in the recyclerview.
     * @param currencies
     */
    public void searchCurrencies(List<Currency> currencies){
        this.currencies = currencies;
        notifyDataSetChanged();
    }
}
