package login_and_registeration;

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

import com.archivo.move.R;

import MainActivity.Main;

public class LoginActivity extends AppCompatActivity {

    //VARIABLES GLOBALES
    EditText emailData,passwordData;
    Button btnLogin;
    TextView newAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // SE IDENTIFICA CADA VARIABLE GLOBAL
        emailData = findViewById(R.id.txtEmail);
        passwordData = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        newAccount = findViewById(R.id.txtNewAccount);

    }

    // MÉTODO PARA MOSTRAR LA PANTALLA DE REGISTRO
    public void newUserScreen(View view){
        Intent registerScreen = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(registerScreen);

    }

    //Metodo para no tener que iniciar sesion
    public void exitLogin(View view){
        Intent mainScreen = new Intent(LoginActivity.this, Main.class);
        startActivity(mainScreen);

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