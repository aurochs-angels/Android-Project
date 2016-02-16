package zozo.group;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by zozo on 12.2.2016.
 */
public class secondActivity extends AppCompatActivity implements View.OnClickListener{
    EditText distance;
    EditText volume;
    Float highest;
    TextView warning2;
    TextView warning3;
    Button add;
    Button back;
    String dateText;
    SharedPreferences prefPut;
    SharedPreferences.Editor prefEditor;
    String send;
    SharedPreferences prefGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        Date date = new Date();
        dateText = dateFormat.format(date);
        distance = (EditText) findViewById(R.id.distance);
        volume = (EditText) findViewById(R.id.volume);
        warning2=(TextView) findViewById(R.id.warning2);
        warning3=(TextView) findViewById(R.id.warning3);
        add=(Button)findViewById(R.id.add);
        add.setOnClickListener(this);
        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(this);
        prefPut = getSharedPreferences("data2",
                Activity.MODE_PRIVATE);
        prefEditor = prefPut.edit();
        send="";
        prefGet = getSharedPreferences("data2", Activity.MODE_PRIVATE);
        highest=prefGet.getFloat("highest", 0);
        System.out.println(highest);
        //set= new LinkedHashSet<String>();
        //prefGet = getSharedPreferences("data2", Activity.MODE_PRIVATE);
       // store=prefGet.getStringSet(dateText, null);
/*
        if(store==null){
            set= new LinkedHashSet<String>();
        }else{
            set=store;
        } */





    }
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.add:

                String distanceText = distance.getText().toString();
                String volumeText = volume.getText().toString();

                if(!checkValidity(distanceText)){
                    warning2.setText("Your distance is not valid");
                }
                if(!checkValidity(volumeText)){
                    warning3.setText("Your volume is not valid");
                }
                if(checkValidity(distanceText)&& checkValidity(volumeText)){
                    warning2.setText("");
                    warning3.setText("");
                    Float input=Float.parseFloat(distanceText);
                    send= dateText+" "+distanceText+" "+volumeText+" ";
                    if(input>=highest){
                        System.out.println(highest);
                        System.out.println(input);
                        highest=input;
                        System.out.println(prefGet.getFloat("nana", 0));
                        prefEditor.putFloat("highest", highest);
                        prefEditor.commit();
                        System.out.println(prefGet.getFloat("nana", 0));
                        prefEditor.putString("key",send);
                        prefEditor.commit();
                    }else{
                        warning2.setText("Your new odometer value must be higher than the last one");
                    }
                    finish();
                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    public boolean isNumber( String input ) {
        try
        {
            Double.parseDouble( input );
            return true;
        }
        catch( Exception e)
        {
            return false;
        }

    }

    public boolean checkValidity(String input){
        if (!isNumber(input)){
            return false;
        }else{
            if(Double.parseDouble( input)<0){
                return false;
            }
        }
        return true;
    }




}
