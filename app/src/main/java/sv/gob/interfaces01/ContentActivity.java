package sv.gob.interfaces01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ContentActivity extends AppCompatActivity {
    private ProgressBar loadingPB;
    private ArrayList<Dispositivos> listadispositivos;
    private AdaptadorDispositivos adapter;
    private ArrayList<String> names;

    private ListView listView;

    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X","Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X","Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X","Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        loadingPB = findViewById(R.id.progressBar);

        //instanciar el action Bar https://developer.android.com/training/appbar/setting-up?hl=es-419
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.mobile_list);

        getInfo();

        //Establecer el adaptador para el list view
        /*ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, mobileArray);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);*/


        //Establecemos el evento del elemento listener
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),"El elemento seleccionado es :"+selectedItem, Toast.LENGTH_SHORT).show();

            }
        });*/

    }

    //Obtenemos los datos del API
    private void getInfo(){
        //Set api url
        String url="https://fakestoreapi.com/products";

        closeKeyboard();
        //Show loader
        loadingPB.setVisibility(View.VISIBLE);

        StringRequest postRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        names = new ArrayList<String>();
                        listadispositivos=new ArrayList<Dispositivos>();

                        try {

                            loadingPB.setVisibility(View.GONE);
                            System.out.println(response);
                            //get the returned JsonArray
                            JSONArray jsonArray = new JSONArray(response);

                            //Iterate over the JsonArray
                            for (int i=0; i<jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                System.out.println(jsonObject.get("id"));
                                System.out.println(jsonObject.getString("title"));
                                listadispositivos.add( new Dispositivos(jsonObject.getInt("id"),jsonObject.getString("title"),jsonObject.getDouble("price"),jsonObject.getString("description"),jsonObject.getString("category"),jsonObject.getString("image")));
                            }

                            //Intantiate the adapter
                            //adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, names);
                            adapter = new AdaptadorDispositivos(ContentActivity.this);

                            //Set the adapter to listView
                             listView.setAdapter(adapter);


                        } catch (JSONException e) {
                            System.out.println("Errorrr *****");
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(ContentActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingPB.setVisibility(View.GONE);

                /*AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setMessage(error.getMessage()).setTitle("Ocurrio un error!!");

                builder.setNegativeButton("Entendido", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    dialog.cancel();
                });

                AlertDialog dialog = builder.create();
                dialog.show();*/

                Toast.makeText(ContentActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }){
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                //params.put("cadena", textView.getText().toString());

                // at last we are
                // returning our params.
                return params;
            }
        };

        //Send the request
        Volley.newRequestQueue(this).add(postRequest);
    }

    //Cerrar teclado
    private void closeKeyboard()
    {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = this.getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }
    class AdaptadorDispositivos extends ArrayAdapter<Dispositivos> {

        AppCompatActivity appCompatActivity;

        AdaptadorDispositivos(AppCompatActivity context) {
            super(context, R.layout.dispositivo, listadispositivos);
            appCompatActivity = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.dispositivo, null);

            TextView textView1 = item.findViewById(R.id.lbl_nombre);
            textView1.setText(listadispositivos.get(position).getTitle());

            TextView textView2 = item.findViewById(R.id.lbl_ip);
            textView2.setText(listadispositivos.get(position).getPrice().toString());

            TextView textView3 = item.findViewById(R.id.lbl_estado);
            textView3.setText(listadispositivos.get(position).getCategory());

            TextView textView4 = item.findViewById(R.id.lbl_tipo);
            textView4.setText(listadispositivos.get(position).getDescription());

            /*TextView textView5 = item.findViewById(R.id.lbl_descripcion);
            textView5.setText(listadispositivos.get(position).getImage());*/


            return(item);
        }
    }
}