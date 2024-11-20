package es.ucm.fdi.proyecto.dicesanddragons;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class CounterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter_activity);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        List<Counter> counterList = new ArrayList<>();

        CounterAdapter adapter = new CounterAdapter(counterList, position -> {
            // Eliminar el contador en la posición dada
            counterList.remove(position);

            //solucionar para que puedan ser borrados los contadores
            //adapter.notifyItemRemoved(position);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

// Agregar contadores a la lista
        counterList.add(new Counter(0, "Contador 1"));
        counterList.add(new Counter(0,"Contador 2" ));
        adapter.notifyDataSetChanged();

    }

}
