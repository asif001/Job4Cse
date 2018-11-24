package cse.job.asif.job4cse.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

import cse.job.asif.job4cse.R;

public class user_signup extends AppCompatActivity {

    private EditText editName;
    private EditText editUsername;
    private EditText editEamil;
    private EditText editPassword;
    private EditText editConfirmPass;
    private Button btnSignUp;
    private boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        init();
    }

    public void init(){

        editName = findViewById(R.id.signName);
        editUsername = findViewById(R.id.SignUserName);
        editEamil = findViewById(R.id.signEmail);
        editPassword = findViewById(R.id.signPass);
        editConfirmPass = findViewById(R.id.signConfPass);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignUp();
            }
        });


    }


    public void doSignUp(){

        if(editName.getText().toString().isEmpty() || editUsername.getText().toString().isEmpty() || editPassword.getText().toString().isEmpty()
                || editConfirmPass.getText().toString().isEmpty() || editEamil.getText().toString().isEmpty()){

            Toast.makeText(getApplicationContext(),"Fill up all fields",Toast.LENGTH_LONG).show();

        }

        else if(!hasUser()){

            if(!editPassword.getText().toString().equals(editConfirmPass.getText().toString())){

                Toast.makeText(getApplicationContext(),"Password didnot match",Toast.LENGTH_LONG).show();

            }

            else{

                ParseUser user = new ParseUser();

                user.setUsername(editUsername.getText().toString());
                user.setEmail(editEamil.getText().toString());
                user.setPassword(editPassword.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e == null){

                            ParseObject parseObject = new ParseObject("UserDetails");
                            parseObject.put("name",editName.getText().toString());
                            parseObject.put("username",editUsername.getText().toString());
                            parseObject.put("email",editEamil.getText().toString());

                            parseObject.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if(e == null){

                                        Toast.makeText(getApplicationContext(),"Successgully signed up!",Toast.LENGTH_LONG).show();
                                        finish();

                                    }

                                }
                            });

                        }

                        else{

                            Toast.makeText(getApplicationContext(),"Did not signup",Toast.LENGTH_LONG).show();

                        }

                    }
                });

            }

        }


    }


    public boolean hasUser(){

        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("UserDetails");

        parseQuery.whereEqualTo("username",editUsername.getText().toString());

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(objects.size() > 0){

                    check = true;

                }

                else{

                    check = false;

                }

            }
        });

        return check;
    }


}
