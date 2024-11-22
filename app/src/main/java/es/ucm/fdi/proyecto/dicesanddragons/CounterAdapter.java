package es.ucm.fdi.proyecto.dicesanddragons;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CounterAdapter extends RecyclerView.Adapter<CounterAdapter.CounterViewHolder> {

    private final List<Counter> counters; // Lista de contadores
    private OnCounterListener listener; // Listener para eventos

    public CounterAdapter(List<Counter> counters, OnCounterListener listener) {
        this.counters = counters;
        this.listener = listener;
    }

    public CounterAdapter(List<Counter> counters) {
        this.counters = counters;
    }

    @NonNull
    @Override
    public CounterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_counter, parent, false);
        return new CounterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CounterViewHolder holder, int position) {
        Counter counter = counters.get(position);

        // Establece los valores iniciales
        holder.etCounterName.setText(counter.getName());
        holder.etCounterValue.setText(String.valueOf(counter.getValue()));

        // Incrementar el valor
        holder.btnIncrement.setOnClickListener(v -> {
            int nuevoValor = Integer.parseInt(holder.etCounterValue.getText().toString());
            counter.setValue(nuevoValor);
            counter.increment();
            notifyItemChanged(position); // Actualizar el ítem
        });

        // Decrementar el valor
        holder.btnDecrement.setOnClickListener(v -> {
            int nuevoValor = Integer.parseInt(holder.etCounterValue.getText().toString());
            counter.setValue(nuevoValor);
            counter.decrement();
            notifyItemChanged(position); // Actualizar el ítem
        });

        // Eliminar el contador
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteCounter(position));

        // Cambiar el nombre del contador
        holder.etCounterName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                counter.setName(holder.etCounterName.getText().toString());
            }
        });

        // Cambiar manualmente el valor del contador
        holder.etCounterValue.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String newValue = holder.etCounterValue.getText().toString();
                if (!newValue.isEmpty()) {
                    counter.setValue(Integer.parseInt(newValue));
                }
            }
        });

        holder.etCounterValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se necesita acción aquí
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Se actualiza el valor en tiempo real mientras el usuario escribe
                String newValue = s.toString();
                if (!newValue.isEmpty()) {
                    try {
                        counter.setValue(Integer.parseInt(newValue));
                    } catch (NumberFormatException e) {
                        // Manejar el caso de números inválidos
                        counter.setValue(0);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                // No se necesita acción aquí
            }
        });

    }

    @Override
    public int getItemCount() {
        return counters.size();
    }

    public static class CounterViewHolder extends RecyclerView.ViewHolder {
        EditText etCounterName, etCounterValue;
        Button btnIncrement, btnDecrement, btnDelete;

        public CounterViewHolder(@NonNull View itemView) {
            super(itemView);

            etCounterName = itemView.findViewById(R.id.tv_counter_name);
            etCounterValue = itemView.findViewById(R.id.tv_counter_value);
            btnIncrement = itemView.findViewById(R.id.btn_increment);
            btnDecrement = itemView.findViewById(R.id.btn_decrement);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    // Interfaz para manejar eventos como eliminar contadores
    public interface OnCounterListener {
        void onDeleteCounter(int position); // Método para eliminar el contador
    }
    public void setOnCounterClickListener(OnCounterListener listener) {
        this.listener = listener;
    }
}
