package com.slngl.moviebank.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.slngl.moviebank.base.AppConstants;
import com.slngl.moviebank.databinding.ItemCastBinding;
import com.slngl.moviebank.model.Cast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private final Context context;
    private ArrayList<Cast> casts;
    public CastAdapter(Context context, ArrayList<Cast> castList) {
        casts=castList;
        this.context = context;
    }

    static class CastViewHolder extends RecyclerView.ViewHolder{

        ItemCastBinding binding;
        public CastViewHolder(@NonNull @NotNull ItemCastBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    @NotNull
    @Override
    public CastAdapter.CastViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        com.slngl.moviebank.databinding.ItemCastBinding binding = ItemCastBinding.inflate(inflater, parent, false);
        return new CastViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CastAdapter.CastViewHolder holder, int position) {
        holder.binding.castName.setText(casts.get(position).getName());
        holder.binding.castRole.setText(casts.get(position).getCharacter());
        if (casts.get(position).getProfile_path()!=null){
            Glide.with(context).load(AppConstants.ImageBaseURL + casts.get(position).getProfile_path())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.binding.castImage);
        }else{
            holder.binding.castImage.setImageResource(android.R.drawable.ic_menu_report_image);
        }
    }

    @Override
    public int getItemCount() {
        return casts == null ? 0 : casts.size();
    }

    public void setCastList(ArrayList<Cast> castList){
        this.casts=castList;
    }
}
