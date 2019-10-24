package br.com.firmino.teste;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.firmino.teste.adapter.AdapterEvents;
import br.com.firmino.teste.models.Event;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Events extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdapterEvents mAdapter;

    private List<Event> feedItem = new ArrayList<Event>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        getSupportActionBar().setTitle("Eventos");

        initViews();
    }

    private void initViews() {

        mRecyclerView = findViewById(R.id.rvEvents);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        Event e1 = new Event();
        e1.setTitle("Teste1");
        e1.setPlace("Picos");

        Event e2 = new Event();
        e2.setTitle("Teste2");
        e2.setPlace("Taverna Geek");

        feedItem.add(e1);
        feedItem.add(e2);

        mAdapter = new AdapterEvents(Events.this, feedItem);
        mRecyclerView.setAdapter(mAdapter);


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();


        return true;
    }
}
