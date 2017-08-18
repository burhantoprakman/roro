package vavien.agency.liman.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import vavien.agency.liman.R;
import vavien.agency.liman.Users;



/**
 * Created by ${Burhan} on 19.07.2017.
 * burhantoprakman@gmail.com
 */

public class VideosFragment extends Fragment implements MediaPlayer.OnCompletionListener {
    private VideoView videoView;
    private StorageReference mStorageRef, storage;
    private Uri uri;
    private DatabaseReference myDatabaseReference;
    private String loginKey2, pnumb1, pass, nameSurname, plakaNo;
    private String readStatu = "Okunmadı", watchStatu = "İzlenmedi";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.layout_videosfragment, container, false);
        videoView = rootview.findViewById(R.id.vv);
        final ProgressDialog progress = ProgressDialog.show(getActivity(), "UN RO-RO", "Güvenlik Videoları Yükleniyor Lüttfen Biraz Bekleyiniz...", true);
        progress.show();
        myDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        Bundle extras = getActivity().getIntent().getExtras();
        loginKey2 = extras.getString("loginKey");
        pnumb1 = extras.getString("phoneNumber");
        pass = extras.getString("Password");
        nameSurname = extras.getString("NameSurname");
        plakaNo = extras.getString("plakaNo");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReferenceFromUrl("gs://unroro-15b4e.appspot.com");
        mStorageRef.child("Videos/unroro.mp4").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                MediaController mediaController = new MediaController(getActivity());
                videoView.setVideoURI(uri);
                videoView.requestFocus();
                videoView.setMediaController(new MediaController(getActivity()));
                mediaController.setAnchorView(videoView);
                videoView.start();
                videoView.setOnCompletionListener(VideosFragment.this);
                progress.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progress.dismiss();

            }
        });


        return rootview;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // write logic here b'z it is called when fragment is visible to user
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Videoyu sonuna kadar izlediğinizi onaylıyor musunuz ?")
                .setCancelable(false)
                .setPositiveButton("Onaylıyorum", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference rf = rootRef.child("Users");
                        rf.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {

                                    if ((data.child("numb").getValue().equals(pnumb1)) && (data.child("pass").getValue().equals(pass))) {

                                        nameSurname = data.child("nameSurname").getValue().toString();
                                        plakaNo = data.child("plakaNo").getValue().toString();
                                        readStatu = data.child("readStatu").getValue().toString();
                                        watchStatu = "İzlendi";
                                        if (Objects.equals(readStatu, "Okundu")) {
                                            addUsers(nameSurname, pnumb1, pass, plakaNo, watchStatu, readStatu);
                                        } else {
                                            addUsers(nameSurname, pnumb1, pass, plakaNo, watchStatu, "Okunmadı");
                                        }


                                    }
                                }
                                Toast.makeText(getActivity(), " Onayladınız ", Toast.LENGTH_SHORT).show();

                               //Intent intent = new Intent(getActivity(), MainActivity.class);
                               //intent.putExtra("watchStatu",watchStatu);
                               //startActivity(intent);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }
                })
                .setNegativeButton("Tekrar İzle", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        watchStatu = "İzlenmedi";
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void addUsers(String nameSurname, String numb, String pass, String plakaNo, String watchStatu, String readStatu) {
        Users users = new Users(nameSurname, pnumb1, pass, plakaNo, watchStatu, readStatu);
        myDatabaseReference.child(loginKey2).setValue(users);
    }

}
