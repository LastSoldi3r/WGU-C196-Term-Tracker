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
import wgu.c196application.termtracker.model.CourseEntity;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItem1;
        private final TextView courseItem2;
        private final TextView courseItem3;

        private  CourseViewHolder(View itemView) {
            super (itemView);
            courseItem1 = itemView.findViewById(R.id.courseTextView1);
            courseItem2 = itemView.findViewById(R.id.courseTextView2);
            courseItem3 = itemView.findViewById(R.id.courseTextView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final CourseEntity current = courses.get(position);
                    Intent intent = new Intent(context, AssessmentActivity.class);
                    intent.putExtra("id", current.getCourseID());
                    intent.putExtra("title", current.getCourseTitle());
                    intent.putExtra("start_date", current.getCourseStartDate());
                    intent.putExtra("end_date", current.getCourseEndDate());
                    intent.putExtra("status", current.getCourseStatus());
                    intent.putExtra("instructor_name", current.getCourseInstructorName());
                    intent.putExtra("instructor_phone", current.getCourseInstructorPhone());
                    intent.putExtra("instructor_email", current.getCourseInstructorEmail());
                    intent.putExtra("note", current.getCourseNote());
                    intent.putExtra("term_ID", current.getTermID());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<CourseEntity> courses;
    private final Context context;
    private final LayoutInflater inflater;

    public CourseAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.course_list_item, parent, false);

        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (courses != null) {
            final CourseEntity current = courses.get(position);
            holder.courseItem1.setText(current.getCourseTitle());
            holder.courseItem2.setText(current.getCourseStartDate());
            holder.courseItem3.setText(current.getCourseEndDate());
        } else {
            holder.courseItem1.setText("No Course Title");
            holder.courseItem2.setText("No Start Date");
            holder.courseItem3.setText("No End Date");
        }
    }

    public void setCourses(List<CourseEntity> coursesToSet) {
        courses = coursesToSet;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}
