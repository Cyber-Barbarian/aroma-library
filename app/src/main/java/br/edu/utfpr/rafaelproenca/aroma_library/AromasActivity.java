package br.edu.utfpr.rafaelproenca.aroma_library;

import android.content.Intent;
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
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aromas);

        setTitle(getString(R.string.cadastro_de_aromas));
        listViewAromas = findViewById(R.id.listViewAromas);
        listViewAromas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aroma aroma = (Aroma) listViewAromas.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "\"" + aroma.getNome() + "\"" + "\n" + getString(R.string.foi_clicado),
                        Toast.LENGTH_SHORT).show();
            }
        });
        popularListaAromas();
        registerForContextMenu(listViewAromas);
    }

    private void popularListaAromas() {
        /*
        String[] aromas_nome = getResources().getStringArray(R.array.aromas_nome);
        int[] aromas_favoritos = getResources().getIntArray(R.array.aromas_favorito);
        int[] aromas_longevidade = getResources().getIntArray(R.array.aromas_longevidade);
        int[] aromas_projecao = getResources().getIntArray(R.array.aromas_projecao);
        int[] aromas_genero = getResources().getIntArray(R.array.aromas_genero);
        String[] aromas_indicacao = getResources().getStringArray(R.array.aromas_indicacao);
        String[] aromas_tipo_de_aroma = getResources().getStringArray(R.array.aromas_tipo_de_aroma);
        String[] aromas_piramide_olfativa_saida = getResources().getStringArray(R.array.aromas_piramide_olfativa_saida);
        String[] aromas_piramide_olfativa_corpo = getResources().getStringArray(R.array.aromas_piramide_olfativa_corpo);
        String[] aromas_piramide_olfativa_fundo = getResources().getStringArray(R.array.aromas_piramide_olfativa_fundo);
        */

        listaAromas = new ArrayList<>();
        Aroma aroma;
        boolean favoritos;
        Longevidade longevidade;
        Longevidade[] longevidadeArray = Longevidade.values();
        Projecao projecao;
        Projecao[] projecaoArray = Projecao.values();
        Genero genero;
        Genero[] generoArray = Genero.values();

        /*
        for (int cont = 0; cont < aromas_nome.length; cont++) {
            favoritos = (aromas_favoritos[cont] == 1 ? true : false);
            longevidade = longevidadeArray[aromas_longevidade[cont]];
            projecao = projecaoArray[aromas_projecao[cont]];
            genero = generoArray[aromas_genero[cont]];

            aroma = new Aroma(aromas_nome[cont],
                    favoritos,
                    longevidade,
                    projecao,
                    genero,
                    aromas_indicacao[cont],
                    aromas_tipo_de_aroma[cont],
                    aromas_piramide_olfativa_saida[cont],
                    aromas_piramide_olfativa_corpo[cont],
                    aromas_piramide_olfativa_fundo[cont]);

            listaAromas.add(aroma);
        }
        */


        /*
        ArrayAdapter<Aroma> adapter = new ArrayAdapter<>(this,

                                                            android.R.layout.simple_list_item_1,
                                                            listaAromas);

        listViewAromas.setAdapter(adapter);
         */

        adapterAroma = new AromaAdapter(this, listaAromas);
        listViewAromas.setAdapter(adapterAroma);

    }
    public void abrirSobre(){
        Intent intentAbertura = new Intent(this, SobreActivity.class);
        //se quiser passar parâmetros
        //intentAbertura.putExtra()
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.aroma_item_selecionado, menu);

    }

    private void  excluirAroma(int posicao){
        listaAromas.remove(posicao);
        adapterAroma.notifyDataSetChanged();
    }

    private void editarAroma(int posicao){

        posicaoSelecionada = posicao;

        Aroma aromaEditar = listaAromas.get(posicaoSelecionada);
        Intent intentAbertura =  new Intent(this, AromaLibraryActivity.class);
        intentAbertura.putExtra(AromaLibraryActivity.KEY_MODO, AromaLibraryActivity.MODO_EDITAR);
        intentAbertura.putExtra(AromaLibraryActivity.KEY_AROMA ,aromaEditar.getNome());
        intentAbertura.putExtra(AromaLibraryActivity.KEY_FAVORITO ,aromaEditar.isFavoritos());
        intentAbertura.putExtra(AromaLibraryActivity.KEY_LONGEVIDADE ,aromaEditar.getLongevidade());
        intentAbertura.putExtra(AromaLibraryActivity.KEY_PROJECAO ,aromaEditar.getProjecao());
        intentAbertura.putExtra(AromaLibraryActivity.KEY_GENERO ,aromaEditar.getGenero());
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

                            //Aroma aromaNovo = new Aroma(aromaNome, favorito,Longevidade.valueOf(longevidade),Projecao.valueOf(projecao),
                            //        Genero.valueOf(genero),indicacaoAroma, tipoAroma, notaDeSaida, notaDeBase, notaDeFundo) ;

                            //listaAromas.add(aromaNovo);
                            adapterAroma.notifyDataSetChanged();

                        }
                    }
                }
            });
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int idMenuItem = item.getItemId();
        if (idMenuItem == R.id.menuItemEditar){
            //ver na AromaLibraryActivity --> criamos o suporte para dois modos(dcadastro e edição)
            //VER KEY_MODO e abrirAdicionar
            editarAroma(info.position);
            return true;
        } else if (idMenuItem == R.id.menuItemExcluir){
            excluirAroma(info.position);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }
}