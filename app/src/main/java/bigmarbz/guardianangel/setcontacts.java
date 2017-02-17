package bigmarbz.guardianangel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;

import java.util.ArrayList;
import java.util.List;

public class setcontacts extends AppCompatActivity implements View.OnClickListener{
    Button AddContact;
    Button newlist,emergencycontacts,distresscontacts;

    private ListView listView;
    public static List<Product> productList;
    public static List<Product> emergencyList;
    Button removecontactpopup;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setcontacts);
        if (removecontact.productList == null){
            removecontact.productList = new ArrayList<>();
        }

        if (removeemergency.emergencyList == null){
            removeemergency.emergencyList = new ArrayList<>();
        }


        Button AddContact = (Button) findViewById(R.id.addcontact);
        Button distresscontacts = (Button) findViewById(R.id.distresscontacts);
        Button emergencycontacts = (Button) findViewById(R.id.emergencycontacts);
        Button back = (Button) findViewById(R.id.back);


        listView = (ListView) findViewById(R.id.mylistview);
        //get list of product



        removecontactpopup = (Button) findViewById(R.id.removecontactpopup);

        back.setOnClickListener(this);
        distresscontacts.setOnClickListener(this);
        emergencycontacts.setOnClickListener(this);
        AddContact.setOnClickListener(this);

        final Button removecontactpopup = (Button)findViewById(R.id.removecontactpopup);
        removecontactpopup.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);

                Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                Button removecontact = (Button)popupView.findViewById(R.id.contact);
                Button removeemergency = (Button)popupView.findViewById(R.id.emergencycontact);
                removecontact.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View a) {
                        Intent intent1 = new Intent(setcontacts.this, removecontact.class);
                        setcontacts.this.startActivity(intent1);
                    }});

                removeemergency.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(View b) {
                            Intent intent2 = new Intent(setcontacts.this, removeemergency.class);
                            setcontacts.this.startActivity(intent2);
                        }});

                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }});

                popupWindow.showAsDropDown(removecontactpopup, 50, 10);

            }});
    }






    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addcontact:
                Intent intent1 = new Intent(setcontacts.this, addcontact.class);
                setcontacts.this.startActivity(intent1);
                break;
            case R.id.distresscontacts:
                Intent intent2 = new Intent(setcontacts.this, distresscontacts.class);
                setcontacts.this.startActivity(intent2);
                break;
            case R.id.emergencycontacts:
                Intent intent3 = new Intent(setcontacts.this, emergencycontacts.class);
                setcontacts.this.startActivity(intent3);
                break;
            case R.id.back:
                Intent intent4 = new Intent(setcontacts.this, MainActivity.class);
                setcontacts.this.startActivity(intent4);

        }
    }



}
