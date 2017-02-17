package bigmarbz.guardianangel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class sethome extends AppCompatActivity implements View.OnClickListener{
    Button set, back;
    EditText address;
    public static String homeaddresslocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sethome);

        address = (EditText) findViewById(R.id.editText);
        set = (Button) findViewById(R.id.set);
        back = (Button) findViewById(R.id.back);

        set.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.set:
                String homeaddress = address.getText().toString();
                homeaddress.replaceAll("\\s","+");
                this.homeaddresslocation = homeaddress;
                Toast.makeText(getApplicationContext(),"Added address!",Toast.LENGTH_SHORT).show();
                break;

            case R.id.back:
                Intent intent4 = new Intent(sethome.this, MainActivity.class);
                sethome.this.startActivity(intent4);
                break;

        }
    }

}
