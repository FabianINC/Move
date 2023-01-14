package MainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.archivo.move.R;
import com.archivo.move.databinding.SideBarBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Login_RegisterActivity.LoginActivity;
import Login_RegisterActivity.RegisterActivity;


public class Main extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private SideBarBinding binding;
    SharedPreferences sharedPreferences;

    ArrayList<Box> boxes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sistema para cerrar sesion
        sharedPreferences = getSharedPreferences("MyAppName" , MODE_PRIVATE);

        if(sharedPreferences.getString("logged", "false").equals("false")){
            startActivity(new Intent(Main.this, LoginActivity.class));
            finish();
        }

        //Desaraparece la decoracion superior e inferior que vienen por defecto
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);





        binding = SideBarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //Inicializar el "RecyclerView"
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);

        //Metodo para crear los elementos guardados
        setUpBoxModels();

        //Inicializar el adaptador para el "RecyclerView"

        B_RecyclerViewAdapter adapter = new B_RecyclerViewAdapter(this, boxes );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Para que los colores del sideBar se pueda ver
        navigationView.setItemIconTintList(null);




    }


    public void logout(MenuItem menuItem){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="http://192.168.100.5/login_register/logout.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if(response.equals("success")) {
                            //En caso de que se haya cerrado sesion exitosamente los valores del SharedPreferences se ponene en blanco para no iniciar sesion de forma automatica
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("logged", "");
                            editor.putString("name", "");
                            editor.putString("email", "");
                            editor.putString("apiKey", "");
                            editor.apply();
                            startActivity(new Intent(Main.this, LoginActivity.class));
                            finish();
                        }else{

                         showToastWrong(response);

                        }



                    }
                }, new Response.ErrorListener() {
            @Override

            /* SE MUESTRA UN TOAST CON EL ERROR */
            public void onErrorResponse(VolleyError error) {
                showToastWrong(error.getMessage());
            }
        }){
            /**/
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();

                paramV.put("email", sharedPreferences.getString("email", ""));
                paramV.put("apiKey",sharedPreferences.getString("apiKey", ""));
                return paramV;
            }
        };
        queue.add(stringRequest);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.side_bar, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setUpBoxModels(){


        for(int i = 0; i < 10; i++){


            boxes.add(new Box("Alajuela, Costa Rica", "₡25,000",R.drawable.img_prueba2 ));


        }
            boxes.add(new Box("San Jose, Costa Rica", "₡25,000",R.drawable.img_prueba2 ));


    }

    // METODO PARA MOSTRAR UN "TOAST" QUE FUE ERRONEO
    public void showToastWrong(String toastMessage){
        LayoutInflater layoutInflater = getLayoutInflater();
        View toastRegistration = layoutInflater.inflate(R.layout.toast_wrong,(ViewGroup) findViewById(R.id.wrong_toast));

        TextView txtMessage = toastRegistration.findViewById(R.id.toast_wrong);

        txtMessage.setText(toastMessage); // MENSAJE DEL TOAST

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0 , 200);

        toast.setDuration(Toast.LENGTH_SHORT); // DURACIÓN DEL TOAST
        toast.setView(toastRegistration);
        toast.show(); // SE MUESTRA EL TOAST
    }



}