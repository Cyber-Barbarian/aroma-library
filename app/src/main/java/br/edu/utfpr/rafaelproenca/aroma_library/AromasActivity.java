package br.edu.utfpr.rafaelproenca.aroma_library;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
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
import java.util.List;

import br.edu.utfpr.rafaelproenca.aroma_library.enums.Genero;
import br.edu.utfpr.rafaelproenca.aroma_library.enums.Longevidade;
import br.edu.utfpr.rafaelproenca.aroma_library.enums.Projecao;

public class AromasActivity extends AppCompatActivity {

    private ListView listViewAromas;
    private List<Aroma> listaAromas;

    private AromaAdapter adapterAroma;

    private int posicaoSelecionada = -1;

    private ActionMode actionMode;
    private View     viewSelecionada;
    private Drawable backgroundDrawable;

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
            if (idMenuItem == R.id.menuItemEditar){
                editarAroma();
                return true;
            } else if (idMenuItem == R.id.menuItemExcluir){
                excluirAroma();
                mode.finish();
                return true;
            } else {
                //return super.onContextItemSelected(item);
                return false;
            }

        }
        //destroi o menu
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelecionada != null){
                viewSelecionada.setBackground(backgroundDrawable);
            }

            actionMode         = null;
            viewSelecionada    = null;
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
        popularListaAromas();
        registerForContextMenu(listViewAromas);
    }

    private void popularListaAromas() {

        listaAromas = new ArrayList<>();
        Aroma aroma;
        boolean favoritos;
        Longevidade longevidade;
        Longevidade[] longevidadeArray = Longevidade.values();
        Projecao projecao;
        Projecao[] projecaoArray = Projecao.values();
        Genero genero;
        Genero[] generoArray = Genero.values();

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
    public void abrirSobre(){
        Intent intentAbertura = new Intent(this, SobreActivity.class);
        startActivity(intentAbertura);

    }

    //launcher para tela de cadastro
    ActivityResultLauncher<Intent> laucherNovoAroma = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == AromasActivity.RESULT_OK){
                        Intent intent = result.getData();

                        Bundle bundle = intent.getExtras();

                        if (bundle != null) {
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

                            Aroma aromaNovo = new Aroma(aromaNome, favorito,Longevidade.valueOf(longevidade),Projecao.valueOf(projecao),
                                    Genero.valueOf(genero),indicacaoAroma, tipoAroma, notaDeSaida, notaDeBase, notaDeFundo) ;

                            listaAromas.add(aromaNovo);
                            adapterAroma.notifyDataSetChanged();

                        }
                    }
                }
            });
    public void abrirAdicionar(){

        Intent intentAbertura = new Intent(this,AromaLibraryActivity.class);
        intentAbertura.putExtra(AromaLibraryActivity.KEY_MODO, AromaLibraryActivity.MODO_NOVO);
        laucherNovoAroma.launch(intentAbertura);

    }

    //cria o menu fixo
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aromas_opcoes, menu);
        return true;
    }

    //um metodo so pra todos os menus items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();
        if (idMenuItem == R.id.menuItemAdicionar){
            abrirAdicionar();
            return true;
        }else if (idMenuItem == R.id.menuItemSobre){
            abrirSobre();
            return true;
        }else {
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


    private void  excluirAroma(){
        listaAromas.remove(posicaoSelecionada);
        adapterAroma.notifyDataSetChanged();
    }

    private void editarAroma(){


        Aroma aromaEditar = listaAromas.get(posicaoSelecionada);
        Intent intentAbertura =  new Intent(this, AromaLibraryActivity.class);
        intentAbertura.putExtra(AromaLibraryActivity.KEY_MODO, AromaLibraryActivity.MODO_EDITAR);
        intentAbertura.putExtra(AromaLibraryActivity.KEY_AROMA ,aromaEditar.getNome());
        intentAbertura.putExtra(AromaLibraryActivity.KEY_FAVORITO ,aromaEditar.isFavoritos());
        intentAbertura.putExtra(AromaLibraryActivity.KEY_LONGEVIDADE ,aromaEditar.getLongevidade().toString());
        intentAbertura.putExtra(AromaLibraryActivity.KEY_PROJECAO ,aromaEditar.getProjecao().toString());
        intentAbertura.putExtra(AromaLibraryActivity.KEY_GENERO ,aromaEditar.getGenero().toString());
        intentAbertura.putExtra(AromaLibraryActivity.KEY_INDICACAO ,aromaEditar.getIndicacao());
        intentAbertura.putExtra(AromaLibraryActivity.KEY_TIPO ,aromaEditar.getTipoDeAroma());
        intentAbertura.putExtra(AromaLibraryActivity.KEY_SAIDA ,aromaEditar.getPiramideOlfativaSaida());
        intentAbertura.putExtra(AromaLibraryActivity.KEY_BASE ,aromaEditar.getPiramideOlfativaCorpo());
        intentAbertura.putExtra(AromaLibraryActivity.KEY_FUNDO ,aromaEditar.getPiramideOlfativaFundo());
        laucherEditarAroma.launch(intentAbertura);
    }

    ActivityResultLauncher<Intent> laucherEditarAroma = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == AromasActivity.RESULT_OK){
                        Intent intent = result.getData();

                        Bundle bundle = intent.getExtras();

                        if (bundle != null) {
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

                            Aroma aromaEditado = listaAromas.get(posicaoSelecionada);

                            aromaEditado.setNome(aromaNome);
                            aromaEditado.setFavoritos(favorito);
                            aromaEditado.setLongevidade(Longevidade.valueOf(longevidade));
                            aromaEditado.setProjecao(Projecao.valueOf(projecao));
                            aromaEditado.setGenero(Genero.valueOf(genero));
                            aromaEditado.setIndicacao(indicacaoAroma);
                            aromaEditado.setTipoDeAroma(tipoAroma);
                            aromaEditado.setPiramideOlfativaSaida(notaDeSaida);
                            aromaEditado.setPiramideOlfativaCorpo(notaDeBase);
                            aromaEditado.setPiramideOlfativaFundo(notaDeFundo);


                            adapterAroma.notifyDataSetChanged();

                        }
                    }
                    posicaoSelecionada = -1;
                    if (actionMode!=null){
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
}