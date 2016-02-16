package zozo.group;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by zozo on 12.2.2016.
 */

public class thirdActivity extends AppCompatActivity implements View.OnClickListener{
    SharedPreferences prefGet;
    String store;
    ListView lv;
    Button clear;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        prefGet = getSharedPreferences("data2", Activity.MODE_PRIVATE);
        store=prefGet.getString("database", null);
        String[] list=store.split(" ");
        int i=0;
        for(String a:list){
            i++;
        }
        String arrayDate[]=new String[i];
        String arrayDistance[]=new String[i];
        String arrayVolume[]=new String[i];
        toArray(store ,arrayDate,arrayDistance ,arrayVolume );
        lv=(ListView)findViewById(R.id.listView);
        Adapter adapter= new Adapter(this,arrayDate,arrayDistance,arrayVolume);
        lv.setAdapter(adapter);
        clear=(Button)findViewById(R.id.clear);
        clear.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        SharedPreferences prefPut = getSharedPreferences("data2",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.clear();
        store="";
        prefEditor.putString("database",store);
        prefEditor.commit();
        prefEditor.putFloat("highest",0);
        prefEditor.commit();
        String arrayDate[]={};
        String arrayDistance[]={};
        String arrayVolume[]={};
        Adapter adapter= new Adapter(this,arrayDate,arrayDistance,arrayVolume);
        lv.setAdapter(adapter);

    }
    public void toArray(String store,String arrayDate[],String arrayDistance[],String arrayVolume[]){
        ArrayList<String> date= new ArrayList<>();
        ArrayList<String> distance= new ArrayList<>();
        ArrayList<String> volume= new ArrayList<>();


        String[] list=store.split(" ");
        for(int i=0; i<list.length-2; i+=3){
            date.add(list[i]);
            distance.add(list[i+1]);
            volume.add(list[i+2]);
        }

        int i=0;
        for(String var: date){
            arrayDate[i]=var;
            i++;
        }
        int x=0;
        for(String var: distance){
            arrayDistance[x]=var;
            x++;
        }
        int y=0;
        for(String var: volume){
            arrayVolume[y]=var;
            y++;
        }
    }
}
