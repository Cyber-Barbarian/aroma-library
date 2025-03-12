package br.edu.utfpr.rafaelproenca.aroma_library.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.utfpr.rafaelproenca.aroma_library.modelo.Aroma;

@Database(entities = {Aroma.class}, version = 1, exportSchema = false)
public abstract class AromasDatabase extends RoomDatabase {

    public abstract AromaDao getAromaDao();

    //padrão singleton -> só um objeto criado por vez

    private static AromasDatabase INSTANCE;
    public static AromasDatabase getInstance(final Context context){
        if (INSTANCE == null){
            synchronized (AromasDatabase.class){
                if (INSTANCE == null){

                    INSTANCE = Room.databaseBuilder(context, AromasDatabase.class, "aroma_library.db").allowMainThreadQueries().build();//allowMainThreadQueries -> não usar em um projeto real

                }
            }
        }
        return INSTANCE;
    }

}
