package vavien.agency.liman;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.fabric.sdk.android.Fabric;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_numb, edt_pass, nameSurname1, passRepeat1, pass1,edt_plakaNo;
    private Button btn_sign, btn_signUp, btn_second_signUp;
    private DatabaseReference myDatabaseReference;
    private String userId,loginKey;
    private String watchStatu="İzlenmedi", readStatu="Okunmadı";
    static{
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);
        initView();
        listener();
        edt_numb.requestFocus();
        edt_pass.requestFocus();
        myDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");

    }

    public void initView() {
        edt_numb = (EditText) findViewById(R.id.number);
        edt_pass = (EditText) findViewById(R.id.password);
        btn_sign = (Button) findViewById(R.id.sign_in_button);
        btn_signUp = (Button) findViewById(R.id.sign_up_button);
        edt_plakaNo= (EditText) findViewById(R.id.plakaNo);


    }

    public void listener() {
        btn_sign.setOnClickListener(this);
        btn_signUp.setOnClickListener(this);
    }

    private void addUsers(String nameSurname, String numb, String pass,String plakaNo,String watchStatu,String readStatu) {
        Users users = new Users(nameSurname, numb, pass,plakaNo,watchStatu,readStatu);
        userId = myDatabaseReference.push().getKey();
        myDatabaseReference.child(userId).setValue(users);
    }


    @Override
    public void onClick(View v) {

            switch (v.getId()) {

            case R.id.sign_in_button:
                if (edt_numb.getText().toString().isEmpty() && edt_pass.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("U.N.RO-RO");
                    builder.setMessage("Lütfen Telefon Numaranızı Giriniz!");
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else {
                    final String pnumb1 = String.valueOf(edt_numb.getText());
                    final String pass1 = String.valueOf(edt_pass.getText());


                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference rf = rootRef.child("Users");

                    rf.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int i = 0;
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                i++;
                                if ((data.child("numb").getValue().equals(pnumb1)) && data.child("pass").getValue().equals(pass1)) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("phoneNumber",pnumb1);
                                    intent.putExtra("Password", pass1);
                                    intent.putExtra("NameSurname", String.valueOf(nameSurname1));
                                   loginKey= data.child("numb").getRef().getParent().getKey();
                                    intent.putExtra("loginKey",loginKey);
                                    startActivity(intent);

                                }
                                else
                                {
                                    if (dataSnapshot.getChildrenCount() == i) {
                                    Toast.makeText(LoginActivity.this, "Şifreniz yada Telefon Numaranız Yanlış", Toast.LENGTH_SHORT).show();
                                }
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                break;


            case R.id.sign_up_button:

                final String pnumb = String.valueOf(edt_numb.getText());

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference rf = rootRef.child("Users");

                rf.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int i = 0;

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            i++;
                            if (data.child("numb").getValue().equals(pnumb)) {
                                setContentView(R.layout.layout_signup);
                                pass1 = (EditText) findViewById(R.id.pass);
                                passRepeat1 = (EditText) findViewById(R.id.passRepeat);
                                nameSurname1 = (EditText) findViewById(R.id.nameSurname);
                                edt_plakaNo= (EditText) findViewById(R.id.plakaNo);
                                btn_second_signUp = (Button) findViewById(R.id.second_sign_up_button);
                                btn_second_signUp.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (!nameSurname1.getText().toString().equals("") && !edt_plakaNo.getText().toString().equals("") && String.valueOf(pass1.getText()).equals(String.valueOf(passRepeat1.getText()))  ) {
                                            addUsers(String.valueOf(nameSurname1.getText()), String.valueOf(edt_numb.getText()), String.valueOf(pass1.getText()), String.valueOf(edt_plakaNo.getText()),watchStatu,readStatu);
                                            Intent intent1 = new Intent(LoginActivity.this, LoginActivity.class);
                                            startActivity(intent1);

                                        }
                                        else
                                            if(nameSurname1.getText().toString().equals("")&& edt_plakaNo.getText().toString().equals("")) {
                                                Toast.makeText(LoginActivity.this, "Boş Alanları Doldurunuz.!", Toast.LENGTH_SHORT).show();
                                            }
                                            else if( !String.valueOf(pass1.getText()).equals(String.valueOf(passRepeat1.getText()))){
                                            Toast.makeText(LoginActivity.this, "Şifreleriniz Aynı Olmalıdır.!", Toast.LENGTH_SHORT).show();
                                        }
                                    }



                                });
                            } else if (dataSnapshot.getChildrenCount() == i) {
                                Toast.makeText(LoginActivity.this, "Telefon Numaranız Kayıtlı Değil", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


                break;

            default:
                break;
        }

    }



}
