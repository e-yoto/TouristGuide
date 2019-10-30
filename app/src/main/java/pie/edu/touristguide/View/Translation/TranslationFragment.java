package pie.edu.touristguide.View.Translation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pie.edu.touristguide.Model.TranslatedSentence;
import pie.edu.touristguide.R;

/**
 * Creates the Translation Fragment
 * @author Emmanuel
 */
public class TranslationFragment extends Fragment {

    ArrayList<TranslatedSentence> sentences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.translation_layout, container, false);
        //initialize sentences
        Bundle bundle = this.getArguments();
        String value = bundle.getString("TRANS_VIEW_CLICKED");
        Log.v("receivedKey", value);
        sentences = TranslatedSentence.createSentencesList(value);

        //get recyclerview
        RecyclerView rvSentences = (RecyclerView) rootView.findViewById(R.id.rvTranslations);

        //set layout manager to position items
        rvSentences.setLayoutManager(new LinearLayoutManager(getActivity()));

        //create adapter
        TranslationRVAdapter adapter = new TranslationRVAdapter(getActivity(), sentences);

        //attach adapter
        rvSentences.setAdapter(adapter);

        return rootView;
    }


}
