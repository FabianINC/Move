package Login_RegisterActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    /* VARIABLES GLOBALES */
    EditText txt_name,txt_email,txt_password,txt_confirmPassword;
    Button btn_registration;
    String name, email, password, passwordConfirmation;

    //private static final String URL1 = "http://152.231.173.118/usuarios/save.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        /* SE IDENTIFICA CADA VARIABLE GLOBAL */
        txt_name = findViewById(R.id.txt_name);
        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        txt_confirmPassword = findViewById(R.id.txt_passwordConfirmation);
        btn_registration = findViewById(R.id.btn_registerAccount);

        /* EVENTO QUE SE EJECUTA CUANDO SE DA CLICK EN btnRegister */
        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* SE CONVIERTE A STRING CADA VALOR INGRESADO */
                name = txt_name.getText().toString();
                email = txt_email.getText().toString();
                password = txt_password.getText().toString();
                passwordConfirmation = txt_confirmPassword.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.100.5/login_register/register.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.equals("success")){ /* SI DEVUELVE 'success' ES PORQUE SE PUDO REGISTRAR EN LA BASE DE DATOS */
                                    showToastGood("Registro exitoso");

                                    /* LLAMADO A LA PANTALLA DE LOGIN */
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                }else{
                                    /* DEVUELVE EL ERROR */
                                    showToastWrong(response);
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        /* ACÁ DEBERÍA IR OTRO TOAST PARA SABER SI ES OTRO TIPO DE ERROR ¿? */
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();

                        /* SE INGRESAN LOS DATOS DE CADA PARAMETRO DEL QUERY PHP */
                        paramV.put("name", name);
                        paramV.put("email", email);
                        paramV.put("password", password);
                        return paramV;
                    }
                };
                /* SE AÑADE EL QUERY DE REGISTRO */
                queue.add(stringRequest);

            }
        });


    }


    /* MÉTODO PARA PASAR A LA PANTALLA DE LOGIN */
    public void AlreadyRegistered(View newUserClicked){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    /* METODO PARA MOSTRAR UN "TOAST" QUE FUE EFECTIVO */
    public void showToastGood(String toastMessage){
        LayoutInflater layoutInflater = getLayoutInflater();
        View toastRegistration = layoutInflater.inflate(R.layout.toast_check,(ViewGroup) findViewById(R.id.check_toast));

        TextView txtMessage = toastRegistration.findViewById(R.id.txt_toast);

        txtMessage.setText(toastMessage); /* MENSAJE DEL TOAST */

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0 , 200);

        toast.setDuration(Toast.LENGTH_SHORT); /* DURACIÓN DEL TOAST */
        toast.setView(toastRegistration);
        toast.show(); /* SE MUESTRA EL TOAST */
    }

    /* METODO PARA MOSTRAR UN "TOAST" QUE FUE ERRONEO */
    public void showToastWrong(String toastMessage){
        LayoutInflater layoutInflater = getLayoutInflater();
        View toastRegistration = layoutInflater.inflate(R.layout.toast_wrong,(ViewGroup) findViewById(R.id.wrong_toast));

        TextView txtMessage = toastRegistration.findViewById(R.id.toast_wrong);

        txtMessage.setText(toastMessage); /* MENSAJE DEL TOAST */

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0 , 200);

        toast.setDuration(Toast.LENGTH_SHORT); /* DURACIÓN DEL TOAST */
        toast.setView(toastRegistration);
        toast.show(); /* SE MUESTRA EL TOAST */
    }


}