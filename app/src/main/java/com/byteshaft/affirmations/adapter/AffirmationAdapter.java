package com.byteshaft.affirmations.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.byteshaft.affirmations.AffirmationEntity;
import com.byteshaft.affirmations.R;

import java.util.List;

public class AffirmationAdapter extends RecyclerView.Adapter<AffirmationAdapter.ViewHolder> {

    public List<AffirmationEntity> arrayList;

    public AffirmationAdapter(List<AffirmationEntity> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AffirmationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delegate_affirmation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AffirmationAdapter.ViewHolder holder, int position) {
        holder.affirmationText.setText(arrayList.get(position).getAffirmation());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView affirmationText;


        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            affirmationText = itemView.findViewById(R.id.tv_affirmation);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            AffirmationEntity text = arrayList.get(itemPosition);
            System.out.println(itemPosition + text.getAffirmation());
        }
    }
}
