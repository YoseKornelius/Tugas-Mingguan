package com.example.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class fragment3 extends Fragment {
    private EditText noMhs;
    private EditText namaMhs;
    private EditText phoneMhs;
    private Button buttonSimpan;
    private Button buttonHapus;
    private String tamp;
    FirebaseFirestore firebaseFirestoreDB;
    View view;

    public fragment3() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment3,container,false);
        noMhs = view.findViewById( R.id.noMhs );
        namaMhs = view.findViewById( R.id.namaMhs );
        phoneMhs = view.findViewById( R.id.phoneMhs );
        buttonSimpan = view.findViewById( R.id.simpanButton );
        buttonHapus = view.findViewById( R.id.hapusButton );
        firebaseFirestoreDB = FirebaseFirestore.getInstance();

        buttonSimpan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noMhs.getText().toString().isEmpty() && namaMhs.getText().toString().isEmpty()){
                    noMhs.setError( "NIM & Nama Tidak Boleh KOSONG" );
                    noMhs.requestFocus();
                }
                else{
                    Mahasiswa mhs = new Mahasiswa( noMhs.getText().toString(),
                            namaMhs.getText().toString(),
                            phoneMhs.getText().toString());
                    final DocumentReference documentReference = firebaseFirestoreDB.collection( "DaftarMhs" ).document(String.valueOf( noMhs.getText() ));
                    documentReference.get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot.exists()){
                                    tambahDataMahasiswa();
                                    Toast.makeText( getActivity(), "Mahasiswa berhasil diupdate",
                                            Toast.LENGTH_SHORT).show();

                                }else {
                                    tambahDataMahasiswa();
                                }
                            }else {
                                Toast.makeText( requireActivity(), "error gan" + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    } );


                    tamp = noMhs.getText().toString();
                    noMhs.getText().clear();
                    namaMhs.getText().clear();
                    phoneMhs.getText().clear();
                    getDataMahasiswa();

                }
            }
        } );

        buttonHapus.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestoreDB.collection("DaftarMhs").document(String.valueOf(noMhs.getText()))
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                noMhs.setText("");
                                namaMhs.setText("");
                                phoneMhs.setText("");
                                Toast.makeText(requireActivity(), "Mahasiswa berhasil dihapus",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireActivity(), "Error deleting document: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        } );



        return view;

    }

    private void tambahDataMahasiswa(){
        Mahasiswa mhs = new Mahasiswa( noMhs.getText().toString(),
                namaMhs.getText().toString(),
                phoneMhs.getText().toString());
        firebaseFirestoreDB.collection( "DaftarMhs" ).document(String.valueOf( noMhs.getText().toString() )).set( mhs )
                .addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText( requireActivity(), "Mahasiswa berhasil didaftarkan",
                                Toast.LENGTH_SHORT).show();
                    }
                } )
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText( requireActivity(), "Errpr" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                } );
    }

    private void getDataMahasiswa() {
        DocumentReference docRef = firebaseFirestoreDB.collection("DaftarMhs").document(String.valueOf( tamp ));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Mahasiswa mhs = document.toObject(Mahasiswa.class);
                        noMhs.setText(mhs.getNim());
                        namaMhs.setText(mhs.getNama());
                        phoneMhs.setText(mhs.getPhone());
                    } else {
                        Toast.makeText(requireActivity(), "Document tidak ditemukan",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireActivity(), "Document error : " + task.getException(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
