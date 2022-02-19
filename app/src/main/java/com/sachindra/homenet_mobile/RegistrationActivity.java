package com.sachindra.homenet_mobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sachindra.homenet_mobile.models.User;
import com.sachindra.homenet_mobile.services.RetrofitClientInstance;
import com.sachindra.homenet_mobile.services.UserClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    EditText email, firstname, lastname, telephone, password, confirmPassword;
    TextView login;
    Button register;

    private ProgressDialog mProgressDialog;

    private UserClient userClient = RetrofitClientInstance.getRetrofitInstance().create(UserClient.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        firstname = findViewById(R.id.first_name);
        lastname = findViewById(R.id.last_name);
        telephone = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.password_2);
        register = findViewById(R.id.register_button);
        login = findViewById(R.id.login);

        mProgressDialog = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private void register() {
        String _email = email.getText().toString();
        String _firstName = firstname.getText().toString();
        String _lastName = lastname.getText().toString();
        String _phoneNumber = telephone.getText().toString();
        String _password = password.getText().toString();
        String _password2 = confirmPassword.getText().toString();

        if (TextUtils.isEmpty(_email) || TextUtils.isEmpty(_firstName) || TextUtils.isEmpty(_lastName) || TextUtils.isEmpty(_phoneNumber) || TextUtils.isEmpty(_password) || TextUtils.isEmpty(_password2)) {
            Toast.makeText(this, "Please enter valid data!", Toast.LENGTH_SHORT).show();
        } else if (!_password.equals(_password2)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User();
            user.setEmail(_email);
            user.setFirstname(_firstName);
            user.setLastname(_lastName);
            user.setTelephone(_phoneNumber);
            user.setPassword(_password);
            user.setConfirmPassword(_password2);

            Call<ResponseBody> call = userClient.register(user);

            //Show progress
            mProgressDialog.setMessage("Creating new account...");
            mProgressDialog.show();

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    mProgressDialog.dismiss();
                    if (response.isSuccessful()) {
                        Toast.makeText(RegistrationActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Email already in use", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(RegistrationActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
