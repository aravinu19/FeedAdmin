package com.ivara.aravi.feedadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth base;

    final String TAG ="Aravi's Debug Log";

    FirebaseAuth.AuthStateListener mlistener;

    TextView em,pw;

    Button s,r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        base = FirebaseAuth.getInstance();

        mlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    Toast.makeText(getApplicationContext(),"Already Logged In",Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(),checkData.class));
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                    Toast.makeText(getApplicationContext(),"Please Enter Your Credentials to Proceed !",Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };

        em = (TextView)findViewById(R.id.em);
        pw = (TextView)findViewById(R.id.pw);

        s = (Button)findViewById(R.id.log);
        r = (Button)findViewById(R.id.reg);

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = em.getText().toString().trim();
                String passd = pw.getText().toString().trim();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(passd))
                {
                    Toast.makeText(getApplicationContext(),"Email Or  Password Cannot be Empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    base.signInWithEmailAndPassword(email, passd)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful())
                                    {
                                        Toast.makeText(getApplicationContext(),"Login Failed ! Please Try Again...",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"login successful",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),checkData.class));
                                    }
                                }
                            });
                }
            }
        });

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUpPage.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        base.addAuthStateListener(mlistener);
    }
}
