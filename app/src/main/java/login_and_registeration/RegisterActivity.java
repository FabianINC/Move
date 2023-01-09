package login_and_registeration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.archivo.move.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    // VARIABLES GLOBALES
    EditText txt_name,txt_email,txt_password,txt_confirmPassword;
    RequestQueue requestQueue;

    private static final String URL1 = "http://152.231.173.118/usuarios/save.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //REQUEST QUEUE
        requestQueue = Volley.newRequestQueue(this);

        txt_name = findViewById(R.id.txt_name);
        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        txt_confirmPassword = findViewById(R.id.txt_confirm);



    }

    public void getRegistered(View view){

        String name = txt_name.getText().toString().trim();
        String email = txt_email.getText().toString().trim();
        String password = txt_password.getText().toString().trim();
        String confirmPassword = txt_confirmPassword.getText().toString().trim();

        createUser(name,email,password);


    }

    private void createUser(final String name,final String email,final String password) {

          StringRequest stringRequest = new StringRequest(

                  Request.Method.POST,
                  URL1,
                  new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                        Toast.makeText(RegisterActivity.this, "REGISTRADO",Toast.LENGTH_SHORT).show();
                      }
                  },
                  new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {

                      }
                  }

          ){
              @Nullable
              @Override
              protected Map<String, String> getParams() throws AuthFailureError {

                  Map<String, String> params =  new HashMap<>();

                  params.put("name",name);
                  params.put("email",email);
                  params.put("password",password);


                  return params;
              }
          };

          requestQueue.add(stringRequest);

    }


    public void AlreadyRegistered(View newUserClicked){
        // SI ES PRESIONADO EL TEXTO DE 'txt_exist' SE MUESTRA LA PANTALLA DE LOGIN
        // LLAMADO A 'login'
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }


    //CUSTOM TOAST

    // METODO PARA MOSTRAR UN "TOAST" QUE FUE EFECTIVO
    public void showToastGood(String toastMessage){
        LayoutInflater layoutInflater = getLayoutInflater();
        View toastRegistration = layoutInflater.inflate(R.layout.toast_check,(ViewGroup) findViewById(R.id.check_toast));

        TextView txtMessage = toastRegistration.findViewById(R.id.txt_toast);

        txtMessage.setText(toastMessage);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0 , 200);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRegistration);
        toast.show();
    }

    // METODO PARA MOSTRAR UN "TOAST" QUE FUE ERRONEO
    public void showToastWrong(String toastMessage){
        LayoutInflater layoutInflater = getLayoutInflater();
        View toastRegistration = layoutInflater.inflate(R.layout.toast_wrong,(ViewGroup) findViewById(R.id.wrong_toast));

        TextView txtMessage = toastRegistration.findViewById(R.id.toast_wrong);

        txtMessage.setText(toastMessage);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0 , 200);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRegistration);
        toast.show();
    }


}