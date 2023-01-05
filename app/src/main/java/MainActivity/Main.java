package MainActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.archivo.move.R;
import com.archivo.move.databinding.SideBarBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Main extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private SideBarBinding binding;

    ArrayList<Box> boxes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        boxes.add(new Box("Alajuela, Costa Rica", "₡25,000",R.drawable.img_prueba2 ));
        boxes.add(new Box("Alajuela, Costa Rica", "₡25,000",R.drawable.img_prueba2 ));
        boxes.add(new Box("Alajuela, Costa Rica", "₡25,000",R.drawable.img_prueba2 ));
        boxes.add(new Box("Alajuela, Costa Rica", "₡25,000",R.drawable.img_prueba2 ));
        boxes.add(new Box("Alajuela, Costa Rica", "₡25,000",R.drawable.img_prueba2 ));

    }



}