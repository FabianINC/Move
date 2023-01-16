package Login_RegisterActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import com.archivo.move.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    // VARIABLES GLOBALES
    EditText input_userName,input_email,input_password,input_confirmPassword;
    Button btn_register;
    /* String name, email, password, passwordConfirmation; */

    ProgressDialog registerProgress;


    FirebaseAuth registerAuth;
    FirebaseUser registerUser;

    //private static final String URL1 = "http://152.231.173.118/usuarios/save.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // SE IDENTIFICA CADA VARIABLE JAVA CON LA UI
        input_userName = findViewById(R.id.txt_name);
        input_email = findViewById(R.id.txt_email);
        input_password = findViewById(R.id.txt_password);
        input_confirmPassword = findViewById(R.id.txt_passwordConfirmation);
        btn_register = findViewById(R.id.btn_registerAccount);

        //SE CREA UN NUEVO PROGRESS DIALOG
        registerProgress = new ProgressDialog(RegisterActivity.this);

        registerAuth = FirebaseAuth.getInstance();
        registerUser = registerAuth.getCurrentUser();
/*
        // EVENTO QUE SE EJECUTA CUANDO SE DA CLICK EN btnRegister
        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // SE CONVIERTE A STRING CADA VALOR INGRESADO
                name = txt_name.getText().toString();
                email = txt_email.getText().toString();
                password = txt_password.getText().toString();
                passwordConfirmation = txt_confirmPassword.getText().toString();

                if(!password.equals(passwordConfirmation)){

                    showUnsuccessfulToast("La contraseña y su confirmación deben coincidir");

                }

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.100.5/login_register/register.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.equals("success")){ // SI DEVUELVE 'success' ES PORQUE SE PUDO REGISTRAR EN LA BASE DE DATOS
                                    showSuccessfulToast("Registro exitoso");

                                    // LLAMADO A LA PANTALLA DE LOGIN
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                }else{
                                    // DEVUELVE EL ERROR
                                    showUnsuccessfulToast(response);
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();

                        // SE INGRESAN LOS DATOS DE CADA PARAMETRO DEL QUERY PHP
                        paramV.put("name", name);
                        paramV.put("email", email);
                        paramV.put("password", password);
                        return paramV;
                    }
                };
                // SE AÑADE EL QUERY DE REGISTRO
                queue.add(stringRequest);

            }
        });
*/

    }

    /* VARIABLE PARA VERIFICAR QUE SEA UN CORREO VALIDO */
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    // MÉTODO PARA REALIZAR EL REGISTRO
    public void performRegister( View btnRegisterClicked ){

        // SE OBTIENEN TODOS LOS DATOS INGRESADOS
        final String userName = input_userName.getText().toString();
        final String userEmail = input_email.getText().toString();
        final String userPassword = input_password.getText().toString();
        final String userPasswordConfirmation = input_confirmPassword.getText().toString();

        // VERIFICAMOS QUE EL CORREO SEA REALMENTE UN FORMATO DE CORREO
        if( !userEmail.matches(emailPattern) ){
            input_email.setError("Ingrese un correo eléctronico válido");
            input_email.setText("");

        // VERIFICAMOS SI LA CONTRASEÑA ESTÁ VACÍA O ES MUY CORTA
        }else if( userPassword.isEmpty() || userPassword.length() < 6 ){
            input_password.setError("Ingrese una contraseña válido");
            input_password.setText("");
            input_confirmPassword.setText("");


        // VERIFICAMOS QUE AMBAS CONTRASEÑAN COINCIDAN
        }else if( !userPassword.equals(userPasswordConfirmation) ){
            input_confirmPassword.setError("Las contraseñas no coinciden");
            input_password.setText("");
            input_confirmPassword.setText("");


        // SI TODAS LAS VERIFICACIONES SON CORRECTAS
        }else{
            registerProgress.setMessage("Por favor espere...");
            registerProgress.setTitle("Registrando");
            registerProgress.setCanceledOnTouchOutside(false);
            registerProgress.show();

            // SE REALIZA LA PETICIÓN PARA REGISTRAR UN USUARIO
            registerAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if( task.isSuccessful() ){
                        registerProgress.dismiss();
                        showSuccessfulToast("¡Te has registrado exitosamente!");

                        Intent loginScreen = new Intent(RegisterActivity.this, LoginActivity.class);
                        loginScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(loginScreen);

                    }else{
                        registerProgress.dismiss();
                        showUnsuccessfulToast(task.getException().getMessage());
                    }
                }
            });
        }

    }

    // MÉTODO PARA PASAR A LA PANTALLA DE LOGIN
    public void alreadyRegistered(View newUserClicked){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    // METODO PARA MOSTRAR UN "TOAST" QUE FUE EFECTIVO
    public void showSuccessfulToast(String toastMessage){
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

    // METODO PARA MOSTRAR UN "TOAST" QUE FUE ERRONEO
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