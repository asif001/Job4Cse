package cse.job.asif.job4cse.Activities;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

import cse.job.asif.job4cse.HelperClass.checkLogn;

import cse.job.asif.job4cse.R;

public class user_profile extends AppCompatActivity {

    private ImageView imageView;
    private TextView uploadCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        init();
    }

    private void init(){

        imageView = findViewById(R.id.logout);
        uploadCV = findViewById(R.id.uploadCV);

        uploadCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,7);


            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {

                        checkLogn.isLogged = 0;
                        setResult(RESULT_OK,new Intent());
                        finish();

                    }
                });

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case 7:
                if(resultCode == RESULT_OK){

                    String path = data.getData().getPath();

                    //path = path.replace("/document/primary:","/");

                    final ParseFile parseFile = new ParseFile(new File(path));
                    Toast.makeText(getApplicationContext(),path,Toast.LENGTH_LONG).show();

                    parseFile.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Files");

                            parseQuery.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());

                            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> objects, ParseException e) {

                                    if(objects.size() > 0){

                                        objects.get(0).put("CV",parseFile);
                                        objects.get(0).saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }

                                    else{

                                        ParseObject parseObject = new ParseObject("Files");

                                        parseObject.put("username",ParseUser.getCurrentUser().getUsername());
                                        parseObject.put("CV",parseFile);

                                        parseObject.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }

                                }
                            });

                        }
                    });

                }

                break;

        }

    }



}
