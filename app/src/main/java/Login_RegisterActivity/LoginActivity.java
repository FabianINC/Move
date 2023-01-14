package Login_RegisterActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.archivo.move.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import MainActivity.Main;

public class LoginActivity extends AppCompatActivity {

    // VARIABLES GLOBALES
    EditText txt_email,txt_password;
    String  email , password, name , apiKey;
    Button btnLogin;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //SE IDENTIFICA CADA VARIABLE GLOBAL
        txt_email = findViewById(R.id.txtEmail);
        txt_password = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        sharedPreferences = getSharedPreferences("MyAppName" , MODE_PRIVATE);

        // SE MANTIENE LA SESION INICIADA UNA VEZ QUE HAYA INICIADO SESION
        if(sharedPreferences.getString("logged", "false").equals("true")){
            startActivity(new Intent(LoginActivity.this, Main.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // SE CONVIERTE A STRING LOS VALORES INGRESADOS
                email = txt_email.getText().toString();
                password = txt_password.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.100.5/login_register/login.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {

                                    //Se utiliza un jsonObject ya que el php esta en formato JSON
                                    JSONObject jsonObject = new JSONObject(response);
                                    //Esto retorna el estado del php, si el inicio de sesion fue exitoso o no
                                    String status = jsonObject.getString("status");
                                    //Este retonrna el mensaje, utilizado sobretodo en caso de errores
                                    String message = jsonObject.getString("message");


                                    if(status.equals("success")){

                                        //En caso de que se inicie sesion exitosamente se recuperan los datos del php
                                       name = jsonObject.getString("name");
                                       email = jsonObject.getString("email");
                                       apiKey = jsonObject.getString("apiKey");

                                       /*Estos datos son utilizados en SharedPreferences lo que despues ayudara a
                                         mantener la sesion iniciada despues de la primera vez que inicio sesion*/
                                       SharedPreferences.Editor editor = sharedPreferences.edit();
                                       editor.putString("logged" , "true");
                                       editor.putString("name" , name);
                                       editor.putString("email" , email);
                                       editor.putString("apiKey" , apiKey);
                                       editor.apply();
                                       showSuccesfulToast("Login successful");
                                       startActivity(new Intent(LoginActivity.this, Main.class));
                                       finish();

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override

                    /* SE MUESTRA UN TOAST CON EL ERROR */
                    public void onErrorResponse(VolleyError error) {
                        showUnsuccessfulToast(error.getMessage());
                    }
                }){
                    /**/
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();

                        paramV.put("email", email);
                        paramV.put("password", password);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }

    // MÉTODO PARA MOSTRAR LA PANTALLA DE REGISTRO
    public void newUserScreen(View view){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    // MÉTODO PARA USAR LA APP COMO 'Invitado'
    public void guestLogin(View view){
        startActivity(new Intent(LoginActivity.this, Main.class));
    }

    // MÉTODO PARA REESTABLECER LA CONTRASEÑA
    public void resetPassword(View view){
        startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
    }

    // METODO PARA MOSTRAR UN "TOAST" QUE FUE EFECTIVO
    public void showSuccesfulToast(String toastMessage){
        LayoutInflater layoutInflater = getLayoutInflater();
        View toastRegistration = layoutInflater.inflate(R.layout.toast_check,(ViewGroup) findViewById(R.id.check_toast));

        TextView txtMessage = toastRegistration.findViewById(R.id.txt_toast);

        txtMessage.setText(toastMessage); // MENSAJE DEL TOAST

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0 , 200);

        toast.setDuration(Toast.LENGTH_SHORT); // DURACIÓN DEL TOAST
        toast.setView(toastRegistration);
        toast.show(); // SE MUESTRA EL TOAST
    }

    // METODO PARA MOSTRAR UN TOAST QUE FUE ERRONEO
    public void showUnsuccessfulToast(String toastMessage){
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