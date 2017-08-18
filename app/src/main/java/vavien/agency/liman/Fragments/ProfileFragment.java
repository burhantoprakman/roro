package vavien.agency.liman.Fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vavien.agency.liman.R;
import vavien.agency.liman.Users;

/**
 * Created by ${Burhan} on 27.07.2017.
 * burhantoprakman@gmail.com
 */

public class ProfileFragment extends Fragment {
    private DatabaseReference myDatabaseReference;
    private String pnumb1, pass, nameSurname, plakaNo;
    TextView frag_namesurname, frag_pass, frag_changebutton;
    TextView frag_plaka;
    EditText update_plaka, update_namesurname, update_pass, update_pass1;
    Button update_changebutton;
    private SharedPreferences preferences;
    LinearLayout fragLL, updateLL;
    String loginKey1;
    private String watchStatu, readStatu;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.layout_profilefragment, container, false);


        frag_plaka = rootview.findViewById(R.id.frag_plaka);
        frag_namesurname = rootview.findViewById(R.id.frag_namesurname);
        frag_pass = rootview.findViewById(R.id.frag_pass);
        frag_changebutton = rootview.findViewById(R.id.frag_changebutton);

        update_plaka = rootview.findViewById(R.id.update_plaka);
        update_namesurname = rootview.findViewById(R.id.update_namesurname);
        update_pass = rootview.findViewById(R.id.update_pass);
        update_pass1 = rootview.findViewById(R.id.update_pass1);
        update_changebutton = rootview.findViewById(R.id.update_changebutton);

        fragLL = rootview.findViewById(R.id.fragLL);
        updateLL = rootview.findViewById(R.id.updateLL);
        updateLL.setVisibility(View.INVISIBLE);
        fragLL.setVisibility(View.VISIBLE);


        Bundle extras = getActivity().getIntent().getExtras();
        pnumb1 = extras.getString("phoneNumber");
        pass = extras.getString("Password");
        nameSurname = extras.getString("NameSurname");
        plakaNo = extras.getString("plakaNo");
        loginKey1 = extras.getString("loginKey");
        watchStatu = extras.getString("watchStatu");
        readStatu = extras.getString("readStatu");


        myDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rf = rootRef.child("Users");
        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if ((data.child("numb").getValue().equals(pnumb1)) && (data.child("pass").getValue().equals(pass))) {
                        nameSurname = data.child("nameSurname").getValue().toString();
                        plakaNo = data.child("plakaNo").getValue().toString();
                        frag_namesurname.setText(nameSurname);
                        frag_pass.setText(pass);
                        frag_plaka.setText(plakaNo);


                    }
                }
                fragLL.requestLayout();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        frag_changebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLL.setVisibility(View.VISIBLE);
                fragLL.setVisibility(View.INVISIBLE);
            }
        });
        update_changebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(update_pass.getText()).equals(String.valueOf(update_pass1.getText()))) {
                    addUsers(update_namesurname.getText().toString(), pnumb1, update_pass.getText().toString(), update_plaka.getText().toString(), watchStatu, readStatu);
                    updateLL.setVisibility(View.INVISIBLE);
                    fragLL.setVisibility(View.VISIBLE);
                    fragLL.invalidate();

                } else
                    Toast.makeText(getActivity(), "Şifreler aynı olmalıdır.!", Toast.LENGTH_SHORT).show();

            }
        });

        return rootview;
    }

    private void addUsers(String nameSurname, String numb, String pass, String plakaNo, String watchStatu, String readStatu) {
        Users users = new Users(nameSurname, numb, pass, plakaNo, watchStatu, readStatu);
        myDatabaseReference.child(loginKey1).setValue(users);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // write logic here b'z it is called when fragment is visible to user
    }

    @Override
    public void onResume() {
        fragLL.requestLayout();
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
