package zozo.group;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn2;
    Button btn3;
    Intent intent2;
    Intent intent3;
    TextView highestText;
    TextView averageText;
    TextView lowestText;
    TextView latestText;
    String receive;
    String store;
    SharedPreferences prefPut;
    SharedPreferences.Editor prefEditor;
    SharedPreferences prefGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefGet = getSharedPreferences("data2", Activity.MODE_PRIVATE);
        store=prefGet.getString("database", "");
        receive="";

        prefPut = getSharedPreferences("data2",
                Activity.MODE_PRIVATE);
        prefEditor = prefPut.edit();
        btn2=(Button)findViewById(R.id.act2);
        btn3=(Button)findViewById(R.id.act3);
        intent2 = new Intent(this, secondActivity.class);
        intent3 = new Intent(this, thirdActivity.class);
        highestText=(TextView) findViewById(R.id.highest);
        averageText=(TextView) findViewById(R.id.average);
        lowestText=(TextView) findViewById(R.id.lowest);
        latestText=(TextView) findViewById(R.id.latest);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        calculate();




    }
    private void setOnClickListener(MainActivity mainActivity) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        receptFunc();
        calculate();

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.act2:
                startActivityForResult(intent2, 2);
                break;
            case R.id.act3:
                startActivityForResult(intent3, 3);
                break;
        }
    }

    public void receptFunc(){
        receive=prefGet.getString("key","");
        if(!receive.isEmpty()){
            store+=receive;
            prefEditor.clear();
            prefEditor.putString("database",store);
            prefEditor.commit();

        }

    }
    public void calculate(){
        store=prefGet.getString("database","");
            int size=0;
            String[] list=store.split(" ");
            for(String a:list){
                size++;
            }
        if(size>=6){
            ArrayList<Double> result=new ArrayList<>();
            ArrayList<Double> distance=new ArrayList<>();
            ArrayList<Double> volume=new ArrayList<>();
            ArrayList<String> date=new ArrayList<>();


            for(int i=0; i<list.length-2; i+=3){
                date.add(list[i]);
                distance.add(Double.parseDouble(list[i+1]));
                volume.add(Double.parseDouble(list[i+2]));
            }

            for(int i=0; i<distance.size()-1;i++){
                double value=volume.get(i)/(distance.get(i+1)-distance.get(i));
                result.add(value);
            }
            DecimalFormat df = new DecimalFormat("#.##");
            String showLatest=String.valueOf(df.format(result.get(result.size()-1)));
            Collections.sort(result);
            double sum=0;
            for(double e:result){
                sum+=e;
            }
            double average=sum/result.size();
            String showHighest=String.valueOf(df.format(result.get(result.size()-1)));
            String showAverage=String.valueOf(df.format(average));
            String Lowest=String.valueOf(df.format(result.get(0)));
            highestText.setText("Highest: "+showHighest);
            averageText.setText("Average: "+showAverage);
            lowestText.setText("Lowest: "+Lowest);
            latestText.setText("Latest: "+showLatest);

        }else{
            highestText.setText("Highest: 0");
            averageText.setText("Average: 0");
            lowestText.setText("Lowest: 0");
            latestText.setText("Latest: 0");
        }

    }
}
