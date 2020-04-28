package com.example.login;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class fragmentKamera extends Fragment {

    //public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "settingsPreference";
    private static final int MY_PERMISSION_REQUEST_CAMERA = 100;
    ImageView imageView;
    Button btKamera;

    private Button btnOpenCamera;


    Context context;
    View view;

    public fragmentKamera() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_kamera, container, false );
        btnOpenCamera = view.findViewById( R.id.btn_kamera );

        return view;

    }

    @Override 
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraHardware();

            }
        });

    }

    private void checkCameraHardware() {
        AlertDialog alertDialog = new AlertDialog.Builder(requireActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Aplikasi Membutuhkan akses kamera!");
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (getFromPref(requireContext(), ALLOW_KEY)) {
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Tidak Diijinkan", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            } else if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA)) {
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Tidak Diijinkan",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ijinkan",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    ActivityCompat.requestPermissions(requireActivity(),
                                            new String[]{Manifest.permission.CAMERA},
                                            MY_PERMISSION_REQUEST_CAMERA);
                                }
                            });
                    alertDialog.show();
                } else {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSION_REQUEST_CAMERA);
                }
            }
        } else {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivity(intent);
        }
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                        requireActivity(), permission);
                        if (showRationale) {
                            AlertDialog alertDialog = new AlertDialog.Builder(requireActivity()).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("App needs to access the Camera.");
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            ActivityCompat.requestPermissions(requireActivity(),
                                                    new String[]{Manifest.permission.CAMERA},
                                                    MY_PERMISSION_REQUEST_CAMERA);
                                        }
                                    });
                            alertDialog.show();
                        } else if (!showRationale) {
                            SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = myPrefs.edit();
                            //agak ragu2
                            prefsEditor.putBoolean(ALLOW_KEY, true);
                            prefsEditor.commit();
                        }
                    }
                }
            }
        }
    }
}
