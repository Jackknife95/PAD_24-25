package es.ucm.fdi.proyecto.dicesanddragons;

import android.app.AlertDialog;
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

    private List<Counter> counters;
    private OnCounterListener listener;

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
            counter.increment();
            holder.etCounterValue.setText(String.valueOf(counter.getValue()));
            notifyItemChanged(position);
        });

        // Decrementar el valor
        holder.btnDecrement.setOnClickListener(v -> {
            counter.decrement();
            holder.etCounterValue.setText(String.valueOf(counter.getValue()));
            notifyItemChanged(position);
        });

        // Eliminar el contador
        holder.btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle(v.getContext().getString(R.string.borrar_contador) + counter.getName());
            builder.setMessage(v.getContext().getString(R.string.deseas_borrar) + counter.getName() + "?");

            builder.setPositiveButton(v.getContext().getString(R.string.si), (dialog, which) -> {
                listener.onDeleteCounter(position);
                dialog.dismiss();
            });

            builder.setNegativeButton(v.getContext().getString(R.string.no), (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });

        // Cambiar el nombre del contador
        holder.etCounterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                counter.setName(s.toString());
            }
        });

        // Cambiar manualmente el valor del contador
        holder.etCounterValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    try {
                        int value = Integer.parseInt(s.toString());
                        counter.setValue(value);
                    } catch (NumberFormatException e) {
                        holder.etCounterValue.setError(holder.itemView.getContext().getString(R.string.invalid_number));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return counters.size();
    }

    public void setCounterList(List<Counter> counterList) {
        this.counters = counterList;
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

    public interface OnCounterListener {
        void onDeleteCounter(int position);
    }

    public void setOnCounterClickListener(OnCounterListener listener) {
        this.listener = listener;
    }
}
