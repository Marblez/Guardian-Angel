package bigmarbz.guardianangel;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class removeemergency extends AppCompatActivity {

    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;
    Intent intenthero;
    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listView;
    private GridView gridView;
    private ListViewAdapter listViewAdapter;
    private GridViewAdapter gridViewAdapter;
    public static List<Product> emergencyList;
    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //Do any thing when user click to item
            Toast.makeText(getApplicationContext(), emergencyList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            if (true) {
                for(int j = 0; j < emergencyList.size(); j++)
                {
                    Product property = emergencyList.get(j);

                    if(emergencyList.get(position).equals(property)){
                        //found, delete.
                        emergencyList.remove(j);
                        break;
                    }

                }
            }

            }

    };
    private int currentViewMode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (emergencyList == null){
            emergencyList = new ArrayList<>();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_removeemergency);

        stubList = (ViewStub) findViewById(R.id.stub_list);
        stubGrid = (ViewStub) findViewById(R.id.stub_grid);

        //Inflate ViewStub before get view

        stubList.inflate();
        stubGrid.inflate();

        listView = (ListView) findViewById(R.id.mylistview);
        gridView = (GridView) findViewById(R.id.mygridview);

        //get list of product
        getEmergencyList();

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
            listViewAdapter = new ListViewAdapter(this, R.layout.list_item, emergencyList);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, emergencyList);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    public static List<Product> getEmergencyList() {

        return emergencyList;
    }


}
