package com.example.dud_mobile.ui.lessons;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dud_mobile.R;
import com.example.dud_mobile.databinding.ItemLessonBinding;
import com.example.dud_mobile.models.lessons.Lesson;
import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.ViewHolder> {

    private ItemLessonBinding binding;
    private Context context;
    private List<Lesson> lessons;

    public LessonsAdapter(Context context, List<Lesson> lessons) {
        this.context = context;
        this.lessons = lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemLessonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(lessons.get(position));
    }

    @Override
    public int getItemCount() {
        return lessons != null ? lessons.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemLessonBinding binding;

        public ViewHolder(@NonNull ItemLessonBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void onBind(Lesson lesson) {
            binding.titleLesson.setText(lesson.getTitle());
            binding.descrLesson.setText(lesson.getDescription());

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Создаем Bundle и добавляем lessonId в качестве аргумента
                    Bundle bundle = new Bundle();
                    bundle.putInt("lessonId", lesson.getId());

                    // Находим NavController и выполняем навигацию к GrammarFragment с переданным lessonId
                    NavController navController = Navigation.findNavController((Activity) itemView.getContext(), R.id.nav_host);
                    navController.navigate(R.id.action_lessonsFragment_to_grammarFragment, bundle);
                }
            });
        }
    }
}
