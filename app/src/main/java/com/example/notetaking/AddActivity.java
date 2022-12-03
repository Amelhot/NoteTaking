package com.example.notetaking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.notetaking.databinding.ActivityAddBinding;
import com.example.notetaking.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class AddActivity extends AppCompatActivity {

    ActivityAddBinding binding;
    private String title = "";
    private String description = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = binding.title.getText().toString();
                description = binding.description.getText().toString();
                saveNote();

            }
        });

    }
    //id,title,description

    private void saveNote() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String noteId = UUID.randomUUID().toString();
        Notes notes = new Notes(noteId,title, description, firebaseAuth.getUid());
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Notes").document(noteId)
                .set(notes)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this, "Note Saved",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}