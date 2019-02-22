package ru.uxapps.counterlessons;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CounterList {

    private final CounterAdapter mAdapter;

    public CounterList(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        mAdapter = new CounterAdapter();
        rv.setAdapter(mAdapter);
    }

    public void setCounters(List<Counter> list) {
        mAdapter.setData(list);
    }

    static class CounterAdapter extends RecyclerView.Adapter<CounterAdapter.Vh> {

        private List<Counter> mData;

        void setData(List<Counter> data) {
            mData = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public Vh onCreateViewHolder(@NonNull ViewGroup parent, int type) {
            return new Vh(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull Vh vh, int position) {
            vh.bind(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class Vh extends RecyclerView.ViewHolder {

            private final TextView mName;
            private final TextView mValue;

            Vh(ViewGroup parent) {
                super(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.i_counter, parent, false));
                mName = itemView.findViewById(R.id.i_counter_name);
                mValue = itemView.findViewById(R.id.i_counter_value);
            }

            void bind(Counter counter) {
                mName.setText(counter.name);
                mValue.setText(String.valueOf(counter.value));
            }
        }

    }

}
