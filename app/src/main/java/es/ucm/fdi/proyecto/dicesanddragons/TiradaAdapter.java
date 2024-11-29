package es.ucm.fdi.proyecto.dicesanddragons;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TiradaAdapter extends RecyclerView.Adapter<TiradaAdapter.TiradaViewHolder> {

    private List<Tirada> listaTiradas;

    public TiradaAdapter(List<Tirada> listaTiradas) {
        this.listaTiradas = listaTiradas;
    }

    @NonNull
    @Override
    public TiradaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tirada, parent, false);
        return new TiradaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TiradaViewHolder holder, int position) {
        Tirada tirada = listaTiradas.get(position);

        // Asignar valores a los campos
        holder.tiradaNombre.setText("Tirada: " + tirada.getNombre());
        holder.cantidadDados.setText("Cantidad de dados: " + tirada.getNumDados());
        holder.numeroCaras.setText("Número de caras: " + tirada.getNumCaras());
        holder.resultado.setText("Resultado: " + tirada.getResultado());
    }

    @Override
    public int getItemCount() {
        return listaTiradas.size();
    }

    public static class TiradaViewHolder extends RecyclerView.ViewHolder {
        TextView tiradaNombre;
        TextView cantidadDados;
        TextView numeroCaras;
        TextView resultado;

        public TiradaViewHolder(View itemView) {
            super(itemView);

            // Inicializar vistas
            tiradaNombre = itemView.findViewById(R.id.tiradaNombre);
            cantidadDados = itemView.findViewById(R.id.tiradaCantidadDados);
            numeroCaras = itemView.findViewById(R.id.tiradaNumeroCaras);
            resultado = itemView.findViewById(R.id.tiradaResultado);
        }
    }
}
