package com.example.dud_mobile.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.dud_mobile.R;
import com.example.dud_mobile.databinding.FragmentLoginBinding;
import com.example.dud_mobile.models.user.CurrentUser;
import com.example.dud_mobile.models.user.LoginResponse;
import com.example.dud_mobile.remote_data.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    NavController navController;
    String emailUserIdentify;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding
                .inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnLogin.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            if (!isEmptyEditTextLogin()) {
                loginUser(new CurrentUser(binding.email.getText().toString(),
                        binding.password.getText().toString()));
            }
        });
        binding.btnToRegistration.setOnClickListener(v1 -> {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
            navController.navigate(R.id.action_loginFragment_to_registrationFragment);
        });
    }

    private void loginUser(CurrentUser currentUser) {
        try {
            Call<LoginResponse> response = RetrofitClient.getInstance().getApi().checkLoginUser(currentUser);

            response.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {

                        String access_token = response.body().getAccess_token();
                        Log.d("API", "Access Token: " + access_token);


//                        emailUserIdentify = binding.email.getText().toString();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("identify", emailUserIdentify);

                        Bundle bundle_token = new Bundle();
                        bundle_token.putString("token", access_token);

                        try {
                            SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor prefLoginEdit = preferences.edit();
                            prefLoginEdit.putBoolean("loggedin", true);
                            prefLoginEdit.putString("token", access_token);
                            prefLoginEdit.apply();

                            navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
                            navController.navigate(R.id.action_loginFragment_to_navigation_home);
                        } catch (Exception e) {
                            Log.d("API", "Token error: " + e.toString());
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("API", t.toString());
                    binding.textError.setText("FAILER LOGIN!" + toString());
                }
            });
        } catch (Exception e) {
            Log.d("API", e.toString());
        }
    }


    private Boolean isEmptyEditTextLogin() {

        if (binding.email.getText().toString().isEmpty()
                || binding.password.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "Empty EditText", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}