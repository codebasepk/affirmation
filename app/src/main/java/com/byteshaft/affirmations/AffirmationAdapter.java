package com.byteshaft.affirmations;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class AffirmationAdapter extends RecyclerView.Adapter<AffirmationAdapter.ViewHolder> {

    private List<AffirmationEntity> arrayList;

    AffirmationAdapter(List<AffirmationEntity> arrayList) {
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
        holder.firstName.setText(arrayList.get(position).getAffirmation());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView firstName;
        TextView lastName;
        TextView email;


        public ViewHolder(View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.tv_affirmation);
        }
    }
}
