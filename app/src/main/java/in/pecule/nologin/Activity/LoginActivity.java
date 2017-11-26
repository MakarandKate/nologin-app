package in.pecule.nologin.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import in.pecule.nologin.BO.User;
import in.pecule.nologin.R;
import in.pecule.nologin.Util.SessionManager;

public class LoginActivity extends AppCompatActivity {
    Button buttonSave;
    EditText editTextName,editTextPhone,editTextEmail,editTextAge;
    SessionManager sm;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext=this;
        sm=new SessionManager(this);
        editTextName=(EditText)findViewById(R.id.editTextName);
        editTextPhone=(EditText)findViewById(R.id.editTextPhone);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextAge=(EditText)findViewById(R.id.editTextAge);
        buttonSave=(Button)findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editTextName.getText().toString();
                String email=editTextEmail.getText().toString();
                String phone=editTextPhone.getText().toString();
                int age=Integer.parseInt(editTextAge.getText().toString());

                User user=new User(name, phone, email,age);

                sm.login(user);
                Intent intent=new Intent(mContext,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
