package com.project.term_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.project.term_project.activity.service.SharedPreferenceService;

import org.json.JSONArray;
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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailEditText = findViewById(R.id.login_email_edittext);
        EditText passwordEditText = findViewById(R.id.login_password_edittext);
        Button loginButton = findViewById(R.id.login_login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }

                postLoginRequest(email, password);
            }
        });
    }

    private void postLoginRequest(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://52.79.134.121:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("email", email);
        requestBody.addProperty("password", password);

        Call<ResponseBody> call = apiService.postLoginRequest(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        Log.d("my", "login : " + responseBody);
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        String message = jsonResponse.getString("message");

                        if (jsonResponse.getBoolean("success")) {
                            JSONObject dataObject = jsonResponse.getJSONObject("data");
                            JSONObject jwtInfoObject = dataObject.getJSONObject("jwtInfo");
                            String jwt = jwtInfoObject.getString("accessToken");
                            jwt = "Bearer " + jwt;

                            Context context = LoginActivity.this;
                            Intent gotoHomeActivity = new Intent(context, HomeActivity.class);

                            // jwt 업데이트
                            SharedPreferenceService jwtService = new SharedPreferenceService();
                            if (jwtService.getJWTFromSharedPreferences(context).isEmpty()) {
                                jwtService.saveJWTToSharedPreferences(context, jwt);
                            } else {
                                jwtService.updateJWTInSharedPreferences(context, jwt);
                            }

                            startActivity(gotoHomeActivity);
                        } else {
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.d("my", e.toString());
                        Toast.makeText(LoginActivity.this, "요청 오류 발생", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.d("my", e.toString());
                        Toast.makeText(LoginActivity.this, "요청 오류 발생", Toast.LENGTH_SHORT).show();
                    }
                    // API 응답 처리
                } else {
                    // API 요청 실패 처리
                    Toast.makeText(LoginActivity.this, "요청 오류 발생", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // API 요청 실패 처리
                Toast.makeText(LoginActivity.this, "요청 오류 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }
}