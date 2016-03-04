package wiredelta.com.gson;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<CompanyModel> companyList = null;
    private ProgressDialog pd = null;
    private final String url = "https://api.myjson.com/bins/2ggcs";
    private final String companyID = "companyID";
    private final String comapnyName = "comapnyName";
    private final String companyOwner = "companyOwner";
    private final String companyDescription = "companyDescription";
    private final String companyDepartments = "companyDepartments";
    private final String companyStartDate = "companyStartDate";
    private final SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
    private int tryCount = -1;

    private RecyclerView recyclerView = null;
    private CompanyAdapter adapter = null;
    private EditText searchText = null;
    private Button searchButton = null;
    private FloatingActionButton filterButton = null;

    private ArrayList<String> departmentList = null;
    private LayoutInflater inflater = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company);
        inflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        searchText = (EditText) findViewById(R.id.search_company);
        searchButton = (Button) findViewById(R.id.btm_search);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        filterButton = (FloatingActionButton)findViewById(R.id.fab);

        pd = new ProgressDialog(this);
        pd.setCancelable(false);

        // volley library use
        getData();

    }
    private void getData()
    {
        tryCount++;
        RequestQueue queue = Volley.newRequestQueue(this);
        pd.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("*******" + response.toString());
                companyList = new ArrayList<>();
                departmentList = new ArrayList<>();
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        CompanyModel model = new CompanyModel();
                        model.setCompanyID(obj.getString(companyID));
                        model.setCompanyName(obj.getString(comapnyName));
                        model.setCompanyOwner(obj.getString(companyOwner));
                        model.setCompanyDepartments(obj.getString(companyDepartments));
                        model.setCompanyDescription(obj.getString(companyDescription));
                        model.setCompanyStartDate(sdf.parse(obj.getString(companyStartDate)));
                        if(!departmentList.contains(obj.getString(companyDepartments)) && obj.getString(companyDepartments)!="" && obj.getString(companyDepartments)!=null){
                            departmentList.add(obj.getString(companyDepartments));
                        }
                        companyList.add(model);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("***** array size" + companyList.size());
                departmentList.add("All");
                setAdapter(companyList);
                init();
                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("*** got some erroe" + error.toString());
                pd.dismiss();
                if(tryCount <= 2) {
                    getData();
                }else
                {
                    Toast.makeText(MainActivity.this, "please check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
        queue.add(jsonArrayRequest);

    }
    private void init(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tempText = searchText.getText().toString();
                if (tempText.length() != 0) {
                    ArrayList<CompanyModel> tempList = new ArrayList<CompanyModel>();
                    for (int i = 0; i < companyList.size(); i++) {
                        if (companyList.get(i).getCompanyName().toLowerCase().contains(tempText.toLowerCase())) {
                            tempList.add(companyList.get(i));
                            setAdapter(tempList);
                        }
                    }
                } else {
                    setAdapter(companyList);
                }
            }
        });
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterAlert();
            }
        });

    }
    private void setAdapter(ArrayList<CompanyModel> companyList1){
        recyclerView.setAdapter(null); adapter = null;
        adapter = new CompanyAdapter(MainActivity.this, companyList1);
        recyclerView.setAdapter(adapter);
    }
    private void filterByCategory(String cat){
        if(cat.equalsIgnoreCase("All")){
            setAdapter(companyList);
        }
        ArrayList<CompanyModel> tempArray = new ArrayList<>();
        for(int i=0;i<companyList.size();i++){
            if(cat.equals(companyList.get(i).getCompanyDepartments())){
                tempArray.add(companyList.get(i));
            }
        }
        setAdapter(tempArray);
    }
    private void showFilterAlert(){
        View alertView = inflater.inflate(R.layout.filter_alert,null);
        final ListView departmentListView = (ListView)alertView.findViewById(R.id.department_list);
        departmentListView.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, departmentList));
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("Select Department");
        alertDialogBuilder
                .setCancelable(false)
                .setView(alertView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        departmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                filterByCategory(departmentList.get(position));
                alertDialog.dismiss();
            }
        });


    }
}


