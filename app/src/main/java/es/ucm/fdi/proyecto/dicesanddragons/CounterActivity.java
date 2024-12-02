package es.ucm.fdi.proyecto.dicesanddragons;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.proyecto.dicesanddragons.SavedGame.SavedGame;
import es.ucm.fdi.proyecto.dicesanddragons.SavedGame.SessionManager;

public class CounterActivity extends AppCompatActivity {
    private List<Counter> counterList= new ArrayList<>();
    private CounterAdapter adapter;
    private static final String TAG = "CounterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter_activity);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        Button agregar = findViewById(R.id.btn_add_counter);
        Button volver = findViewById(R.id.btn_back);

        // Restaura o inicializa la lista de contadores
        if (savedInstanceState != null) {
            counterList = (List<Counter>) savedInstanceState.getSerializable("counter_list");
            if (counterList == null) {
                Log.d(TAG, "No se encontraron contadores, se crea lista vacía");
                counterList = new ArrayList<>();
            }
        } else if (SessionManager.getInstance().getCurrentSession().getContadores().isEmpty()) {
            counterList = new ArrayList<>();
            counterList.add(new Counter(0, getString(R.string.monedas_cobre)));
            counterList.add(new Counter(0, getString(R.string.monedas_plata)));
            counterList.add(new Counter(0, getString(R.string.monedas_oro)));
            counterList.add(new Counter(0, getString(R.string.monedas_esmeralda)));
            counterList.add(new Counter(0, getString(R.string.monedas_platino)));
            Log.d(TAG, "Contadores inicializados con valores predeterminados");
        } else {
            counterList.addAll(SessionManager.getInstance().getCurrentSession().getContadores());
            Log.d(TAG, "Contadores restaurados desde la sesión actual");
        }

        // Configura el adaptador
        adapter = new CounterAdapter(counterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Configura el clic en el botón de agregar
        agregar.setOnClickListener(v -> {
            counterList.add(new Counter(0));
            adapter.notifyItemInserted(counterList.size() - 1);
        });

        // Configura el clic en el botón de volver
        volver.setOnClickListener(v -> {
            SavedGame curr = SessionManager.getInstance().getCurrentSession();
            curr.setContadores(counterList);
            SessionManager.getInstance().setCurrentSession(curr);
            SessionManager.getInstance().guardarPartida(this);
            finish();
        });

        // Configura el clic en los elementos del adaptador
        adapter.setOnCounterClickListener(position -> {
            if (position >= 0 && position < counterList.size()) {
                counterList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, counterList.size());
            } else {
                Log.e("CounterActivity", "Invalid position: " + position);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("counter_list", new ArrayList<>(counterList));
    }

    @Override
    protected void onPause() {

        super.onPause();
        SavedGame curr;
        curr=SessionManager.getInstance().getCurrentSession();
        curr.setContadores(counterList);
        SessionManager.getInstance().setCurrentSession(curr);
        SessionManager.getInstance().guardarPartida(this);
    }

}
