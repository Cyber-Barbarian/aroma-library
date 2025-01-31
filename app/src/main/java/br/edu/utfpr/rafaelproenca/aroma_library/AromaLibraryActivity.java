package br.edu.utfpr.rafaelproenca.aroma_library;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AromaLibraryActivity extends AppCompatActivity {

    private EditText editTextAroma;
    private CheckBox checkBoxFavoritos;
    private RadioGroup radioGroupLongevidade, radioGroupProjecao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAroma = findViewById(R.id.editTextAroma);
        checkBoxFavoritos = findViewById(R.id.checkBoxFavoritos);
        radioGroupLongevidade = findViewById(R.id.radioGroupLongevidade);
        radioGroupProjecao = findViewById(R.id.radioGroupProjecao);


    }

    public void limparCampos(View view){
        editTextAroma.setText(null);
        checkBoxFavoritos.setChecked(false);
        radioGroupLongevidade.clearCheck();
        radioGroupProjecao.clearCheck();

        editTextAroma.requestFocus();
        Toast.makeText(this, R.string.as_entradas_foram_apagadas, Toast.LENGTH_LONG).show();
    }

    public void salvarValores(View view){
        String aroma = editTextAroma.getText().toString().trim();

        //nome do aroma
        if (aroma == null || aroma.isEmpty()){
            Toast.makeText(this, R.string.digite_um_valor_valido, Toast.LENGTH_LONG).show();
            editTextAroma.setText(null);
            editTextAroma.requestFocus();
            return;
        }

        //favorito - Checkbox
        boolean favorito = checkBoxFavoritos.isChecked();

        //longevidade - RadioButton e RadioGroup
        int radioButtonLongevidadeId = radioGroupLongevidade.getCheckedRadioButtonId();
        String longevidade;
        if (radioButtonLongevidadeId == R.id.radioButtonCurta){
            longevidade = getString(R.string.curta);
        } else if (radioButtonLongevidadeId == R.id.radioButtonMedia){
            longevidade = getString(R.string.media);
        } else if (radioButtonLongevidadeId == R.id.radioButtonLonga){
            longevidade = getString(R.string.longa);
        } else {
            Toast.makeText(this, R.string.faltou_preencher_a_longevidade_do_aroma, Toast.LENGTH_LONG).show();
            return;
        }

        //projeção - RadioButton e RadioGroup
        int radioButtonProjecaoId = radioGroupProjecao.getCheckedRadioButtonId();
        String projecao;
        if (radioButtonProjecaoId == R.id.radioButtonFraca){
            projecao = getString(R.string.fraca);
        } else if (radioButtonProjecaoId == R.id.radioButtonModerada){
            projecao = getString(R.string.moderada);
        } else if (radioButtonProjecaoId == R.id.radioButtonForte){
            projecao = getString(R.string.forte);
        } else {
            Toast.makeText(this, R.string.faltou_preencher_a_projecao_do_aroma, Toast.LENGTH_LONG).show();
            return;
        }


        Toast.makeText(this,
                getString(R.string.aroma_cadastrado) + aroma + "\n"+
                        (favorito? getString(R.string.tag_favoritos) + "\n" : "") +
                        getString(R.string.longevidade) + longevidade + "\n" +
                         getString(R.string.projecao) + projecao + "\n"
                ,Toast.LENGTH_LONG).show();
    }
}