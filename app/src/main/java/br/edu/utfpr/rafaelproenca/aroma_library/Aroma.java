package br.edu.utfpr.rafaelproenca.aroma_library;

import java.util.Comparator;

import br.edu.utfpr.rafaelproenca.aroma_library.enums.Genero;
import br.edu.utfpr.rafaelproenca.aroma_library.enums.Longevidade;
import br.edu.utfpr.rafaelproenca.aroma_library.enums.Projecao;

public class Aroma {

    public static Comparator<Aroma> ordenacaoCrescente = new Comparator<Aroma>() {
        @Override
        public int compare(Aroma aroma1, Aroma aroma2) {
            return aroma1.getNome().compareToIgnoreCase(aroma2.getNome());
        }
    };
    private String nome;
    private boolean favoritos;
    private Longevidade longevidade;
    private Projecao projecao;
    private Genero genero;
    private String indicacao;
    private String tipoDeAroma;
    private String piramideOlfativaSaida;
    private String piramideOlfativaCorpo;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isFavoritos() {
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
}
