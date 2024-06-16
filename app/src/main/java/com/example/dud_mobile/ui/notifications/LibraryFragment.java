package com.example.dud_mobile.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.dud_mobile.R;
import com.example.dud_mobile.databinding.FragmentLibraryBinding;
import com.example.dud_mobile.models.content.Library;
import com.example.dud_mobile.remote_data.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class LibraryFragment extends Fragment {

    private FragmentLibraryBinding binding;
    private LibraryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new LibraryAdapter(getActivity(), null);
        binding.rvCatalogM.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvCatalogM.setAdapter(adapter);

        loadLibrariesData();

        return root;
    }

    private void loadLibrariesData() {
        Call<List<Library>> apiCall = RetrofitClient.getInstance().getApi().getAllBooks();
        apiCall.enqueue(new Callback<List<Library>>() {
            @Override
            public void onResponse(Call<List<Library>> call, Response<List<Library>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Library> libraries = response.body();
                    for (Library library : libraries) {
                        Log.d("LibraryFragment", "Library: " + library.getTitle());
                    }
                    adapter.setLibraries(libraries);
                    adapter.notifyDataSetChanged(); // Добавьте это, чтобы уведомить адаптер об изменениях данных
                    adapter.setOnItemClickListener(new LibraryAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Library selectedLibrary = adapter.getLibraries().get(position);
                            navigateToLibraryDetail(selectedLibrary);
                        }
                    });
                } else {
                    Toast.makeText(requireActivity(), "Failed to load libraries", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<Library>> call, Throwable t) {
                Log.e("LibraryFragment", "Failed to fetch libraries: " + t.getMessage());
                Toast.makeText(requireActivity(), "Failed to load libraries", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToLibraryDetail(Library library) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("library", library);
        Navigation.findNavController(requireView()).navigate(R.id.action_navigation_notifications_to_libraryDetailFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}