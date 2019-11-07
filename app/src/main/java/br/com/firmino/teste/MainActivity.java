package br.com.firmino.teste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.firmino.teste.adapter.AdapterEvents;
import br.com.firmino.teste.models.Event;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private RecyclerView mRecyclerView;
    private AdapterEvents mAdapter;

    private List<Event> feedItem = new ArrayList<Event>();

    private Button btnCadFesta;
    private Button btnLogin;
    private Button btnRegister;

    private ImageButton imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            Toast.makeText(getApplicationContext(), "Bem vindo de volta " + user.getEmail() + "!", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }

        imgLogo = findViewById(R.id.imgLogo);

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FirebaseAuth.getInstance().signOut();
                    // signed out
                } catch (Exception e){
                    // an error
                }
            }
        });

        /*btnCadFesta = findViewById(R.id.btnCadastrar);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnCadFesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, CadFesta.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startActivity(new Intent(MainActivity.this, Events.class));
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterUser.class));
            }
        });*/


        mRecyclerView = findViewById(R.id.rvEvents);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        Event e1 = new Event();
        e1.setTitle("Teste1");
        e1.setPlace("Picos");

        Event e2 = new Event();
        e2.setTitle("Teste2");
        e2.setPlace("Taverna Geek");

        Event e3 = new Event();
        e3.setTitle("Teste2");
        e3.setPlace("Taverna Geek");

        Event e4 = new Event();
        e4.setTitle("Teste2");
        e4.setPlace("Taverna Geek");

        Event e5 = new Event();
        e5.setTitle("Teste2");
        e5.setPlace("Taverna Geek");

        feedItem.add(e1);
        feedItem.add(e2);
        feedItem.add(e3);
        feedItem.add(e4);
        feedItem.add(e5);

        mAdapter = new AdapterEvents(MainActivity.this, feedItem);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CadFesta.class));
            }
        });


    }


}
