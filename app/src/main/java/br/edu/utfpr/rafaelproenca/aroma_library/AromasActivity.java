package br.edu.utfpr.rafaelproenca.aroma_library;

import static br.edu.utfpr.rafaelproenca.aroma_library.AromaLibraryActivity.removerAcentos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.utfpr.rafaelproenca.aroma_library.modelo.enums.Genero;
import br.edu.utfpr.rafaelproenca.aroma_library.modelo.enums.Longevidade;
import br.edu.utfpr.rafaelproenca.aroma_library.modelo.enums.Projecao;
import br.edu.utfpr.rafaelproenca.aroma_library.modelo.Aroma;
import br.edu.utfpr.rafaelproenca.aroma_library.persistencia.AromasDatabase;
import br.edu.utfpr.rafaelproenca.aroma_library.utils.UtilsAlert;


public class AromasActivity extends AppCompatActivity {

    private ListView listViewAromas;
    private List<Aroma> listaAromas;

    private AromaAdapter adapterAroma;

    private int posicaoSelecionada = -1;

    private ActionMode actionMode;
    private View viewSelecionada;
    private Drawable backgroundDrawable;

    public static final String ARQUIVO_PREFERENCIAS = "br.edu.utfpr.rafaelproenca.aroma_library.preferencias";
    public static final String KEY_ORDENACAO_ASCENDENTE = "ORDENACAO_ASCENDENTE";

    public static final boolean PADRAO_INICIAL_ORDENACAO_ASCENDENTE = true;
    private boolean ordenacaoAscendente = PADRAO_INICIAL_ORDENACAO_ASCENDENTE;

    private MenuItem menuItemOrdenacao;

    //ouvidor de eventos do menu de ação contextual
    private ActionMode.Callback actionCallback = new ActionMode.Callback() {
        //criação, exibiçao do menu
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.aroma_item_selecionado, menu);
            return true;
        }



        //altera em tempo de execução
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        //click no menu
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            AdapterView.AdapterContextMenuInfo info;
            info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int idMenuItem = item.getItemId();
            if (idMenuItem == R.id.menuItemEditar) {
                System.out.println("editar aroma");
                editarAroma();
                return true;
            } else if (idMenuItem == R.id.menuItemExcluir) {
                excluirAroma();

                return true;
            } else {
                //return super.onContextItemSelected(item);
                return false;
            }

        }

        //destroi o menu
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelecionada != null) {
                viewSelecionada.setBackground(backgroundDrawable);
            }

            actionMode = null;
            viewSelecionada = null;
            backgroundDrawable = null;

            listViewAromas.setEnabled(true);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aromas);

        setTitle(getString(R.string.cadastro_de_aromas));
        listViewAromas = findViewById(R.id.listViewAromas);

        /*

        listViewAromas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aroma aroma = (Aroma) listViewAromas.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "\"" + aroma.getNome() + "\"" + "\n" + getString(R.string.foi_clicado),
                        Toast.LENGTH_SHORT).show();
            }
        });

         */
        lerPreferencias();
        popularListaAromas();
        registerForContextMenu(listViewAromas);
    }

    private void popularListaAromas() {

        //listaAromas = new ArrayList<>();
        Aroma aroma;
        boolean favoritos;
        Longevidade longevidade;
        Longevidade[] longevidadeArray = Longevidade.values();
        Projecao projecao;
        Projecao[] projecaoArray = Projecao.values();
        Genero genero;
        Genero[] generoArray = Genero.values();



        AromasDatabase database = AromasDatabase.getInstance(this);
        if(ordenacaoAscendente){
            listaAromas = database.getAromaDao().queryAllAscending();
        }else{
            listaAromas = database.getAromaDao().queryAllDescending();
        }
        adapterAroma = new AromaAdapter(this, listaAromas);


        listViewAromas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aroma aroma = (Aroma) listViewAromas.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "\"" + aroma.getNome() + "\"" + "\n" + getString(R.string.foi_selecionado),
                        Toast.LENGTH_SHORT).show();
                posicaoSelecionada = position;
                editarAroma();
            }
        });

        listViewAromas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (actionMode != null) {
                    return false;
                }
                Aroma aroma = (Aroma) listViewAromas.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "\"" + aroma.getNome() + "\"" + "\n" + getString(R.string.foi_selecionado),
                        Toast.LENGTH_SHORT).show();

                posicaoSelecionada = position;

                viewSelecionada = view;
                backgroundDrawable = view.getBackground();

                view.setBackgroundColor(Color.LTGRAY);

                listViewAromas.setEnabled(false);

                actionMode = startSupportActionMode(actionCallback);

                return true;
            }
        });
        listViewAromas.setAdapter(adapterAroma);
    }

    public void abrirSobre() {
        Intent intentAbertura = new Intent(this, SobreActivity.class);
        startActivity(intentAbertura);

    }

    //launcher para tela de cadastro
    ActivityResultLauncher<Intent> laucherNovoAroma = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == AromasActivity.RESULT_OK) {
                        Intent intent = result.getData();

                        Bundle bundle = intent.getExtras();

                        if (bundle != null) {
                            long id =  bundle.getLong(AromaLibraryActivity.KEY_ID);

                            AromasDatabase database = AromasDatabase.getInstance(AromasActivity.this);

                            Aroma aromaNovo = database.getAromaDao().queryForId(id);

                            listaAromas.add(aromaNovo);
                            ordenarLista();

                        }
                    }
                }
            });

    public void abrirAdicionar() {

        Intent intentAbertura = new Intent(this, AromaLibraryActivity.class);
        intentAbertura.putExtra(AromaLibraryActivity.KEY_MODO, AromaLibraryActivity.MODO_NOVO);
        laucherNovoAroma.launch(intentAbertura);

    }

    //cria o menu fixo
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aromas_opcoes, menu);
        menuItemOrdenacao = menu.findItem(R.id.menuItemOrdenacao);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        atualizarIconeOrdenacao();
        return true;
    }

    //um metodo so pra todos os menus items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();
        if (idMenuItem == R.id.menuItemAdicionar) {
            abrirAdicionar();
            return true;
        } else if (idMenuItem == R.id.menuItemSobre) {
            abrirSobre();
            return true;
        } else if (idMenuItem == R.id.menuItemOrdenacao) {
            salvarPreferenciasOrdenacao(!ordenacaoAscendente);
            atualizarIconeOrdenacao();
            ordenarLista();
            return true;
        } else if (idMenuItem == R.id.menuItemRestaurar){

            confirmarRestaurarPadroes();

            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }

    /*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.aroma_item_selecionado, menu);

    }
    */


    private void excluirAroma() {
        final Aroma aroma = listaAromas.get(posicaoSelecionada);

        //String mensagem = getString(R.string.deseja_deletar) + aroma.getNome() + "\"";

        String mensagem = getString(R.string.deseja_deletar,aroma.getNome().toString()) ;

        DialogInterface.OnClickListener listenerSim = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AromasDatabase database = AromasDatabase.getInstance(AromasActivity.this);

                int quantideDeletada = database.getAromaDao().delete(aroma);

                if (quantideDeletada!=1){
                    UtilsAlert.mostrarAviso(AromasActivity.this, R.string.erro_na_operacao_delete);
                    return;
                }

                listaAromas.remove(posicaoSelecionada);
                adapterAroma.notifyDataSetChanged();
                actionMode.finish();
            }
        };

        UtilsAlert.confirmarAcao(this, mensagem, listenerSim, null);


    }

    private void editarAroma() {



        Aroma aromaEditar = listaAromas.get(posicaoSelecionada);
        System.out.println("aroma editar " + aromaEditar.getLongevidade().toString());
        Intent intentAbertura = new Intent(this, AromaLibraryActivity.class);
        intentAbertura.putExtra(AromaLibraryActivity.KEY_MODO, AromaLibraryActivity.MODO_EDITAR);
        intentAbertura.putExtra(AromaLibraryActivity.KEY_ID, aromaEditar.getId());

        laucherEditarAroma.launch(intentAbertura);
    }

    ActivityResultLauncher<Intent> laucherEditarAroma = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == AromasActivity.RESULT_OK) {
                        Intent intent = result.getData();

                        Bundle bundle = intent.getExtras();

                        if (bundle != null) {

                            final Aroma aromaOriginal = listaAromas.get(posicaoSelecionada);


                            long id =  bundle.getLong(AromaLibraryActivity.KEY_ID);

                            AromasDatabase database = AromasDatabase.getInstance(AromasActivity.this);

                            final Aroma aromaEditado = database.getAromaDao().queryForId(id);

                            //Aroma aromaEditado = listaAromas.get(posicaoSelecionada);

                            listaAromas.set(posicaoSelecionada,aromaEditado);

                            ordenarLista();

                        }
                    }
                    posicaoSelecionada = -1;
                    if (actionMode != null) {
                        actionMode.finish();
                    }
                }
            });

/*    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int idMenuItem = item.getItemId();
        if (idMenuItem == R.id.menuItemEditar){
            editarAroma(info.position);
            return true;
        } else if (idMenuItem == R.id.menuItemExcluir){
            excluirAroma(info.position);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

 */

    private void lerPreferencias() {
        SharedPreferences shared = getSharedPreferences(ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        ordenacaoAscendente = shared.getBoolean(KEY_ORDENACAO_ASCENDENTE, ordenacaoAscendente);

    }

    private void salvarPreferenciasOrdenacao(boolean novoValor) {
        SharedPreferences shared = getSharedPreferences(ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(KEY_ORDENACAO_ASCENDENTE, novoValor);
        editor.commit();

        ordenacaoAscendente = novoValor;
    }

    private void ordenarLista() {
        if (ordenacaoAscendente) {
            Collections.sort(listaAromas, Aroma.ordenacaoCrescente);

        } else {
            Collections.sort(listaAromas, Aroma.ordenacaoDecrescente);
        }
        adapterAroma.notifyDataSetChanged();
    }

    private void atualizarIconeOrdenacao(){
        if(ordenacaoAscendente) {
            menuItemOrdenacao.setIcon(android.R.drawable.arrow_down_float);
        } else {
            menuItemOrdenacao.setIcon(android.R.drawable.arrow_up_float);
        }
    }


    private void confirmarRestaurarPadroes(){
        DialogInterface.OnClickListener listenerSim = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                restaurarPadroes();
                atualizarIconeOrdenacao();
                ordenarLista();

                Toast.makeText(AromasActivity.this,
                        R.string.configuracoes_retornaram_ao_padrao_de_fabrica, Toast.LENGTH_LONG).show();

            }
        };
        UtilsAlert.confirmarAcao(this, getString(R.string.do_you_want_to_restore_defaults), listenerSim, null );
    }
    private void restaurarPadroes(){

        SharedPreferences shared = getSharedPreferences(ARQUIVO_PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        /*
        editor.remove(KEY_ORDENACA_ASCENDENTE);
        editor.remove(AromaLibraryActivity.KEY_SUGERIR_TIPO);
        editor.remove(AromaLibraryActivity.KEY_ULTIMO_TIPO);
         */
        editor.clear();
        editor.commit();

        ordenacaoAscendente = PADRAO_INICIAL_ORDENACAO_ASCENDENTE;

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

