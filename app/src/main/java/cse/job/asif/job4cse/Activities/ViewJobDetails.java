package cse.job.asif.job4cse.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cse.job.asif.job4cse.R;

public class ViewJobDetails extends AppCompatActivity {

    private WebView viewUrl;
    private Intent intent;
    //private String HostUrl;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job_details);

        intent = getIntent();

        url = intent.getStringExtra("Url");
        //HostUrl = intent.getStringExtra("Origin");

        viewUrl = findViewById(R.id.webView);
        viewUrl.loadUrl(url);
        viewUrl.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                view.loadUrl(request.getUrl().toString());
                return false;
            }
        });

        viewUrl.getSettings().setJavaScriptEnabled(true);

    }

}
