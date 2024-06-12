package com.example.dud_mobile.ui.lessons.grammar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dud_mobile.R;
import com.example.dud_mobile.models.GrammarLesson;
import com.squareup.picasso.Picasso;
import java.util.List;

public class GrammarAdapter extends RecyclerView.Adapter<GrammarAdapter.ViewHolder> {

    private Context context;
    private List<GrammarLesson> grammarLessons;

    public GrammarAdapter(Context context, List<GrammarLesson> grammarLessons) {
        this.context = context;
        this.grammarLessons = grammarLessons;
    }

    public void setGrammarLessons(List<GrammarLesson> grammarLessons) {
        this.grammarLessons = grammarLessons;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_grammar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(grammarLessons.get(position));
    }

    @Override
    public int getItemCount() {
        return grammarLessons != null ? grammarLessons.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView grammarTitle;
        private TextView grammarDescription;
        private ImageView grammarImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            grammarTitle = itemView.findViewById(R.id.grammarTitle);
            grammarDescription = itemView.findViewById(R.id.grammarDescription);
            grammarImage = itemView.findViewById(R.id.grammarImage);
        }

        public void bind(GrammarLesson grammarLesson) {
            grammarTitle.setText(grammarLesson.getTitle());
            grammarDescription.setText(grammarLesson.getDescription());
            Picasso.get().load(grammarLesson.getGrammarImage()).into(grammarImage);
        }
    }
}



