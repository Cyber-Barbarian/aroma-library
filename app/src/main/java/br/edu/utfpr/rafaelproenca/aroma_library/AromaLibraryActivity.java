package br.edu.utfpr.rafaelproenca.aroma_library;

import java.text.Normalizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.rafaelproenca.aroma_library.enums.Longevidade;
import br.edu.utfpr.rafaelproenca.aroma_library.enums.Projecao;

public class AromaLibraryActivity extends AppCompatActivity {
    public static final String KEY_AROMA = "KEY_AROMA";
    public static final String KEY_FAVORITO = "KEY_FAVORITO";

    public static final String KEY_LONGEVIDADE = "KEY_LONGEVIDADE";
    public static final String KEY_PROJECAO = "KEY_PROJECAO";
    public static final String KEY_GENERO = "KEY_GENERO";
    public static final String KEY_INDICACAO = "KEY_INDICACAO";
    public static final String KEY_TIPO = "KEY_TIPO";
    public static final String KEY_SAIDA = "KEY_SAIDA";
    public static final String KEY_BASE = "KEY_BASE";
    public static final String KEY_FUNDO = "KEY_FUNDO";
    //ação editar
    public static final String KEY_MODO = "MODO";
    private int modo;
    public static final int MODO_NOVO = 0;
    public static final int MODO_EDITAR = 1;
    private TextView textViewheader;
    private EditText editTextAroma, editTextTextNotasDeSaida, editTextTextNotasDeCorpo, editTextTextNotasDeFundo;
    private CheckBox checkBoxFavoritos;
    private RadioGroup radioGroupLongevidade, radioGroupProjecao, radioGroupGenero;
    RadioButton radioButtonLongevidadeId, radioButtonProjecaoId, radioButtonGeneroId;
    private Spinner spinnerTipoAroma, spinnerIndicacao;

    public String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.novo_aroma));

        textViewheader = findViewById(R.id.textViewheader);
        editTextAroma = findViewById(R.id.editTextAroma);
        editTextTextNotasDeSaida = findViewById(R.id.editTextTextNotasDeSaida);
        editTextTextNotasDeCorpo = findViewById(R.id.editTextTextNotasDeCorpo);
        editTextTextNotasDeFundo = findViewById(R.id.editTextTextNotasDeFundo);
        checkBoxFavoritos = findViewById(R.id.checkBoxFavoritos);
        radioGroupLongevidade = findViewById(R.id.radioGroupLongevidade);
        radioGroupProjecao = findViewById(R.id.radioGroupProjecao);
        radioGroupGenero = findViewById(R.id.radioGroupGenero);
        spinnerTipoAroma = findViewById(R.id.spinnerTipoAroma);
        spinnerIndicacao = findViewById(R.id.spinnerIndicacao);
        textViewheader.requestFocus();
        //ação editar
        Intent intentAbertura = getIntent();
        Bundle bundle = intentAbertura.getExtras();
        if (bundle != null){
            modo = bundle.getInt(KEY_MODO);

            if (modo == MODO_NOVO){
                setTitle(getString(R.string.novo_aroma));
            } else {
                setTitle(getString(R.string.editar_aroma));
                String aromaNome = bundle.getString(AromaLibraryActivity.KEY_AROMA);
                Boolean favorito = bundle.getBoolean(AromaLibraryActivity.KEY_FAVORITO);
                String longevidade = bundle.getString(AromaLibraryActivity.KEY_LONGEVIDADE);
                String projecao = bundle.getString(AromaLibraryActivity.KEY_PROJECAO);
                String genero = bundle.getString(AromaLibraryActivity.KEY_GENERO);
                String indicacaoAroma = bundle.getString(AromaLibraryActivity.KEY_INDICACAO);
                String tipoAroma = bundle.getString(AromaLibraryActivity.KEY_TIPO);
                String notaDeSaida = bundle.getString(AromaLibraryActivity.KEY_SAIDA);
                String notaDeBase = bundle.getString(AromaLibraryActivity.KEY_BASE);
                String notaDeFundo = bundle.getString(AromaLibraryActivity.KEY_FUNDO);

                editTextAroma.setText(aromaNome);
                checkBoxFavoritos.setChecked(favorito);

                //continuar

                if (projecao.equalsIgnoreCase(String.valueOf(Projecao.Fraca))) {
                    radioButtonProjecaoId = findViewById(R.id.radioButtonFraca);
                    radioButtonProjecaoId.setChecked(true);
                } else if (projecao.equalsIgnoreCase(String.valueOf(Projecao.Moderada))) {
                    radioButtonProjecaoId = findViewById(R.id.radioButtonModerada);
                    radioButtonProjecaoId.setChecked(true);
                } else if (projecao.equalsIgnoreCase(String.valueOf(Projecao.Forte))) {
                    radioButtonProjecaoId = findViewById(R.id.radioButtonForte);
                    radioButtonProjecaoId.setChecked(true);
                }
                //spinnerIndicacao.setAutofillHints(indicacaoAroma);
                //spinnerTipoAroma.se


            }
        }

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

    public void limparCampos() {
        editTextAroma.setText(null);
        editTextTextNotasDeSaida.setText(null);
        editTextTextNotasDeCorpo.setText(null);
        editTextTextNotasDeFundo.setText(null);
        checkBoxFavoritos.setChecked(false);
        radioGroupLongevidade.clearCheck();
        radioGroupProjecao.clearCheck();
        radioGroupGenero.clearCheck();
        spinnerTipoAroma.setSelection(0);
        spinnerIndicacao.setSelection(0);

        editTextAroma.requestFocus();
        Toast.makeText(this, R.string.as_entradas_foram_apagadas, Toast.LENGTH_LONG).show();
    }

    public void salvarValores() {
        String aroma = editTextAroma.getText().toString().trim();
        String notaDeSaida = editTextTextNotasDeSaida.getText().toString().trim();
        String notaDeBase = editTextTextNotasDeCorpo.getText().toString().trim();
        String notaDeFundo = editTextTextNotasDeFundo.getText().toString().trim();

        //nome do aroma
        if (aroma == null || aroma.isEmpty()) {
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
        if (radioButtonLongevidadeId == R.id.radioButtonCurta) {
            longevidade = getString(R.string.curta);
        } else if (radioButtonLongevidadeId == R.id.radioButtonMedia) {
            longevidade = getString(R.string.media);
        } else if (radioButtonLongevidadeId == R.id.radioButtonLonga) {
            longevidade = getString(R.string.longa);
        } else {
            Toast.makeText(this, R.string.faltou_preencher_a_longevidade_do_aroma, Toast.LENGTH_LONG).show();
            return;
        }

        //projeção - RadioButton e RadioGroup
        int radioButtonProjecaoId = radioGroupProjecao.getCheckedRadioButtonId();
        String projecao;
        if (radioButtonProjecaoId == R.id.radioButtonFraca) {
            projecao = getString(R.string.fraca);
        } else if (radioButtonProjecaoId == R.id.radioButtonModerada) {
            projecao = getString(R.string.moderada);
        } else if (radioButtonProjecaoId == R.id.radioButtonForte) {
            projecao = getString(R.string.forte);
        } else {
            Toast.makeText(this, R.string.faltou_preencher_a_projecao_do_aroma, Toast.LENGTH_LONG).show();
            return;
        }

        //gênero - RadioButton e RadioGroup
        int radioButtonGeneroId = radioGroupGenero.getCheckedRadioButtonId();
        String genero;
        if (radioButtonGeneroId == R.id.radioButtonMasculino) {
            genero = getString(R.string.radioButtonMasculino);
        } else if (radioButtonGeneroId == R.id.radioButtonFeminino) {
            genero = getString(R.string.radioButtonFeminino);
        } else if (radioButtonGeneroId == R.id.radioButtonUnissex) {
            genero = getString(R.string.radioButtonUnissex);
        } else {
            Toast.makeText(this, R.string.faltou_preencher_o_genero_do_aroma, Toast.LENGTH_LONG).show();
            return;
        }

        //spinner Indicação Aroma
        String indicacaoAroma = (String) spinnerIndicacao.getSelectedItem();

        if (indicacaoAroma == null || indicacaoAroma == getString(R.string.spinner_indicacao_hint)) {
            Toast.makeText(this, getString(R.string.faltou_selecionar_a_indicacao_do_aroma), Toast.LENGTH_LONG).show();
            return;
        }

        //spinner Tipo Aroma
        String tipoAroma = (String) spinnerTipoAroma.getSelectedItem();

        if (tipoAroma == null || tipoAroma == getString(R.string.spinner_aroma_hint)) {
            Toast.makeText(this, getString(R.string.faltou_selecionar_o_tipo_de_aroma), Toast.LENGTH_LONG).show();
            return;
        }

        Intent intenResposta = new Intent();

        intenResposta.putExtra(KEY_AROMA,aroma);
        intenResposta.putExtra(KEY_FAVORITO,favorito);
        intenResposta.putExtra(KEY_LONGEVIDADE,removerAcentos(longevidade));
        intenResposta.putExtra(KEY_PROJECAO,removerAcentos(projecao));
        intenResposta.putExtra(KEY_GENERO,removerAcentos(genero));
        intenResposta.putExtra(KEY_INDICACAO,indicacaoAroma);
        intenResposta.putExtra(KEY_TIPO,tipoAroma);
        intenResposta.putExtra(KEY_SAIDA,notaDeSaida);
        intenResposta.putExtra(KEY_BASE,notaDeBase);
        intenResposta.putExtra(KEY_FUNDO,notaDeFundo);

        setResult(AromaLibraryActivity.RESULT_OK, intenResposta);

        finish();




//        Toast.makeText(this,
//                getString(R.string.aroma_cadastrado) + aroma + "\n" +
//                        (favorito ? getString(R.string.tag_favoritos) + "\n" : "") +
//                        getString(R.string.longevidade) + longevidade + "\n" +
//                        getString(R.string.projecao) + projecao + "\n" +
//                        getString(R.string.textViewGenero) + genero + "\n" +
//                        getString(R.string.indicacao_do_aroma) + indicacaoAroma + "\n" +
//                        getString(R.string.tipo_de_aroma) + tipoAroma + "\n" +
//                        (notaDeSaida != null && !notaDeSaida.isEmpty() ? getString(R.string.textViewNotasDeSaida) + notaDeSaida + "\n" : "") +
//                        (notaDeBase != null && !notaDeBase.isEmpty() ? getString(R.string.textViewNotasDeCorpo) + notaDeBase + "\n" : "") +
//                        (notaDeFundo != null && !notaDeFundo.isEmpty() ? getString(R.string.textViewNotasDeFundo) + notaDeFundo + "\n" : "")
//                , Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aroma_library_opcoes,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if(idMenuItem == R.id.menuItemSalvar){
            salvarValores();
            return true;
        } else if (idMenuItem == R.id.menuItemLimpar){
            limparCampos();
            return true;
        }else {

            return super.onOptionsItemSelected(item);
        }
    }
}