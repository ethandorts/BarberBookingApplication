package com.example.barberbookingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {
    EditText Email, Password, ReenterPassword, FirstName, Surname, PhoneNumber, DOB;
    Button btnRegister;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.EditTextEmailReg);
        Password = findViewById(R.id.editTextPasswordRegister);
        ReenterPassword = findViewById(R.id.editTextReenterPasswordRegister);
        FirstName = findViewById(R.id.editTextFName);
        Surname = findViewById(R.id.editTextSurname);
        PhoneNumber = findViewById(R.id.editTextMobileNum);
        DOB = findViewById(R.id.EditTextDOB);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password, firstName, surname, phoneNumber, dob;
                email = String.valueOf(Email.getText());
                password = String.valueOf(Password.getText());
                firstName = String.valueOf(FirstName.getText());
                surname = String.valueOf(Surname.getText());
                phoneNumber = String.valueOf(PhoneNumber.getText());
                dob = String.valueOf(DOB.getText());

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(firstName)
                        || TextUtils.isEmpty(surname) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(dob)) {
                    Toast.makeText(Register.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Create user here
                Customer customer = new Customer();
                customer.setFirstName(firstName);
                customer.setLastName(surname);
                customer.setEmail(email);
                customer.setPhoneNumber(phoneNumber);
                customer.setDateOfBirth(dob);

                // Register the user with Firebase Authentication
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Registration success, now save user details to Firestore
                                    saveCustomerToFirestore(customer);

                                    // Set a result code and finish the activity
                                    setResult(RESULT_OK);
                                    finish();
                                } else {
                                    // Registration failed, display a message to the user
                                    Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void saveCustomerToFirestore(Customer customer) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            db.collection("customers")
                    .document(currentUser.getUid())
                    .set(customer)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(Register.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Register.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
