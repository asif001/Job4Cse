package cse.job.asif.job4cse.recyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cse.job.asif.job4cse.HelperClass.Interview;
import cse.job.asif.job4cse.R;

public class InterviewAdapter extends RecyclerView.Adapter<InterviewAdapter.MyViewHolder> {


    private ArrayList<Interview> interviews;

    public InterviewAdapter(ArrayList<Interview> interviews){
        this.interviews = interviews;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interview,parent,false);

        return new InterviewAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        Interview interview = interviews.get(position);

        String date = interview.getDate();
        String title = interview.getJobTitle();
        String comp = interview.getCompName();

        myViewHolder.interviewText.setText("Hi! You have been selected for " + title + " at " + comp +
                     "!!\nYour interview is on " + date);

    }

    @Override
    public int getItemCount() {
        return interviews.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView interviewText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            interviewText = itemView.findViewById(R.id.interviewText);

        }
    }

}
