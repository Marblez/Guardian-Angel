package bigmarbz.guardianangel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class addcontact extends AppCompatActivity implements View.OnClickListener {
    Button addcontact;
    Button back;
    Button emergency;
    EditText name;
    EditText phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontact);

        addcontact= (Button) findViewById(R.id.addcontact);
        back= (Button) findViewById(R.id.back);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        emergency = (Button) findViewById(R.id.emergency);

        addcontact.setOnClickListener(this);
        back.setOnClickListener(this);
        emergency.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String nameinfo = name.getText().toString();
        String phoneinfo = phone.getText().toString();
        switch(v.getId()){
            case(R.id.addcontact):
                removecontact.productList.add(new Product(R.drawable.distress, nameinfo,phoneinfo));
                Intent intent1 = new Intent(addcontact.this, setcontacts.class);
                addcontact.this.startActivity(intent1);
                Toast.makeText(getApplicationContext(),"Added Contact",Toast.LENGTH_SHORT).show();
                break;
            case(R.id.emergency):
                removeemergency.emergencyList.add(new Product(R.drawable.emergency, nameinfo,phoneinfo));
                Intent intent3 = new Intent(addcontact.this, setcontacts.class);
                addcontact.this.startActivity(intent3);
                Toast.makeText(getApplicationContext(),"Added Emergency Contact",Toast.LENGTH_SHORT).show();
                break;
            case(R.id.back):
                this.finish();
                break;
        }
    }
}
