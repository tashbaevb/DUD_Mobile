package com.example.dud_mobile.ui.notifications;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.dud_mobile.constant.ConstantAPI;
import com.example.dud_mobile.databinding.FragmentLibraryDetailBinding;
import com.example.dud_mobile.models.content.Library;
import com.squareup.picasso.Picasso;

public class LibraryDetailFragment extends Fragment {

    private FragmentLibraryDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLibraryDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            Library library = getArguments().getParcelable("library");
            if (library != null) {
                binding.titleBookDetail.setText(library.getTitle());
                binding.authorBookDetail.setText(library.getAuthor());
                binding.descriptionBookDetail.setText(library.getDescription());
                binding.contentBookDetail.setText(library.getContent());

                Picasso.get().load(ConstantAPI.BASE_URL + library.getFilePath()).into(binding.imgBookDetail);
            }
        }


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}