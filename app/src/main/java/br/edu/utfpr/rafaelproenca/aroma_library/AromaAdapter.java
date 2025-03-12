package br.edu.utfpr.rafaelproenca.aroma_library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.utfpr.rafaelproenca.aroma_library.modelo.Aroma;

public class AromaAdapter extends BaseAdapter {

    private Context context;
    private List<Aroma> listaAromas;
    private int[] arrayLongevidade;
    private int[] arrayProjecao;
    private int[] arrayGenero;
    private String[] arrayIndicacao;
    private String[] arrayTipoDeAroma;

    public AromaAdapter(Context context, List<Aroma> listaAromas) {
        this.context = context;
        this.listaAromas = listaAromas;
        arrayLongevidade = context.getResources().getIntArray(R.array.aromas_longevidade);
        arrayProjecao = context.getResources().getIntArray(R.array.aromas_projecao);
        arrayGenero = context.getResources().getIntArray(R.array.aromas_genero);
        arrayIndicacao = context.getResources().getStringArray(R.array.aromas_indicacao);
        arrayTipoDeAroma = context.getResources().getStringArray(R.array.tipos_de_aromas);
    }

    @Override
    public int getCount() {
        return listaAromas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaAromas.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AromaHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.linha_lista_aromas, parent, false);

            holder = new AromaHolder();

            holder.textViewValorNome = convertView.findViewById(R.id.textViewValorNome);
            holder.textViewValorFavorito = convertView.findViewById(R.id.textViewRotuloFavorito);
            holder.textViewValorLongevidade = convertView.findViewById(R.id.textViewValorLongevidade);
            holder.textViewValorProjecao = convertView.findViewById(R.id.textViewValorProjecao);
            holder.textViewValorGenero = convertView.findViewById(R.id.textViewValorGenero);
            holder.textViewValorIndicacao = convertView.findViewById(R.id.textViewValorIndicacao);
            holder.textViewValorTipoDeAroma = convertView.findViewById(R.id.textViewValorTipoDeAroma);
            holder.textViewValorPiramideOlfativaSaida = convertView.findViewById(R.id.textViewValorPiramideOlfativaSaida);
            holder.textViewValorPiramideOlfativaCorpo = convertView.findViewById(R.id.textViewValorPiramideOlfativaCorpo);
            holder.textViewValorPiramideOlfativaFundo = convertView.findViewById(R.id.textViewValorPiramideOlfativaFundo);

            convertView.setTag(holder);

        } else {
            holder = (AromaHolder) convertView.getTag();
        }

        Aroma aroma = listaAromas.get(position);

        holder.textViewValorNome.setText(aroma.getNome());

        if (aroma.getFavoritos()) {
            holder.textViewValorFavorito.setText(R.string.perfume_favorito);
        } else {
            holder.textViewValorFavorito.setText(R.string.empty_message);
        }

        holder.textViewValorIndicacao.setText(aroma.getIndicacao());
        holder.textViewValorTipoDeAroma.setText(aroma.getIndicacao());
        holder.textViewValorPiramideOlfativaSaida.setText(aroma.getPiramideOlfativaSaida());
        holder.textViewValorPiramideOlfativaCorpo.setText(aroma.getPiramideOlfativaCorpo());
        holder.textViewValorPiramideOlfativaFundo.setText(aroma.getPiramideOlfativaFundo());


        switch (aroma.getLongevidade()) {

            case Curta:
                holder.textViewValorLongevidade.setText(R.string.curta);
                break;
            case Media:
                holder.textViewValorLongevidade.setText(R.string.media);
                break;
            case Longa:
                holder.textViewValorLongevidade.setText(R.string.longa);
                break;

        }

        switch (aroma.getProjecao()) {

            case Fraca:
                holder.textViewValorProjecao.setText(R.string.fraca);
                break;
            case Moderada:
                holder.textViewValorProjecao.setText(R.string.moderada);
                break;
            case Forte:
                holder.textViewValorProjecao.setText(R.string.forte);
                break;

        }

        switch (aroma.getGenero()) {

            case Masculino:
                holder.textViewValorGenero.setText(R.string.radioButtonMasculino);
                break;
            case Feminino:
                holder.textViewValorGenero.setText(R.string.radioButtonFeminino);
                break;
            case Unissex:
                holder.textViewValorGenero.setText(R.string.radioButtonUnissex);
                break;

        }

        return convertView;
    }

    public static class AromaHolder {
        public TextView textViewValorNome;
        public TextView textViewValorFavorito;
        public TextView textViewValorLongevidade;
        public TextView textViewValorProjecao;
        public TextView textViewValorGenero;
        public TextView textViewValorIndicacao;
        public TextView textViewValorTipoDeAroma;
        public TextView textViewValorPiramideOlfativaSaida;
        public TextView textViewValorPiramideOlfativaCorpo;
        public TextView textViewValorPiramideOlfativaFundo;
    }
}
