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


        CounterAdapter adapter = new CounterAdapter(counterList);

// Configura el listener después de inicializar el adapter
        adapter.setOnCounterClickListener(position -> {
            counterList.remove(position);
            adapter.notifyDataSetChanged();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

// Agregar contadores a la lista
        for (int i=1;i<=10;i++){
            counterList.add(new Counter(i,"Contador "+i ));
        }
        adapter.notifyDataSetChanged();

    }

}
