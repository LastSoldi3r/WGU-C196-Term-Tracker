package wgu.c196application.termtracker.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import wgu.c196application.termtracker.R;
import wgu.c196application.termtracker.model.TermEntity;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder{
        private final TextView termItem1;
        private final TextView termItem2;
        private final TextView termItem3;

        private TermViewHolder(View itemView) {
            super (itemView);
            termItem1 = itemView.findViewById(R.id.termTextView1);
            termItem2 = itemView.findViewById(R.id.termTextView2);
            termItem3 = itemView.findViewById(R.id.termTextView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final TermEntity current = terms.get(position);
                    Intent intent = new Intent(context, CourseActivity.class);
                    intent.putExtra("id", current.getTermID());
                    intent.putExtra("title", current.getTermTitle());
                    intent.putExtra("start_date", current.getTermStartDate());
                    intent.putExtra("end_date", current.getTermEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<TermEntity> terms;
    private final Context context;
    private final LayoutInflater inflater;

    public TermAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.term_list_item, parent, false);

        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if (terms != null) {
            final TermEntity current = terms.get(position);
            holder.termItem1.setText(current.getTermTitle());
            holder.termItem2.setText(current.getTermStartDate());
            holder.termItem3.setText(current.getTermEndDate());
        } else {
            holder.termItem1.setText("No Term Title");
            holder.termItem2.setText("No Start Date");
            holder.termItem3.setText("No End Date");
        }
    }

    public void setTerms(List<TermEntity> termsToSet) {
        terms = termsToSet;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }
}
