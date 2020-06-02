package com.example.myspanishvocab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {

    public interface ItemClicked {
        void onItemClicked(int position);
    }

    private ArrayList<Word> words;

    ItemClicked activity;

    public WordAdapter(Context context, ArrayList<Word> list) {
        words = list;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView wordTv, detailsTv;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            wordTv = itemView.findViewById(R.id.wordTv);
            detailsTv = itemView.findViewById(R.id.detailsTv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(words.indexOf(v.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public WordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.word_list, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WordAdapter.ViewHolder viewHolder, int position) {

        viewHolder.itemView.setTag(words.get(position));

        viewHolder.wordTv.setText(words.get(position).getWord());
        viewHolder.detailsTv.setText(words.get(position).getPos());

    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public void listChanged(List<Word> updWords) {
        words = (ArrayList<Word>) updWords;
        notifyDataSetChanged();
    }
}
