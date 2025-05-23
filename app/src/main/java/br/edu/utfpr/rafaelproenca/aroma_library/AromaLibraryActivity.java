package br.edu.utfpr.rafaelproenca.aroma_library;

import java.text.Normalizer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.rafaelproenca.aroma_library.modelo.enums.Genero;
import br.edu.utfpr.rafaelproenca.aroma_library.modelo.enums.Longevidade;
import br.edu.utfpr.rafaelproenca.aroma_library.modelo.enums.Projecao;
import br.edu.utfpr.rafaelproenca.aroma_library.modelo.Aroma;
import br.edu.utfpr.rafaelproenca.aroma_library.persistencia.AromasDatabase;
import br.edu.utfpr.rafaelproenca.aroma_library.utils.UtilsAlert;

public class AromaLibraryActivity extends AppCompatActivity {
    public static final String KEY_ID = "ID";
    //ação editar
    public static final String KEY_MODO = "MODO";

    public static final String KEY_SUGERIR_TIPO = "SUGERIR_TIPO";
    public static final String KEY_ULTIMO_TIPO = "ULTIMO_TIPO";
    private int modo;
    public static final int MODO_NOVO = 0;
    public static final int MODO_EDITAR = 1;
    private TextView textViewheader;
    private EditText editTextAroma, editTextTextNotasDeSaida, editTextTextNotasDeCorpo, editTextTextNotasDeFundo;
    private CheckBox checkBoxFavoritos;
    private RadioGroup radioGroupLongevidade, radioGroupProjecao, radioGroupGenero;
    RadioButton radioButtonLongevidadeId, radioButtonProjecaoId, radioButtonGeneroId;
    private Spinner spinnerTipoAroma, spinnerIndicacao;

    private Aroma aromaOriginal;

    private boolean sugerirTipo = false;
    private int ultimoTipo = 0;

    public static String removerAcentos(String str) {
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
        lerPreferencias();
        textViewheader.requestFocus();
        //ação editar
        Intent intentAbertura = getIntent();
        Bundle bundle = intentAbertura.getExtras();
        if (bundle != null){
            modo = bundle.getInt(KEY_MODO);

            if (modo == MODO_NOVO){
                setTitle(getString(R.string.novo_aroma));
                if (sugerirTipo){
                    spinnerTipoAroma.setSelection(ultimoTipo);

                }
            } else {
                setTitle(getString(R.string.editar_aroma));
                long id = bundle.getLong(KEY_ID);

                AromasDatabase database = AromasDatabase.getInstance(this);

                aromaOriginal = database.getAromaDao().queryForId(id);


                editTextAroma.setText(aromaOriginal.getNome());
                editTextTextNotasDeSaida.setText(aromaOriginal.getPiramideOlfativaSaida());
                editTextTextNotasDeCorpo.setText(aromaOriginal.getPiramideOlfativaCorpo());
                editTextTextNotasDeFundo.setText(aromaOriginal.getPiramideOlfativaFundo());
                checkBoxFavoritos.setChecked(aromaOriginal.getFavoritos());
                String longevidade = enumTranslator(String.valueOf(aromaOriginal.getLongevidade()));
                String projecao = enumTranslator(String.valueOf(aromaOriginal.getProjecao()));
                String genero = enumTranslator(String.valueOf(aromaOriginal.getGenero()));
                String indicacaoAroma = aromaOriginal.getIndicacao();
                String tipoAroma = aromaOriginal.getTipoDeAroma();

                if (longevidade.equalsIgnoreCase(getResources().getString(R.string.curta)) || longevidade.equalsIgnoreCase(String.valueOf(Longevidade.Curta))) {
                    radioButtonLongevidadeId = findViewById(R.id.radioButtonCurta);
                    radioButtonLongevidadeId.setChecked(true);
                } else if (longevidade.equalsIgnoreCase(getResources().getString(R.string.media)) || longevidade.equalsIgnoreCase(String.valueOf(Longevidade.Media))) {
                    radioButtonLongevidadeId = findViewById(R.id.radioButtonMedia);
                    radioButtonLongevidadeId.setChecked(true);
                } else if (longevidade.equalsIgnoreCase(getResources().getString(R.string.longa)) || longevidade.equalsIgnoreCase(String.valueOf(Longevidade.Longa))) {
                    radioButtonLongevidadeId = findViewById(R.id.radioButtonLonga);
                    radioButtonLongevidadeId.setChecked(true);
                }

                if (projecao.equalsIgnoreCase(getResources().getString(R.string.fraca)) || projecao.equalsIgnoreCase(String.valueOf(Projecao.Fraca))) {
                    radioButtonProjecaoId = findViewById(R.id.radioButtonFraca);
                    radioButtonProjecaoId.setChecked(true);
                } else if (projecao.equalsIgnoreCase(getResources().getString(R.string.moderada)) || projecao.equalsIgnoreCase(String.valueOf(Projecao.Moderada))) {
                    radioButtonProjecaoId = findViewById(R.id.radioButtonModerada);
                    radioButtonProjecaoId.setChecked(true);
                } else if (projecao.equalsIgnoreCase(getResources().getString(R.string.forte)) || projecao.equalsIgnoreCase(String.valueOf(Projecao.Forte))) {
                    radioButtonProjecaoId = findViewById(R.id.radioButtonForte);
                    radioButtonProjecaoId.setChecked(true);
                }

                if (genero.equalsIgnoreCase(getResources().getString(R.string.radioButtonMasculino)) || genero.equalsIgnoreCase(String.valueOf(Genero.Masculino))) {
                    radioButtonGeneroId = findViewById(R.id.radioButtonMasculino);
                    radioButtonGeneroId.setChecked(true);
                } else if (genero.equalsIgnoreCase(getResources().getString(R.string.radioButtonFeminino)) || genero.equalsIgnoreCase(String.valueOf(Genero.Feminino))) {
                    radioButtonGeneroId = findViewById(R.id.radioButtonFeminino);
                    radioButtonGeneroId.setChecked(true);
                } else if (genero.equalsIgnoreCase(getResources().getString(R.string.radioButtonUnissex)) || genero.equalsIgnoreCase(String.valueOf(Genero.Unissex))) {
                    radioButtonGeneroId = findViewById(R.id.radioButtonUnissex);
                    radioButtonGeneroId.setChecked(true);
                }
                int indicacaoPosition = 0;
                for (String elem : getResources().getStringArray(R.array.indicacao_aromas)) {
                    if (elem.equalsIgnoreCase(indicacaoAroma)){
                        spinnerIndicacao.setSelection(indicacaoPosition);
                    }else{indicacaoPosition+=1;}
                }
                int tipoAromaPosition = 0;
                for (String elem : getResources().getStringArray(R.array.tipos_de_aromas)) {
                    if (elem.equalsIgnoreCase(tipoAroma)){
                        spinnerTipoAroma.setSelection(tipoAromaPosition);
                    }else{
                        tipoAromaPosition +=1;}
                }

                editTextAroma.requestFocus();
                editTextAroma.setSelection(editTextAroma.getText().length());



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
        //Toast.makeText(this, R.string.as_entradas_foram_apagadas, Toast.LENGTH_LONG).show();
        UtilsAlert.mostrarAviso(this,R.string.as_entradas_foram_apagadas);
    }

    public void salvarValores() {
        String aroma = editTextAroma.getText().toString().trim();
        String notaDeSaida = editTextTextNotasDeSaida.getText().toString().trim();
        String notaDeBase = editTextTextNotasDeCorpo.getText().toString().trim();
        String notaDeFundo = editTextTextNotasDeFundo.getText().toString().trim();

        //nome do aroma
        if (aroma == null || aroma.isEmpty()) {
            UtilsAlert.mostrarAviso(this,R.string.digite_um_valor_valido);
            //Toast.makeText(this, R.string.digite_um_valor_valido, Toast.LENGTH_LONG).show();
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
            //Toast.makeText(this, R.string.faltou_preencher_a_longevidade_do_aroma, Toast.LENGTH_LONG).show();
            UtilsAlert.mostrarAviso(this,R.string.faltou_preencher_a_longevidade_do_aroma);
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
            //Toast.makeText(this, R.string.faltou_preencher_a_projecao_do_aroma, Toast.LENGTH_LONG).show();
            UtilsAlert.mostrarAviso(this,R.string.faltou_preencher_a_projecao_do_aroma);
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
            //Toast.makeText(this, R.string.faltou_preencher_o_genero_do_aroma, Toast.LENGTH_LONG).show();
            UtilsAlert.mostrarAviso(this,R.string.faltou_preencher_o_genero_do_aroma);
            return;
        }

        //spinner Indicação Aroma
        String indicacaoAroma = (String) spinnerIndicacao.getSelectedItem();

        if (indicacaoAroma == null || indicacaoAroma == getString(R.string.spinner_indicacao_hint)) {
            //Toast.makeText(this, getString(R.string.faltou_selecionar_a_indicacao_do_aroma), Toast.LENGTH_LONG).show();
            UtilsAlert.mostrarAviso(this,R.string.faltou_selecionar_a_indicacao_do_aroma);
            return;
        }

        //spinner Tipo Aroma
        String tipoAroma = (String) spinnerTipoAroma.getSelectedItem();

        if (tipoAroma == null || tipoAroma == getString(R.string.spinner_aroma_hint)) {
            //Toast.makeText(this, getString(R.string.faltou_selecionar_o_tipo_de_aroma), Toast.LENGTH_LONG).show();
            UtilsAlert.mostrarAviso(this,R.string.faltou_selecionar_o_tipo_de_aroma);
            return;
        }

        Aroma aromaObj = new Aroma(aroma, favorito,Longevidade.valueOf(removerAcentos(enumTranslator(longevidade))),
                Projecao.valueOf(enumTranslator(projecao)), Genero.valueOf(enumTranslator(genero)),
                indicacaoAroma,tipoAroma, notaDeSaida, notaDeBase, notaDeFundo);

        if(aromaObj.equals(aromaOriginal)){
            // valores não foram alterado, não precisa salvar nada
            setResult(AromasActivity.RESULT_CANCELED);
            finish();
            return;
        }


        int tipoAromaInt;
        try {
            tipoAromaInt = Integer.parseInt(tipoAroma.replaceAll("\\D+",""));
        } catch (NumberFormatException e) {
            tipoAromaInt = 0;
        }


        salvatUltimoTipo(tipoAromaInt);

        Intent intenResposta = new Intent();

        AromasDatabase database = AromasDatabase.getInstance(this);

        if(modo == MODO_NOVO){ //novo registro
            long novoId = database.getAromaDao().insert(aromaObj);

            if (novoId<=0){
                UtilsAlert.mostrarAviso(this,R.string.erro_na_operacao_escrita);
            return;}

            aromaObj.setId(novoId);

        }else{ //update

            aromaObj.setId(aromaOriginal.getId());

            int quantRegistrosAlterados = database.getAromaDao().update(aromaObj);

            if (quantRegistrosAlterados!=1) {
                UtilsAlert.mostrarAviso(this,R.string.erro_na_operacao_escrita);
                return;
            }



        }

        intenResposta.putExtra(KEY_ID, aromaObj.getId());


        setResult(AromaLibraryActivity.RESULT_OK, intenResposta);

        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aroma_library_opcoes,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menuItemSugerirTipo);
        item.setChecked(sugerirTipo);
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
        }else if (idMenuItem == R.id.menuItemSugerirTipo){
            boolean valor = !item.isChecked();
            salvarSugerirTipo(valor);
            item.setChecked(valor);
            if(sugerirTipo){
                spinnerTipoAroma.setSelection(ultimoTipo);
            }
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }
    private void lerPreferencias(){
        SharedPreferences shared = getSharedPreferences(AromasActivity.ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);

        sugerirTipo = shared.getBoolean(KEY_SUGERIR_TIPO, sugerirTipo);
        ultimoTipo = shared.getInt(KEY_ULTIMO_TIPO, ultimoTipo);
    }

    private void salvarSugerirTipo(boolean novoValor){
        SharedPreferences shared = getSharedPreferences(AromasActivity.ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(KEY_SUGERIR_TIPO, novoValor);

        editor.commit();

        sugerirTipo = novoValor;
    }

    private void salvatUltimoTipo(int novovalor){
        SharedPreferences shared = getSharedPreferences(AromasActivity.ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt(KEY_ULTIMO_TIPO, novovalor);
        editor.commit();
        ultimoTipo = novovalor;
    }

    private String enumTranslator(String valueEnum){
        valueEnum = removerAcentos(valueEnum);
        String translatedValueEnum = "";
        if (valueEnum.equalsIgnoreCase(getResources().getString(R.string.curta)) || valueEnum.equalsIgnoreCase(String.valueOf(Longevidade.Curta))){
            translatedValueEnum = String.valueOf(Longevidade.Curta);
        } else if (valueEnum.equalsIgnoreCase(getResources().getString(R.string.media)) || valueEnum.equalsIgnoreCase(String.valueOf(Longevidade.Media))){
            translatedValueEnum = String.valueOf(Longevidade.Media);
        } else if (valueEnum.equalsIgnoreCase(getResources().getString(R.string.longa)) || valueEnum.equalsIgnoreCase(String.valueOf(Longevidade.Longa))){
            translatedValueEnum = String.valueOf(Longevidade.Longa);
        } else if (valueEnum.equalsIgnoreCase(getResources().getString(R.string.fraca))|| valueEnum.equalsIgnoreCase(String.valueOf(Projecao.Fraca))){
            translatedValueEnum = String.valueOf(Projecao.Fraca);
        } else if (valueEnum.equalsIgnoreCase(getResources().getString(R.string.moderada)) || valueEnum.equalsIgnoreCase(String.valueOf(Projecao.Moderada))){
            translatedValueEnum = String.valueOf(Projecao.Moderada);
        } else if (valueEnum.equalsIgnoreCase(getResources().getString(R.string.forte)) || valueEnum.equalsIgnoreCase(String.valueOf(Projecao.Forte))){
            translatedValueEnum = String.valueOf(Projecao.Forte);
        } else if (valueEnum.equalsIgnoreCase(getResources().getString(R.string.radioButtonMasculino)) || valueEnum.equalsIgnoreCase(String.valueOf(Genero.Masculino))){
            translatedValueEnum = String.valueOf(Genero.Masculino);
        } else if (valueEnum.equalsIgnoreCase(getResources().getString(R.string.radioButtonFeminino)) || valueEnum.equalsIgnoreCase(String.valueOf(Genero.Feminino))){
            translatedValueEnum = String.valueOf(Genero.Feminino);
        } else if (valueEnum.equalsIgnoreCase(getResources().getString(R.string.radioButtonUnissex)) || valueEnum.equalsIgnoreCase(String.valueOf(Genero.Unissex))){
            translatedValueEnum = String.valueOf(Genero.Unissex);
        }
            return translatedValueEnum;
    }
}