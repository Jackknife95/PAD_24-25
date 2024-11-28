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
        holder.nombreDado.setText("Dado: " + tirada.getNombreDado());
        holder.resultado.setText("Resultado: " + tirada.getResultado());
    }

    @Override
    public int getItemCount() {
        return listaTiradas.size();
    }

    public static class TiradaViewHolder extends RecyclerView.ViewHolder {
        TextView nombreDado;
        TextView resultado;

        public TiradaViewHolder(View itemView) {
            super(itemView);
            nombreDado = itemView.findViewById(R.id.tiradaNombreDado);
            resultado = itemView.findViewById(R.id.tiradaResultado);
        }
    }
}

