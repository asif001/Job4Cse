package cse.job.asif.job4cse.Activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import cse.job.asif.job4cse.HelperClass.checkLogn;

import cse.job.asif.job4cse.R;

public class UserLogin extends AppCompatActivity {

    private EditText editUserName;
    private EditText editPassword;
    private Button   btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        init();
    }


    private void init(){

            editUserName = findViewById(R.id.userName);
            editPassword = findViewById(R.id.userPass);
            btnLogin = findViewById(R.id.userLogin);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doLogin(editUserName.getText().toString(), editPassword.getText().toString());
                }
            });

    }

    private void doLogin(String userName,String userpassword){

        if(userName.isEmpty() || userpassword.isEmpty()){

            Toast.makeText(getApplicationContext(),"Invalid username or password",Toast.LENGTH_LONG).show();

        }

        else{

            ParseUser.logInInBackground(userName, userpassword, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if(user != null){

                        checkLogn.isLogged = 1;
                        startActivityForResult(new Intent(getApplicationContext(),UserDashboard.class),1);

                    }
                    else{

                        Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_SHORT).show();

                    }

                }
            });

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        setResult(RESULT_OK,new Intent());
        finish();

    }
}
