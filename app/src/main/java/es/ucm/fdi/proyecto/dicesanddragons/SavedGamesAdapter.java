package es.ucm.fdi.proyecto.dicesanddragons;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import java.util.List;

import es.ucm.fdi.proyecto.dicesanddragons.SavedGame.SavedGame;
import es.ucm.fdi.proyecto.dicesanddragons.SavedGame.SessionManager;

public class SavedGamesAdapter extends RecyclerView.Adapter<SavedGamesAdapter.ViewHolder> {

    private final List<SavedGame> listaPartidas;
    private final Context context;

    // Constructor
    public SavedGamesAdapter(Context context, List<SavedGame> listaPartidas) {
        this.context = context;
        this.listaPartidas = listaPartidas;
    }

    // Clase interna para ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombrePartida, nombrePersonaje;
        Button seleccionarPartida;

        public ViewHolder(View view) {
            super(view);
            nombrePartida = view.findViewById(R.id.nombrePartida);
            nombrePersonaje = view.findViewById(R.id.nombrePersonaje);
            seleccionarPartida = view.findViewById(R.id.seleccionarPartida);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_session_saved_game_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SavedGame partida = listaPartidas.get(position);

        // Configurar los datos en la tarjeta
        holder.nombrePartida.setText(partida.getNombrePartida());
        holder.nombrePersonaje.setText(partida.getNombrePersonaje());

        holder.seleccionarPartida.setOnClickListener(v -> {
            // Actualizar la sesión activa
            SessionManager.getInstance().setCurrentSession(partida);

            // Crear un Intent para redirigir al MainActivity
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaPartidas.size();
    }
}