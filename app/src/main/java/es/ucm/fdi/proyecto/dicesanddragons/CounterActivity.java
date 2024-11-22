package es.ucm.fdi.proyecto.dicesanddragons;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        Button agregar= findViewById(R.id.btn_add_counter);

        CounterAdapter adapter = new CounterAdapter(counterList);

// Configura el listener después de inicializar el adapter
        adapter.setOnCounterClickListener(position -> {
            counterList.remove(position);
            adapter.notifyDataSetChanged();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

// Agregar contadores a la lista
        int resourceId = getResources().getIdentifier("nombre_del_contador", "string", getPackageName());
        String nombre =getString(resourceId);

            counterList.add(new Counter(0,nombre ));

        adapter.notifyDataSetChanged();

        agregar.setOnClickListener(v -> {
            counterList.add(new Counter(0));
            adapter.notifyDataSetChanged();
        });



    }

}
