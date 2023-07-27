package com.project.term_project.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.project.term_project.R;
import com.project.term_project.activity.service.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        EditText emailEditText = findViewById(R.id.signup_email_edittext);
        EditText userNameEditText = findViewById(R.id.signup_username_edittext);
        EditText passwordEditText = findViewById(R.id.signup_password_edittext);
        EditText passwordCheckEditText = findViewById(R.id.signup_password_check_edittext);

        Button signupButton = findViewById(R.id.signup_signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String userName = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String passwordCheck = passwordCheckEditText.getText().toString();

                if(email.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (userName.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(passwordCheck)) {
                    Toast.makeText(SignupActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                } else {
                    postSignupRequest(email, password, userName);
                }
            }
        });
    }

    private void postSignupRequest(String email, String password, String userName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.79.134.121:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("email", email);
        requestBody.addProperty("password", password);
        requestBody.addProperty("userName", userName);

        Call<ResponseBody> call = apiService.postSignupRequest(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        Log.d("my", "signup : " + responseBody);
                        JSONObject jsonResponse = new JSONObject(responseBody);

                        String message = jsonResponse.getString("message");
                        if (jsonResponse.getBoolean("success")) {
                            // goto Main
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(SignupActivity.this, "오류 발생", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(SignupActivity.this, "오류 발생", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // API 요청 실패 처리
                    Toast.makeText(SignupActivity.this, "요청 오류 발생", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // API 요청 실패 처리
                Toast.makeText(SignupActivity.this, "요청 오류 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }
}