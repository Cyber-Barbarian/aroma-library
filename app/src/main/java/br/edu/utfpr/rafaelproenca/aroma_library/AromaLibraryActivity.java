package br.edu.utfpr.rafaelproenca.aroma_library;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AromaLibraryActivity extends AppCompatActivity {

    private EditText editTextAroma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAroma = findViewById(R.id.editTextAroma);

    }

    public void limparCampos(View view){
        editTextAroma.setText(null);

        editTextAroma.requestFocus();
        Toast.makeText(this, R.string.as_entradas_foram_apagadas, Toast.LENGTH_LONG).show();
    }

    public void salvarValores(View view){
        String aroma = editTextAroma.getText().toString().trim();

        if (aroma == null || aroma.isEmpty()){
            Toast.makeText(this, R.string.digite_um_valor_valido, Toast.LENGTH_LONG).show();
            editTextAroma.setText(null);
            editTextAroma.requestFocus();
            return;
        }
        Toast.makeText(this,getString(R.string.aroma_cadastrado) + aroma + "\n",Toast.LENGTH_LONG).show();
    }
}