package pie.edu.touristguide.View.Translation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Spinner;

import pie.edu.touristguide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TranslationWebViewFragment extends Fragment {
    View view;
    private ProgressBar progressBar;
    final String url = "https://translate.google.ca/#view=home&op=translate&sl=en&tl=fr";


    public TranslationWebViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.translation_webview_fragment, container, false);

        final WebView webView = (WebView) view.findViewById(R.id.wbvTranslation);

        progressBar = view.findViewById(R.id.pbTranslateWebview);
        progressBar.setVisibility(View.VISIBLE);

        final WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressBar.isShown()){
                    progressBar.setVisibility(View.GONE);
                }
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl(url);
        return view;
    }



}
