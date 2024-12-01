package es.ucm.fdi.proyecto.dicesanddragons;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.ucm.fdi.proyecto.dicesanddragons.SavedGame.SavedGame;
import es.ucm.fdi.proyecto.dicesanddragons.SavedGame.SessionManager;

public class DiceRegisterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TiradaAdapter tiradaAdapter;
    private SavedGame game;
    private List<Tirada> tiradas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dice_register_activity);

        recyclerView = findViewById(R.id.listaTiradas);
        Button btnLimpiarRegistro = findViewById(R.id.btnLimpiarRegistro);


        SessionManager sm =SessionManager.getInstance();
        game = sm.getCurrentSession(); // Asumiendo que tienes un SessionManager para obtener las tiradas
        tiradas=game.getTiradas();
        // Configurar el RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tiradaAdapter = new TiradaAdapter(tiradas);
        recyclerView.setAdapter(tiradaAdapter);

        // Acción para limpiar el registro
        btnLimpiarRegistro.setOnClickListener(view -> {
            // Limpiar el registro
            game.removeTiradas();
            sm.setCurrentSession(game);
            sm.guardarPartida(this);
            tiradaAdapter.notifyDataSetChanged();
            // Aquí podrías llamar a un método en SessionManager para limpiar el registro de las tiradas
        });
    }
}
