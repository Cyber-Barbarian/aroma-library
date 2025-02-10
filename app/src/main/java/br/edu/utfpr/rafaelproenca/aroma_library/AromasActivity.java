package br.edu.utfpr.rafaelproenca.aroma_library;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AromasActivity extends AppCompatActivity {

    private ListView listViewAromas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aromas);

        listViewAromas =findViewById(R.id.listViewAromas);

        popularListaAromas();
    }
    private void popularListaAromas(){
        //https://www.youtube.com/watch?v=N-fgOdNO-5Y 27:45
    }
}