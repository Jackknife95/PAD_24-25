package es.ucm.fdi.proyecto.dicesanddragons;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.proyecto.dicesanddragons.SavedGame.SessionManager;

public class CounterActivity extends AppCompatActivity {
    private List<Counter> counterList;
    private CounterAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter_activity);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        Button agregar = findViewById(R.id.btn_add_counter);

        // Inicializa la lista de contadores
        if (savedInstanceState != null) {
            // Restaura los contadores guardados
            counterList = (List<Counter>) savedInstanceState.getSerializable("counter_list");
        } else if(SessionManager.getInstance().getCurrentSession().getContadores().isEmpty()) {
            counterList = new ArrayList<>();
            String nombre = this.getString(R.string.monedas_cobre);
            counterList.add(new Counter(0, nombre));

            nombre = this.getString(R.string.monedas_plata);
            counterList.add(new Counter(0, nombre));
            nombre = this.getString(R.string.monedas_oro);
            counterList.add(new Counter(0, nombre));
            nombre = this.getString(R.string.monedas_esmeralda);
            counterList.add(new Counter(0, nombre));
            nombre = this.getString(R.string.monedas_platino);
            counterList.add(new Counter(0, nombre));
        }

        // Configura el adaptador
        adapter = new CounterAdapter(counterList);
        adapter.setOnCounterClickListener(position -> {
            counterList.remove(position);
            adapter.notifyItemRemoved(position);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Agregar nuevos contadores
        agregar.setOnClickListener(v -> {
            counterList.add(new Counter(0));
            adapter.notifyItemInserted(counterList.size()-1);
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Guarda la lista de contadores
        outState.putSerializable("counter_list", new ArrayList<>(counterList));
    }
}
