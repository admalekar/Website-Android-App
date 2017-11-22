package at.xtools.pwawrapper;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import at.xtools.pwawrapper.ui.UIManager;
import at.xtools.pwawrapper.webview.WebViewHelper;

public class MainActivity extends AppCompatActivity {
    // Globals
    private UIManager uiManager;
    private WebViewHelper webViewHelper;
    private WebView webView;

  //  Document doc = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setup Theme
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Helpers
        uiManager = new UIManager(this);
        webViewHelper = new WebViewHelper(this, uiManager);

        // Setup App
        webViewHelper.setupWebView();
        uiManager.changeRecentAppsIcon();

        // Load up the Web App
       webViewHelper.loadHome();
      //  new LoadData().execute();


    }

    @Override
    protected void onPause() {
        webViewHelper.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        webViewHelper.onResume();
        // retrieve content from cache primarily if not connected
        webViewHelper.forceCacheIfOffline();
        super.onResume();
    }

    // Handle back-press in browser
    @Override
    public void onBackPressed() {
        if (!webViewHelper.goBack()) {
            super.onBackPressed();
        }
    }
    /*
    class LoadData extends AsyncTask<Void, Void, Document>
    {

        String primeDiv="androidpromoblk clearfix";

        String html=new String();



        @Override
        protected Document doInBackground(Void... voids) {
            Document doc = null;
            try {

                // doc=Jsoup.connect()
                doc = Jsoup.connect(Constants.WEBAPP_URL).timeout(100000).get();
                doc.getElementsByClass("footer").remove();
            } catch (IOException e) {
                e.printStackTrace();
            }
/*

            Elements alldivs=doc.select("div");
            Elements footer=doc.select("footer");
            ArrayList<String> list=new ArrayList<String>();
            ArrayList<String> list2=new ArrayList<String>();

            for(org.jsoup.nodes.Element e: alldivs)
            {
                if(!e.id().equals(""))
                    list.add(e.id());
            }
            for(org.jsoup.nodes.Element e: footer)
            {
                if(!e.id().equals(""))
                    list2.add(e.id());
            }
            for(int i=0;i<list.size();i++)
            {
                if(list.get(i).equals(primeDiv))
                    doc.select("div[id="+list.get(i)+"]").remove();

            }
            for(int i=0;i<list2.size();i++)
            {
                if(list.get(i).equals(footer))
                    doc.select("footer").remove();

            }
            html = alldivs.outerHtml();

            return doc;
        }

        @Override
        protected void onPostExecute(Document result) {

            super.onPostExecute(result);
            webView.loadDataWithBaseURL(Constants.WEBAPP_URL,result.toString(),"text/html","utf-8","");
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(Constants.WEBAPP_URL);
                    return super.shouldOverrideUrlLoading(view, request);
                }
            });

        }
    }
    */

}
