package zozo.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by zozo on 13.2.2016.
 */
public class Adapter extends ArrayAdapter<String> {
    String[] date={};
    String[] distance={};
    String[] volume={};
    Context c;
    LayoutInflater inflater;

    public Adapter(Context context, String[] date, String[] distance, String[] volume){
        super(context,R.layout.model,date);
        this.c= context;
        this.date=date;
        this.distance=distance;
        this.volume=volume;
    }
    public class ViewHolder{
        TextView date;
        TextView distance;
        TextView volume;

    }
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.model, null);

        }
        final ViewHolder holder = new ViewHolder();
        holder.date=(TextView)convertView.findViewById(R.id.date);
        holder.distance=(TextView)convertView.findViewById(R.id.distance);
        holder.volume=(TextView)convertView.findViewById(R.id.volume);
        holder.date.setText(date[position]);
        holder.distance.setText(distance[position]);
        holder.volume.setText(volume[position]);
        return convertView;
    }
}
