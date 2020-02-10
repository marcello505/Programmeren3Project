package com.marcellohaddeman.programmeren3project;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ElementViewHolder> {
    private static final String TAG = "RecycleViewAdapter";

    private List<Element> elements;
    private Context context;


    public RecycleViewAdapter(Context context, List<Element> elements){
        this.elements = elements;
        this.context = context;
    }

    @NonNull
    @Override
    public ElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_item, parent, false);
        ElementViewHolder elementViewHolder = new ElementViewHolder(view);
        Log.v(TAG, "onCreateViewHolder: Finished method.");
        return elementViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ElementViewHolder holder, int position) {
        //Haal de image op en zet hem in de ImageView
        Glide.with(this.context)
                .asBitmap()
                .load(this.elements.get(position).getImageUrl())
                .into(holder.image);
        holder.getElementTitel().setText(elements.get(position).getTitel());
        holder.getGeografischeLigging().setText(elements.get(position).getGeografischeLigging());
        holder.getIdentificatie().setText(elements.get(position).getIdentificatie());
        holder.setContext(this.context);
        holder.setElement(elements.get(position));


    }

    @Override
    public int getItemCount() {
        if(this.elements == null){
            return 0;
        }
        return this.elements.size();
    }


    public class ElementViewHolder extends RecyclerView.ViewHolder{

        private static final String TAG = "ElementViewHolder";
        private Context context;
        private Element element;
        private ImageView image;
        private TextView elementTitel;
        private TextView geografischeLigging;
        private TextView identificatie;

        public ElementViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.v(this.TAG, "ElementViewHolder: Has been made.");
            this.image = itemView.findViewById(R.id.iv_element_item_image);
            this.elementTitel = itemView.findViewById(R.id.tv_element_item_titel);
            this.geografischeLigging = itemView.findViewById(R.id.tv_element_item_geografische_ligging);
            this.identificatie = itemView.findViewById(R.id.tv_element_item_identificatie);
            //Zet alle informatie die nodig is voor de DetailActivity in de intent Extras.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("titel", element.getTitel());
                    intent.putExtra("geografischeLigging", element.getGeografischeLigging());
                    intent.putExtra("imageURL", element.getImageUrl());
                    intent.putExtra("kunstenaar", element.getKunstenaar());
                    intent.putExtra("beschrijving", element.getBeschrijving());
                    intent.putExtra("materiaal", element.getMateriaal());
                    intent.putExtra("ondergrond", element.getOndergrond());
                    intent.putExtra("plaatsingsdatum", element.getPlaatsingDatum());
                    intent.putExtra("geoX", element.getGeoX());
                    intent.putExtra("geoY", element.getGeoY());
                    context.startActivity(intent);
                }
            });
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context){
            this.context = context;
        }

        public ImageView getImage() {
            return image;
        }

        public TextView getElementTitel() {
            return elementTitel;
        }

        public TextView getGeografischeLigging() {
            return geografischeLigging;
        }

        public TextView getIdentificatie() {
            return identificatie;
        }

        public Element getElement() {
            return element;
        }

        public void setElement(Element element) {
            this.element = element;
        }
    }
}
