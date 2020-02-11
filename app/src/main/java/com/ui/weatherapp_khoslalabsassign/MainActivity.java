package com.ui.weatherapp_khoslalabsassign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    String title, image, category;
    int index_no;
    TextView weatherTemp,cityTextView;
    String CityName="";


    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherTemp = (TextView)findViewById(R.id.weatherTemp);
        cityTextView =(TextView)findViewById(R.id.citytextview);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.versionArray[i],
                    MyData.id_[i],
                    MyData.drawableArray[i],
                    MyData.weathercondition[i]
            ));
        }

        adapter = new CustomeAdapter(data);
        recyclerView.setAdapter(adapter);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1000);
        }
        else
        {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            String city = GetCityName(location.getLatitude(),location.getLongitude());
            Log.d("citymain",""+city);

             MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
            myAsyncTasks.execute();
        }




    }


    public class MyAsyncTasks extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            // implement API in background and store the response in current variable
            String current = "";
            try {
                String apiUrl = "http://api.weatherstack.com/current?access_key=437f25dedb4e90a89fedf5b95be161ff&query="+CityName;
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(apiUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isw = new InputStreamReader(in);
                    int data = isw.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isw.read();
                        System.out.print(current);
                    }
                    Log.d("datalength",""+current.length());
                    // return the data to onPostExecute method
                    return current;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            return current;
        }
        @Override
        protected void onPostExecute(String s) {
            Log.d("datacurrent", s);
            // dismiss the progress dialog after receiving data from API
            progressDialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(s);
                String weather=jsonObject.getString("current");
                Log.i("cloudres",""+weather);

               JSONObject weather_data = jsonObject.getJSONObject("current");

                Log.i("weatherdata",""+weather_data.getString("temperature"));
                cityTextView.setText(CityName);
                weatherTemp.setText(weather_data.getString("temperature") + "°");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    public String GetCityName(double lat,double lon){

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addressList;

        try {
            addressList= geocoder.getFromLocation(lat,lon,10);
            if(addressList.size()>0)
            {
                for(Address adr : addressList){
                    if(adr.getLocality()!=null && adr.getLocality().length()>0)
                    {
                        CityName = adr.getLocality();
                    }
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return  CityName;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 1000: if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {

                try {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    String city = GetCityName(location.getLatitude(),location.getLongitude());
                    Log.d("citypermission",""+city);
                     MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                    myAsyncTasks.execute();

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }else
            {
                Toast.makeText(this,"Permission not Granted",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
