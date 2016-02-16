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
    SharedPreferences prefPut1;
    SharedPreferences.Editor prefEditor1;
    SharedPreferences prefGet1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        // retrive the current time
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        Date date = new Date();
        dateText = dateFormat.format(date);
        // 2 edittext objects and 2 textview objects
        distance = (EditText) findViewById(R.id.distance);
        volume = (EditText) findViewById(R.id.volume);
        warning2=(TextView) findViewById(R.id.warning2);
        warning3=(TextView) findViewById(R.id.warning3);
        // 2 butttons
        add=(Button)findViewById(R.id.add);
        add.setOnClickListener(this);
        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(this);
        // create 2 shared preferences
        prefPut = getSharedPreferences("data2",
                Activity.MODE_PRIVATE);
        prefEditor = prefPut.edit();
        send="";
        prefGet = getSharedPreferences("data2", Activity.MODE_PRIVATE);
        prefPut1 = getSharedPreferences("data3",
                Activity.MODE_PRIVATE);
        prefEditor1 = prefPut1.edit();
        // get date
        prefGet1 = getSharedPreferences("data3", Activity.MODE_PRIVATE);
        // assign the latest distance input to variable highest
        highest=prefGet1.getFloat("limit",0);


    }
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.add:
            // I take the distance and volume input from the user
                String distanceText = distance.getText().toString();
                String volumeText = volume.getText().toString();
            // check if the inputs are number or not
                if(!checkValidity(distanceText)){
                    warning2.setText("Your distance is not valid");
                }
                if(!checkValidity(volumeText)){
                    warning3.setText("Your volume is not valid");
                }//If they are number then I calculate
                if(checkValidity(distanceText)&& checkValidity(volumeText)){
                    warning2.setText("");
                    warning3.setText("");
                    //get the distance value
                    Float inputDistance=Float.parseFloat(distanceText);
                    // check if the current distance input is greater than the previous one
                    if(inputDistance>=highest){
                        // add input including date, distance , volume to 1 string
                        send= dateText+" " + distanceText+" "+volumeText+" ";
                        // save send message to key
                        prefEditor.putString("key", send);
                        prefEditor.commit();
                        // save current limit of distance value
                        prefEditor1.putFloat("limit",inputDistance);
                        prefEditor1.commit();
                        finish();

                    }else{
                        warning2.setText("Your new odometer value must be higher than the last one: "+String.valueOf(highest));
                    }

                }
                break;
            case R.id.back:
                finish();
                break;
        }
    }
    // check if the input is number
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
    // check if the input is number and positive
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
