package cse.job.asif.job4cse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewJobDetails extends AppCompatActivity {

    private TextView viewUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job_details);

        Intent intent = getIntent();

        String url = intent.getStringExtra("Url");

        viewUrl = findViewById(R.id.viewUrl);

        viewUrl.setText(url);

    }
}
