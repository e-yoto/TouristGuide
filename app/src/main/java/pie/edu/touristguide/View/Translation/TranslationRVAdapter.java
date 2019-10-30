package pie.edu.touristguide.View.Translation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pie.edu.touristguide.Model.TranslatedSentence;
import pie.edu.touristguide.R;

/**
 * @author Emmanuel
 * Adapter for the RecyclerView that will display the translations.
 * @link https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
 */
public class TranslationRVAdapter extends RecyclerView.Adapter<TranslationRVAdapter.ViewHolder> {
    private List<TranslatedSentence> mData;
    private LayoutInflater mInflater;

    /**
     * Pass data into constructor
     */
    public TranslationRVAdapter(Context context, List<TranslatedSentence> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    /**
     * Inflate the layout from the row layout xml and returns holder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


        //inflate layout
        View translationsView = mInflater.inflate(R.layout.translation_row_layout, parent, false);
        //return instance of a new holder
        ViewHolder viewHolder = new ViewHolder(translationsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        //get data based on position i
        TranslatedSentence ts = mData.get(i);

        //set views based on data received
        viewHolder.originalTV.setText(ts.getOriginal());
        viewHolder.imageView.setImageResource(ts.getImageId());
        viewHolder.translatedTV.setText(ts.getTranslated());
    }


    /**
     * Returns total amount of items in the list
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }


    /**
     * Holds view as they scroll out of screen
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView originalTV;
        public TextView translatedTV;
        public ImageView imageView;

            ViewHolder(View sentencesView){
                super(sentencesView);
                imageView = sentencesView.findViewById(R.id.imageView2);
                originalTV = (TextView)sentencesView.findViewById(R.id.originalText);
                translatedTV = (TextView)sentencesView.findViewById(R.id.translatedText);
        }
    }
}
