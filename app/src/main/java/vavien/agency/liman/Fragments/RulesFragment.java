package vavien.agency.liman.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import vavien.agency.liman.R;
import vavien.agency.liman.Users;


public class RulesFragment extends Fragment implements View.OnClickListener {
    ScrollView sv;
    Button btn_okey;
    private DatabaseReference myDatabaseReference;
    private String loginKey2, pnumb1, pass, nameSurname, plakaNo;
    private String watchStatu;
    private String readStatu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.layout_rulesfragment, container, false);
        sv = rootview.findViewById(R.id.list);
        btn_okey = rootview.findViewById(R.id.button2);
        btn_okey.setOnClickListener(this);
        myDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        Bundle extras = getActivity().getIntent().getExtras();
        loginKey2 = extras.getString("loginKey");
        pnumb1 = extras.getString("phoneNumber");
        pass = extras.getString("Password");
        nameSurname = extras.getString("NameSurname");
        plakaNo = extras.getString("plakaNo");


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
    public void onClick(View view) {
        if (sv.getChildAt(sv.getChildCount() - 1).getBottom() - (sv.getHeight() + sv.getScrollY()) == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Kuralları sonuna kadar okuduğunuzu onaylıyor musunuz ?")
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
                                            watchStatu=data.child("watchStatu").getValue().toString();
                                            readStatu = "Okundu";
                                            if(Objects.equals(watchStatu, "İzlendi")){

                                            addUsers(nameSurname, pnumb1, pass, plakaNo, watchStatu, readStatu);
                                            }else
                                            {
                                                addUsers(nameSurname, pnumb1, pass, plakaNo, "İzlenmedi", readStatu);
                                            }

                                        }
                                    }
                                    Toast.makeText(getActivity(), " Onayladınız ", Toast.LENGTH_SHORT).show();
                                    //Intent intent = new Intent(getActivity(), MainActivity.class);
                                    //intent.putExtra("readStatu",readStatu);
                                    //startActivity(intent);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }
                    })
                    .setNegativeButton("Tekrar Oku", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            readStatu = "Okunmadı";
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            Toast.makeText(getActivity(), "Lütfen kuralları sonuna kadar okuyunuz !", Toast.LENGTH_SHORT).show();
        }
    }

    private void addUsers(String nameSurname, String numb, String pass, String plakaNo, String watchStatu, String readStatu) {
        Users users = new Users(nameSurname, pnumb1, pass, plakaNo, watchStatu, readStatu);
        myDatabaseReference.child(loginKey2).setValue(users);
    }
}
