package br.com.firmino.teste.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import br.com.firmino.teste.CadFesta;
import br.com.firmino.teste.R;
import br.com.firmino.teste.models.Event;

public class AdapterEvents extends RecyclerView.Adapter<AdapterEvents.MyViewHolder> {
    private Context context;
    private List<Event> feedItemList;

   // private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewTitle, mTextViewPlace;
        public ImageView mImageViewEvent;
        public MyViewHolder(View v) {
            super(v);

            this.mTextViewTitle = v.findViewById(R.id.textViewTitle);
            this.mTextViewPlace = v.findViewById(R.id.textViewPlace);

            this.mImageViewEvent = v.findViewById(R.id.imageViewEvent);
        }
    }

    public AdapterEvents(Context context, List<Event> feedItemList) {
        this.context = context;
        this.feedItemList = feedItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, null);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Event item = feedItemList.get(position);

        holder.mTextViewTitle.setText(Html.fromHtml(item.getTitle()));
        holder.mTextViewPlace.setText(Html.fromHtml(item.getPlace()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CadFesta.class);
                i.putExtra("events", new Gson().toJson(item));
                context.startActivity(i);
            }
        });

        /*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preferences.getUserType() == 1){
                    Intent i = new Intent(context, ViewAgendament.class);
                    i.putExtra("agendament", new Gson().toJson(item));
                    context.startActivity(i);
                }else {
                    if(item.getStatus() == 0) {

                        //cancelamento
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Desseja realmente cancelar essse agendamento?");
                        builder.setMessage(item.getTitle());

                        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                item.setStatus(3);

                                databaseReference.child("agendaments").child(item.getId())
                                        .setValue(item)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                } else {

                                                }
                                            }
                                        });
                            }
                        });

                        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.create().show();

                    }
                }
            }
        }); */

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return feedItemList.size();
    }
}