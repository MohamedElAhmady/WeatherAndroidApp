package a7mady.com.weatherandroidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by A7mady on 6/27/2015.
 */
public class CityListAdapter extends ArrayAdapter<City>{
    ArrayList<City> citiesList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
    public CityListAdapter(Context context, int resource, ArrayList<City> objects) {
        super(context, resource, objects);

        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        citiesList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.tvName = (TextView) v.findViewById(R.id.city_txtv);
            holder.tvTemp = (TextView) v.findViewById(R.id.temp_txtv);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.tvName.setText(citiesList.get(position).getCityName());
        holder.tvTemp.setText(Float.toString(citiesList.get(position).getTemperature()));
        return v;
    }
    //use view holder to enhance rendering list view in memory
    static class ViewHolder {
        public TextView tvName;
        public TextView tvTemp;
    }
}
