package pie.edu.touristguide.Model;

import java.util.ArrayList;

import pie.edu.touristguide.R;

public class TranslatedSentence {
    private String original;
    private String translated;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    private int imageId;

    public TranslatedSentence(String original, String translated, int imageId) {
        this.original = original;
        this.translated = translated;
        this.imageId = imageId;
    }

    public static ArrayList<TranslatedSentence> createSentencesList(int numSentence){
        ArrayList<TranslatedSentence> sentences = new ArrayList<>();
        int boy = R.drawable.boy;
        int girl = R.drawable.girl;
        for (int i = 0; i < numSentence; i++){
            if(i%2 == 0)
                sentences.add(new TranslatedSentence("OriginalSentence", "TranslatedSentence", boy));
            else
                sentences.add(new TranslatedSentence("OriginalSentence", "TranslatedSentence", girl));
        }

        return sentences;
    }

    public static ArrayList<TranslatedSentence> createSentencesList(String id){
        ArrayList<TranslatedSentence> sentences = new ArrayList<>();
        int boy = R.drawable.boy;
        int girl = R.drawable.girl;

        if (id.equals("pie.com.touristguide:id/greetingsCard"))
        {
            sentences.add(new TranslatedSentence("Hello", "Bonjour", boy));
            sentences.add(new TranslatedSentence("Goodbye", "Au revoir", girl));
        }

        else if (id.equals("pie.com.touristguide:id/favoritesCard"))
        {
            sentences.add(new TranslatedSentence("Yes", "Oui", boy));
            sentences.add(new TranslatedSentence("Please", "S'il vous plait", girl));
        }

        else if (id.equals("pie.com.touristguide:id/emergencyCard"))
        {
            sentences.add(new TranslatedSentence("Help!", "Au secours!", boy));
            sentences.add(new TranslatedSentence("I need a doctor!", "J'ai besoin d'un docteur!", girl));
        }

        else if (id.equals("pie.com.touristguide:id/questionsCard"))
        {
            sentences.add(new TranslatedSentence("Where is _?", "Où se trouve _?", boy));
            sentences.add(new TranslatedSentence("How much does this cost?", "Combien cela coûte?", girl));
        }

        return sentences;
    }

    @Override
    public String toString() {
        return "TranslatedSentence{" +
                "original='" + original + '\'' +
                ", translated='" + translated + '\'' +
                '}';
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getTranslated() {
        return translated;
    }

    public void setTranslated(String translated) {
        this.translated = translated;
    }
}
