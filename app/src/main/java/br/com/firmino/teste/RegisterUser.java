package br.com.firmino.teste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import br.com.firmino.teste.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterUser extends AppCompatActivity {

    private EditText name, email, pass;
    private Button register;
    private FirebaseAuth auth;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        name = findViewById(R.id.editTextUserName);
        email = findViewById(R.id.editTextUserEmail);
        pass = findViewById(R.id.editTextUserPass);

        register = findViewById(R.id.buttonUserRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = new User();
                u.setName(name.getText().toString());
                u.setEmail(email.getText().toString());
                u.setPass(pass.getText().toString());


                createUser(u);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

    }




    private void createUser(final User user){
        auth = FirebaseAuth.getInstance();


        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPass()).addOnCompleteListener(RegisterUser.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterUser.this, "Usuário cadastro com sucesso!", Toast.LENGTH_SHORT).show();


                    firebaseAuth.getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder()
                            .setDisplayName(user.getName())
                            .build());

                    user.setId(auth.getUid());
                    user.create();


                    //new Preferences(Register.this).commitUser(user.getId(), user.getName(), user.getType());

                    openLogin();
                }else{
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        Toast.makeText(RegisterUser.this, "Escolha uma senha que contenha letras e números!", Toast.LENGTH_SHORT).show();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        Toast.makeText(RegisterUser.this, "O e-mail indicado não é válido!", Toast.LENGTH_SHORT).show();
                    }catch (FirebaseAuthUserCollisionException e){
                        Toast.makeText(RegisterUser.this, "Já existe uma conta com esse e-mail!", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public void openLogin(){
        startActivity(new Intent(RegisterUser.this, Login.class));
        finish();
    }

}
