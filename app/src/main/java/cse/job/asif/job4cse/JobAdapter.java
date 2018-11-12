package cse.job.asif.job4cse;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.myViewHolder> {

    private ArrayList<JobDetails> JobList;

    public JobAdapter(ArrayList<JobDetails> JobList){
        this.JobList = JobList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job,parent,false);

        return new myViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {

        JobDetails jobDetails = JobList.get(position);

        myViewHolder.compName.setText(jobDetails.getCompname());

    }

    @Override
    public int getItemCount() {
        return JobList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        public TextView compName;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            compName = (TextView) itemView.findViewById(R.id.compName);
        }
    }

}
