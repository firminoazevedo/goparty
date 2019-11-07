package br.com.firmino.teste;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import br.com.firmino.teste.models.User;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText email, pass;
    private FirebaseAuth auth;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().hide();

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login(email.getText().toString(), pass.getText().toString());
            }
        });



        findViewById(R.id.btnUserReg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, RegisterUser.class));
            }
        });



        init();
    }

    private void init(){
        email = findViewById(R.id.editTextEmail);
        pass = findViewById(R.id.editTextPass);

        auth = FirebaseAuth.getInstance();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Realizando login ...");


        if(auth.getCurrentUser() != null){
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }

    }



    private void login(String email, String pass){
        mProgressDialog.show();

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Login realizado com sucesso!

                    startActivity(new Intent(Login.this, Events.class));
                    finish();

                    /*

                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("users").child(auth.getUid()).limitToLast(1).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User u = dataSnapshot.getValue(User.class);


                            //Save User
                            // new Preferences(Login.this).commitUser(u);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            mProgressDialog.hide();
                            Toast.makeText(Login.this, "Falha ao realizar login!", Toast.LENGTH_SHORT).show();
                        }
                    });

                     */

                }else {
                    //Falha ao fazer login
                    mProgressDialog.hide();
                    Toast.makeText(Login.this, "Falha ao realizar login!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
