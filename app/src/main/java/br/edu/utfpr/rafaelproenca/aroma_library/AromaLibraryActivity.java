package br.edu.utfpr.rafaelproenca.aroma_library;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AromaLibraryActivity extends AppCompatActivity {

    private EditText editTextAroma;
    private CheckBox checkBoxFavoritos;
    private RadioGroup radioGroupLongevidade, radioGroupProjecao;
    private Spinner spinnerTipoAroma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAroma = findViewById(R.id.editTextAroma);
        checkBoxFavoritos = findViewById(R.id.checkBoxFavoritos);
        radioGroupLongevidade = findViewById(R.id.radioGroupLongevidade);
        radioGroupProjecao = findViewById(R.id.radioGroupProjecao);
        spinnerTipoAroma = findViewById(R.id.spinnerTipoAroma);

        //popularSpinner();
    }
    //fonte de dados
//    public void popularSpinner(){
//        ArrayList<String> listaAromas = new ArrayList<>();
//
//        listaAromas.add(getString(R.string.spinner_aroma_hint));
//        listaAromas.add(getString(R.string.amadeirado));
//        listaAromas.add(getString(R.string.citrico));
//        listaAromas.add(getString(R.string.floral));
//        listaAromas.add(getString(R.string.oriental));
//        listaAromas.add(getString(R.string.frutado));
//        listaAromas.add(getString(R.string.aromatico));
//        listaAromas.add(getString(R.string.aquatico));
//        listaAromas.add(getString(R.string.chipre));
//        listaAromas.add(getString(R.string.gourmand));
//        listaAromas.add(getString(R.string.fougere));
//        listaAromas.add(getString(R.string.especiado));
//        listaAromas.add(getString(R.string.herbal));
//        listaAromas.add(getString(R.string.verde));
//        listaAromas.add(getString(R.string.aldeido));
//        listaAromas.add(getString(R.string.animalistico));
//        listaAromas.add(getString(R.string.balsamico));
//        listaAromas.add(getString(R.string.couro));
//        listaAromas.add(getString(R.string.ozonico));
//        listaAromas.add(getString(R.string.po));
//        listaAromas.add(getString(R.string.tabaco));
//        listaAromas.add(getString(R.string.ambarado));
//        listaAromas.add(getString(R.string.metalico));
//        listaAromas.add(getString(R.string.incenso));
//        listaAromas.add(getString(R.string.terroso));
//        listaAromas.add(getString(R.string.almiscarado));
//
//        //renndetização
//        ArrayAdapter<String> adapterAroma = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaAromas);
//
//        spinnerTipoAroma.setAdapter(adapterAroma);
//    }

    public void limparCampos(View view){
        editTextAroma.setText(null);
        checkBoxFavoritos.setChecked(false);
        radioGroupLongevidade.clearCheck();
        radioGroupProjecao.clearCheck();
        spinnerTipoAroma.setSelection(0);

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

        //spinner
        String tipoAroma = (String) spinnerTipoAroma.getSelectedItem();

        if (tipoAroma == null || tipoAroma == getString(R.string.spinner_aroma_hint)){
            Toast.makeText(this,getString(R.string.faltou_selecionar_o_tipo_de_aroma), Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this,
                getString(R.string.aroma_cadastrado) + aroma + "\n"+
                        (favorito? getString(R.string.tag_favoritos) + "\n" : "") +
                        getString(R.string.longevidade) + longevidade + "\n" +
                         getString(R.string.projecao) + projecao + "\n"+
                        getString(R.string.tipo_de_aroma) + tipoAroma
                ,Toast.LENGTH_LONG).show();
    }
}