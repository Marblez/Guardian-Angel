package bigmarbz.guardianangel;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class aboutapp extends AppCompatActivity implements View.OnClickListener{
ImageButton github;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutapp);
        github = (ImageButton) findViewById(R.id.github);
        github.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.github:
                Intent webOpen = new Intent(android.content.Intent.ACTION_VIEW);
                webOpen.setData(Uri.parse(" https://github.com/Marblez/Guardian-Angel"));
                startActivity(webOpen);
                break;
        }
    }
}
