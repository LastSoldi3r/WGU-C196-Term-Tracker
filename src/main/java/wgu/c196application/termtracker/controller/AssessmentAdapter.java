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
import wgu.c196application.termtracker.model.AssessmentEntity;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItem1;
        private final TextView assessmentItem2;
        private final TextView assessmentItem3;

        private AssessmentViewHolder(View itemView) {
            super (itemView);
            assessmentItem1 = itemView.findViewById(R.id.assessmentTextView1);
            assessmentItem2 = itemView.findViewById(R.id.assessmentTextView2);
            assessmentItem3 = itemView.findViewById(R.id.assessmentTextView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final AssessmentEntity current = assessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetailsActivity.class);
                    intent.putExtra("id", current.getAssessmentID());
                    intent.putExtra("title", current.getAssessmentTitle());
                    intent.putExtra("start_date", current.getAssessmentStartDate());
                    intent.putExtra("end_date", current.getAssessmentEndDate());
                    intent.putExtra("type", current.getAssessmentType());
                    intent.putExtra("course_ID", current.getCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<AssessmentEntity> assessments;
    private final Context context;
    private final LayoutInflater inflater;

    public AssessmentAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.assessment_list_item, parent, false);

        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (assessments != null) {
            final AssessmentEntity current = assessments.get(position);
            holder.assessmentItem1.setText(current.getAssessmentTitle());
            holder.assessmentItem2.setText(current.getAssessmentStartDate());
            holder.assessmentItem3.setText(current.getAssessmentEndDate());
        } else {
            holder.assessmentItem1.setText("No Assessment Title");
            holder.assessmentItem2.setText("No Start Date");
            holder.assessmentItem3.setText("No End Date");
        }
    }

    public void setAssessments(List<AssessmentEntity> assessmentsToSet) {
        assessments = assessmentsToSet;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }
}
