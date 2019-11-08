package br.com.firmino.teste;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import br.com.firmino.teste.models.Event;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class Profile extends AppCompatActivity {

    private EditText title, place, local, data, horario;
    private DatabaseReference databaseReference;

    private Event event = null;

    private ProgressDialog mProgressDialog;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();


        Bundle extras;
        extras = getIntent().getExtras();

        if (getIntent().hasExtra("events")) {
            event = new Gson().fromJson(extras.getString("events"), Event.class);
            title.setText(event.getTitle());
            place.setText(event.getPlace());
            data.setText(event.getData());
            horario.setText(event.getHorario());
            local.setText(event.getLocal());
            button.setText("ATUALIZAR");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAndUpdate();
            }
        });

    }

    private void init(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mProgressDialog = new ProgressDialog(this);

        button = findViewById(R.id.button);

        title = findViewById(R.id.edtTitle11);
        place = findViewById(R.id.edtDescricao1);
        local = findViewById(R.id.edtLocal1);
        data = findViewById(R.id.edtData1);
        horario = findViewById(R.id.edtHorario1);
    }


    private void createAndUpdate(){
        if (event == null) {
            mProgressDialog.setMessage("Aguarde! Cadastrando Evento ...");
        } else {
            mProgressDialog.setMessage("Aguarde! Atualizando Evento ...");
        }
        mProgressDialog.show();


        String key = databaseReference.child("events").push().getKey();

        Event e = new Event();
        e.setTitle(title.getText().toString());
        e.setData(data.getText().toString());
        e.setHorario(horario.getText().toString());
        e.setPlace(place.getText().toString());
        e.setLocal(local.getText().toString());

        if (event == null) {
            e.setId(key);
            databaseReference.child("events").child(key).setValue(e);
        } else {
            e.setId(event.getId());
            databaseReference.child("events").child(event.getId()).setValue(e);
        }

        mProgressDialog.dismiss();

        finish();
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home)
            this.finish();

        return true;
    }
}
