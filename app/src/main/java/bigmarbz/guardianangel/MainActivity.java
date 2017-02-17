package bigmarbz.guardianangel;

import android.Manifest;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;
    Intent intenthero;
    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listView;
    private GridView gridView;
    private ListViewAdapter listViewAdapter;
    private GridViewAdapter gridViewAdapter;
    private List<Product> productList;

    private TrackGPS gps;
    String homeloc;
    double longitude;
    double longitudeindex;
    double latitudeindex;
    double latitude;

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Do any thing when user click to item
            Toast.makeText(getApplicationContext(), productList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            switch (productList.get(position).getTitle()) {
                case "Set Contacts":
                    Intent intent1 = new Intent(MainActivity.this, setcontacts.class); //Done
                    MainActivity.this.startActivity(intent1);
                    break;
                case "Distress Signal":
                    gps = new TrackGPS(MainActivity.this);

                    if (gps.canGetLocation()) {
                        longitude = gps.getLongitude();
                        latitude = gps.getLatitude();

                        Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
                        DistressSMS();
                    } else {
                        gps.showSettingsAlert();
                    }


                    break;

                case "Emergency Signal":
                    gps = new TrackGPS(MainActivity.this);

                    if (gps.canGetLocation()) {
                        longitude = gps.getLongitude();
                        latitude = gps.getLatitude();

                        Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
                        EmergencySMS();
                    } else {
                        gps.showSettingsAlert();
                    }
                    break;
                case "I'm Okay":
                    OkaySMS();
                    break;

                case "Go Home":
                    gps = new TrackGPS(MainActivity.this);
                    longitude = gps.getLongitude();
                    latitude = gps.getLatitude();
                    longitudeindex = longitude;
                    latitudeindex = latitude;
                    Intent webOpen = new Intent(android.content.Intent.ACTION_VIEW);
                    webOpen.setData(Uri.parse("https://www.google.com/maps/dir/("+latitude+","+longitude+")/"+sethome.homeaddresslocation));
                    startActivity(webOpen);

                    break;
                case "Set Home Location":
                    Intent intent3 = new Intent(MainActivity.this, sethome.class);
                    MainActivity.this.startActivity(intent3);
                    break;
                case "Instructions":
                    Intent intent4 = new Intent(MainActivity.this, instructions.class);
                    MainActivity.this.startActivity(intent4);
                    break;

                case "About App":
                    Intent intent5 = new Intent(MainActivity.this, aboutapp.class);
                    MainActivity.this.startActivity(intent5);
                    break;

                case "Track Location":
                    gps = new TrackGPS(MainActivity.this);
                    if (gps.canGetLocation()) {
                        longitude = gps.getLongitude();
                        latitude = gps.getLatitude();

                        for(int k =0; k < 10; k++){
                            double longituder = gps.getLongitude();
                            double latituder = gps.getLatitude();
                            TrackLocation();
                            try {
                                Thread.sleep(120000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            TrackLocation();
                        }
                    } else {
                        gps.showSettingsAlert();
                    }

                    break;

            }
        }
    };
    private int currentViewMode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (productList == null) {
            productList = new ArrayList<>();
        }


        stubList = (ViewStub) findViewById(R.id.stub_list);
        stubGrid = (ViewStub) findViewById(R.id.stub_grid);

        //Inflate ViewStub before get view

        stubList.inflate();
        stubGrid.inflate();

        listView = (ListView) findViewById(R.id.mylistview);
        gridView = (GridView) findViewById(R.id.mygridview);

        //get list of product
        getProductList();

        //Get current view mode in share reference
        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);//Default is view gridview
        //Register item lick
        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);

        switchView();

    }

    private void switchView() {

        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            //Display listview
            stubList.setVisibility(View.VISIBLE);
            //Hide gridview
            stubGrid.setVisibility(View.GONE);
        } else {
            //Hide listview
            stubList.setVisibility(View.GONE);
            //Display gridview
            stubGrid.setVisibility(View.VISIBLE);
        }
        setAdapters();
    }

    private void setAdapters() {
        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            listViewAdapter = new ListViewAdapter(this, R.layout.list_item, productList);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, productList);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    public List<Product> getProductList() {
        //pseudo code to get product, replace your code to get real product here
        productList = new ArrayList<>();
        productList.add(new Product(R.drawable.distress, "Distress Signal", "     "));
        productList.add(new Product(R.drawable.emergency, "Emergency Signal", "     "));
        productList.add(new Product(R.drawable.fine, "I'm Okay", "  "));
        productList.add(new Product(R.drawable.home, "Go Home", " "));
        productList.add(new Product(R.drawable.track, "Track Location", "       "));
        productList.add(new Product(R.drawable.contacts, "Set Contacts", "      "));
        productList.add(new Product(R.drawable.setlocation, "Set Home Location", " "));
        productList.add(new Product(R.drawable.instructions, "Instructions", " "));
        productList.add(new Product(R.drawable.info, "About App", " "));


        return productList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_1:
                if (VIEW_MODE_LISTVIEW == currentViewMode) {
                    currentViewMode = VIEW_MODE_GRIDVIEW;
                } else {
                    currentViewMode = VIEW_MODE_LISTVIEW;
                }
                //Switch view
                switchView();
                //Save view mode in share reference
                SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("currentViewMode", currentViewMode);
                editor.commit();

                break;
        }
        return true;
    }

    protected void DistressSMS() {
        int sizer = removecontact.productList.size();
        for (int i = 0; i < sizer; i++) {
            String number = removecontact.productList.get(i).getDescription();
            String messageToSend = "Help! I am in distress and require immediate attention and assistance. My location is longitude: " + longitude + " latitude: " + latitude;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null, null);
            Log.i("Sent signal", "");
        }
    }

    protected void EmergencySMS() {
        int sizer = removeemergency.emergencyList.size();
        for (int i = 0; i < sizer; i++) {
            String number = removeemergency.emergencyList.get(i).getDescription();
            String messageToSend = "I am in an emergency situation! Please call 911 for me or come to my assistance! Longitude:" + longitude + " and latitude:" + latitude;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null, null);
            Log.i("Sent signal", "");
        }

    }

    protected void OkaySMS() {
        int sizer = removecontact.productList.size();
        int sizer2 = removeemergency.emergencyList.size();
        for (int i = 0; i < sizer; i++) {
            String number = removecontact.productList.get(i).getDescription();
            String messageToSend = "False alarm! I'm perfectly fine and well :)";
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null, null);
            Log.i("Sent signal", "");
        }

        for (int i = 0; i < sizer; i++) {
            String number = removeemergency.emergencyList.get(i).getDescription();
            String messageToSend = "False alarm! I'm perfectly fine and well :)";
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null, null);
            Log.i("Sent signal", "");
        }
    }

    protected void TrackLocation() {
        int sizer = removeemergency.emergencyList.size();
        for (int i = 0; i < sizer; i++) {
            String number = removecontact.productList.get(i).getDescription();
            String messageToSend = "Here is my current updated location, longitude: "+longitude + " latitude: "+latitude;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null, null);
            Log.i("Sent signal", "");
        }

    }

}


