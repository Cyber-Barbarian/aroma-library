package br.edu.utfpr.rafaelproenca.aroma_library.modelo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;
import java.util.Objects;

import br.edu.utfpr.rafaelproenca.aroma_library.modelo.enums.Genero;
import br.edu.utfpr.rafaelproenca.aroma_library.modelo.enums.Longevidade;
import br.edu.utfpr.rafaelproenca.aroma_library.modelo.enums.Projecao;

@Entity()
public class Aroma {

    public static Comparator<Aroma> ordenacaoCrescente = new Comparator<Aroma>() {
        @Override
        public int compare(Aroma aroma1, Aroma aroma2) {
            return aroma1.getNome().compareToIgnoreCase(aroma2.getNome());
        }
    };
    public static Comparator<Aroma> ordenacaoDecrescente = new Comparator<Aroma>() {
        @Override
        public int compare(Aroma aroma1, Aroma aroma2) {
            return -1 * aroma1.getNome().compareToIgnoreCase(aroma2.getNome());
        }
    };

    // por padrão o room ja persiste todos os atributos não estáticos
    //criaremos atributo para chave primária
    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(index = true) //indexar o campo nome melhora a performance da busca por nme
    private String nome;
    private boolean favoritos;
    @NonNull
    private Longevidade longevidade;
    @NonNull
    private Projecao projecao;
    @NonNull
    private Genero genero;
    @NonNull
    private String indicacao;
    @NonNull
    private String tipoDeAroma;
    //tipos contruídos como String e Enum podem vir nulos, só pra garantir que os obrigatórios nunca venham nulos usamos a anotação @NonNull
    //@Nullable para os não obrigatórios
    @Nullable
    private String piramideOlfativaSaida;
    @Nullable
    private String piramideOlfativaCorpo;
    @Nullable
    private String piramideOlfativaFundo;



    public Aroma(String nome, boolean favoritos, Longevidade longevidade, Projecao projecao, Genero genero, String indicacao, String tipoDeAroma, String piramideOlfativaSaida, String piramideOlfativaCorpo, String piramideOlfativaFundo) {
        this.nome = nome;
        this.favoritos = favoritos;
        this.longevidade = longevidade;
        this.projecao = projecao;
        this.genero = genero;
        this.indicacao = indicacao;
        this.tipoDeAroma = tipoDeAroma;
        this.piramideOlfativaSaida = piramideOlfativaSaida;
        this.piramideOlfativaCorpo = piramideOlfativaCorpo;
        this.piramideOlfativaFundo = piramideOlfativaFundo;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    /*public boolean isFavoritos() {
        return favoritos;
    }

     */

    public boolean getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(boolean favoritos) {
        this.favoritos = favoritos;
    }

    public Longevidade getLongevidade() {
        return longevidade;
    }

    public void setLongevidade(Longevidade longevidade) {
        this.longevidade = longevidade;
    }

    public Projecao getProjecao() {
        return projecao;
    }

    public void setProjecao(Projecao projecao) {
        this.projecao = projecao;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getIndicacao() {
        return indicacao;
    }

    public void setIndicacao(String indicacao) {
        this.indicacao = indicacao;
    }

    public String getTipoDeAroma() {
        return tipoDeAroma;
    }

    public void setTipoDeAroma(String tipoDeAroma) {
        this.tipoDeAroma = tipoDeAroma;
    }

    public String getPiramideOlfativaSaida() {
        return piramideOlfativaSaida;
    }

    public void setPiramideOlfativaSaida(String piramideOlfativaSaida) {
        this.piramideOlfativaSaida = piramideOlfativaSaida;
    }

    public String getPiramideOlfativaCorpo() {
        return piramideOlfativaCorpo;
    }

    public void setPiramideOlfativaCorpo(String piramideOlfativaCorpo) {
        this.piramideOlfativaCorpo = piramideOlfativaCorpo;
    }

    public String getPiramideOlfativaFundo() {
        return piramideOlfativaFundo;
    }

    public void setPiramideOlfativaFundo(String piramideOlfativaFundo) {
        this.piramideOlfativaFundo = piramideOlfativaFundo;
    }

    @Override
    public String toString() {
        return
                nome + "\n" +
                        favoritos + "\n" +
                        longevidade + "\n" +
                        projecao + "\n" +
                        genero + "\n" +
                        indicacao + "\n" +
                        tipoDeAroma + "\n" +
                        piramideOlfativaSaida + "\n" +
                        piramideOlfativaCorpo + "\n" +
                        piramideOlfativaFundo
                ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aroma aroma = (Aroma) o;
        return favoritos == aroma.favoritos &&
                nome.equals(aroma.nome)
                && longevidade == aroma.longevidade && projecao == aroma.projecao && genero == aroma.genero &&
                indicacao.equals(aroma.indicacao) && tipoDeAroma.equals(aroma.tipoDeAroma) &&
                piramideOlfativaSaida.equals(aroma.piramideOlfativaSaida) &&
                piramideOlfativaCorpo.equals(aroma.piramideOlfativaCorpo) &&
                piramideOlfativaFundo.equals(aroma.piramideOlfativaFundo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favoritos, longevidade, projecao, genero, indicacao, tipoDeAroma, piramideOlfativaSaida, piramideOlfativaCorpo, piramideOlfativaFundo);
    }
}
