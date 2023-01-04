package login_and_registeration;

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

import com.archivo.move.R;

public class RegisterActivity extends AppCompatActivity {

    // VARIABLES GLOBALES
    EditText txt_name,txt_email,txt_password,txt_confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        txt_name = findViewById(R.id.txt_name);
        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        txt_confirmPassword = findViewById(R.id.txt_confirm);



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