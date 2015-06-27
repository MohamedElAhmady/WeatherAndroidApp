package a7mady.com.weatherandroidapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity {
    ArrayList<City> citiesList;
    CityListAdapter adapter;
    static final String _URI_ = "http://api.openweathermap.org/data/2.5/find?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        citiesList = new ArrayList<City>();


        String[] urls = {_URI_ + "Ottawa", _URI_ + "Toronto", _URI_ + "Montreal", _URI_ + "Calgary", _URI_ + "Vancouver", _URI_ + "Halifax"};

        for (int i = 0; i < urls.length; i++) {
            new JSONAsyncTask().execute(urls[i]);
        }
        ListView listView = (ListView) findViewById(R.id.list);
        adapter = new CityListAdapter(getApplicationContext(),
                R.layout.list_row, citiesList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        citiesList.get(position).getCityName()+"\n"+citiesList.get(position).getTemperature(), Toast.LENGTH_LONG)
                        .show();
            }
        });

    }


    /// async task to connect to the api webservice and get the temperature of every city
    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            //show loading dialog
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {


                // connect to the server webService (post connection)
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    Log.i("data", data);
                    //convert the json data to list of city bean to set the data of the listview by callin the adapter
                    JSONObject jsono = new JSONObject(data);
                    JSONArray array = jsono.getJSONArray("list");
                    JSONObject mainObj = array.getJSONObject(0);
                    JSONObject main = mainObj.getJSONObject("main");
                    City city = new City();
                    city.setCityName(mainObj.getString("name"));
                    city.setTemperature((float) main.getDouble("temp"));
                    citiesList.add(city);
                }
                return true;

            } catch (JSONException e2) {
                e2.printStackTrace();
            } catch (ClientProtocolException e2) {
                e2.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }

            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            adapter.notifyDataSetChanged();
            if (result == false)
                //show toast in case of failure to connect the server
                Toast.makeText(getApplicationContext(),
                        "Unable to fetch data from server", Toast.LENGTH_LONG)
                        .show();

        }
    }
}
