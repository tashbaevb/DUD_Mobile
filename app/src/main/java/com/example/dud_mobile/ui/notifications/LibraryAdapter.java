package com.example.dud_mobile.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dud_mobile.constant.ConstantAPI;
import com.example.dud_mobile.databinding.ItemLibraryBinding;
import com.example.dud_mobile.models.content.Library;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    private Context context;
    private List<Library> libraries;
    private LibraryAdapter.OnItemClickListener listener;

    public LibraryAdapter(Context context, List<Library> libraries) {
        this.context = context;
        this.libraries = libraries;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }

    public void setOnItemClickListener(LibraryAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public LibraryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLibraryBinding binding = ItemLibraryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LibraryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryAdapter.ViewHolder holder, int position) {
        holder.bind(libraries.get(position));
    }

    @Override
    public int getItemCount() {
        return libraries != null ? libraries.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemLibraryBinding binding;

        public ViewHolder(ItemLibraryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }

        public void bind(Library library) {
            binding.titleBook.setText(library.getTitle());
            binding.levelBook.setText(library.getLevel().getLevelName());


            Picasso.get().load(ConstantAPI.BASE_URL + library.getFilePath()).into(binding.imgBook);
        }
    }
}
