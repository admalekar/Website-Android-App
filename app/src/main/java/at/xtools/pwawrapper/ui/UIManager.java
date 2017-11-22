package at.xtools.pwawrapper.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import at.xtools.pwawrapper.Constants;
import at.xtools.pwawrapper.R;

public class UIManager {
    // Instance variables
    private Activity activity;
    private WebView webView;
    private ProgressBar progressSpinner;
    private ProgressBar progressBar;
    private LinearLayout offlineContainer;
    private boolean pageLoaded = false;



    public UIManager(Activity activity) {
        this.activity = activity;
        this.progressBar = (ProgressBar) activity.findViewById(R.id.progressBarBottom);
        this.progressSpinner = (ProgressBar) activity.findViewById(R.id.progressSpinner);
        this.offlineContainer = (LinearLayout) activity.findViewById(R.id.offlineContainer);
        this.webView = (WebView) activity.findViewById(R.id.webView);
      //  webView.getSettings().setJavaScriptEnabled(false);
     //  new LoadData().execute();

        // set click listener for offline-screen
        offlineContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
                setOffline(false);
            }
        });
    }

    // Set Loading Progress for ProgressBar
    public void setLoadingProgress(int progress) {
        // set progress in UI
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(progress, true);
        } else {
            progressBar.setProgress(progress);
        }

        // hide ProgressBar if not applicable
        if (progress >= 0 && progress < 100) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }

        // get app screen back if loading is almost complete
        if (progress >= Constants.PROGRESS_THRESHOLD && !pageLoaded) {
            setLoading(false);
        }
    }

    // Show loading animation screen while app is loading/caching the first time
    public void setLoading(boolean isLoading) {
        if (isLoading) {
            progressSpinner.setVisibility(View.VISIBLE);
            webView.animate().translationX(Constants.SLIDE_EFFECT).alpha(0.5F).setInterpolator(new AccelerateInterpolator()).start();
        } else {
            webView.setTranslationX(Constants.SLIDE_EFFECT * -1);
            webView.animate().translationX(0).alpha(1F).setInterpolator(new DecelerateInterpolator()).start();
            progressSpinner.setVisibility(View.INVISIBLE);
        }
        pageLoaded = !isLoading;
    }

    // handle visibility of offline screen
    public void setOffline(boolean offline) {
        if (offline) {
            setLoadingProgress(100);
            webView.setVisibility(View.INVISIBLE);
            offlineContainer.setVisibility(View.VISIBLE);
        } else {
            webView.setVisibility(View.VISIBLE);
            offlineContainer.setVisibility(View.INVISIBLE);
        }
    }

    // set icon in recent activity view to a white one to be visible in the app bar
    public void changeRecentAppsIcon() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bitmap iconWhite = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_appbar);

            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = activity.getTheme();
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
            int color = typedValue.data;

            ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(
                    activity.getResources().getString(R.string.app_name),
                    iconWhite,
                    color
            );
            activity.setTaskDescription(description);
            iconWhite.recycle();
        }
    }


/*

    class LoadData extends AsyncTask<Void,Void,Void>
    {

        String primeDiv="androidpromoblk clearfix";

        String html=new String();
        Document doc = null;


        @Override
        protected Void doInBackground(Void... voids) {
            try {

                // doc=Jsoup.connect()
                doc = Jsoup.connect(Constants.WEBAPP_URL).timeout(100000).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            webView.loadDataWithBaseURL(null,doc.html(),
                    "text/html", "utf-8", null);

        }
    }
*/

}

