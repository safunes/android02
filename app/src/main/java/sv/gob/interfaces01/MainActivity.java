package sv.gob.interfaces01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import sv.gob.interfaces01.;

import android.widget.TextView;
import android.widget.Toast;

import sv.gob.interfaces01.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);
        ActivityMainBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        //btn = findViewById(R.id.btnLogin);
        //txtUsr =findViewById(R.id.txtUserLayout);
        binding.btnOlvido.setOnClickListener( new View.OnClickListener(){
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(getApplicationContext(), ResetPassword.class);
            startActivity(intent);

        }
        });

        binding.btnRegistro.setOnClickListener( new View.OnClickListener(){
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);

        }
        });

        binding.btnLogin.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {

                        binding.txtUserLayout.setErrorEnabled(false);
                        binding.txtPasswdLayout.setErrorEnabled(false);

                        if(binding.txtUser.getText().toString().isEmpty()) {
                            binding.txtUserLayout.setErrorEnabled(true);
                            binding.txtUserLayout.setError("Debe completar este campo");
                            //binding.txtUserLayout.requestFocus();

                        }
                        if(binding.txtPassws.getText().toString().isEmpty()) {
                            binding.txtPasswdLayout.setErrorEnabled(true);
                            binding.txtPasswdLayout.setError("Debe completar este campo");
                            //binding.txtPasswdLayout.requestFocus();

                        }

                        if(!binding.txtUser.getText().toString().isEmpty() && !binding.txtPassws.getText().toString().isEmpty() )
                        {
                            Intent intent = new Intent(getApplicationContext(),ContentActivity.class);
                            startActivity(intent);
                        }

                        //mBinding.loginUserInputLyt.error = getString(R.string.txt_required_field)

                    }
                }
        );

    }
}