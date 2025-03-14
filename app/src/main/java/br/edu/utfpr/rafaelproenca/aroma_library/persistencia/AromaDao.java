package br.edu.utfpr.rafaelproenca.aroma_library.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.rafaelproenca.aroma_library.modelo.Aroma;

@Dao
public interface AromaDao {

    //criando os nomes dos métodos ->< definiimos o comportamento com as anotações e o Room gera o código automaticamente

    @Insert
    long insert(Aroma aroma); //retornar id

    @Delete
    int delete(Aroma aroma); // retornar o numero de linhas deletadas

    @Update
    int update(Aroma aroma); // retornar o numero de linhas do updade

    //Somente nos selects devemos estabelecer a sintaxe, pois são baseados em cláusulas
    @Query("SELECT * FROM aroma WHERE ID =:id")
    Aroma queryForId(long id);
    @Query("SELECT * FROM aroma ORDER BY nome ASC")
    List<Aroma> queryAllAscending();
    @Query("SELECT * FROM aroma ORDER BY nome DESC")
    List<Aroma> queryAllDescending();

}
