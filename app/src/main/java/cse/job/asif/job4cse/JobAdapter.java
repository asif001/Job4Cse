package cse.job.asif.job4cse;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.myViewHolder> {

    private ArrayList<JobDetails> JobList;

    private OnBottomReachedListener onBottomReachedListener;

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

        if(position == JobList.size() - 1){
            onBottomReachedListener.onBottomReached(position);
        }

        JobDetails jobDetails = JobList.get(position);

        myViewHolder.compTitle.setText(jobDetails.getTitle());
        myViewHolder.compName.setText(jobDetails.getCompname());
        myViewHolder.compLocation.setText("Location : " + jobDetails.getLocation());
        myViewHolder.compExp.setText("Exp : " + jobDetails.getExp());
        myViewHolder.compDead.setText("DeadLine : " + jobDetails.getDeadline());

        myViewHolder.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clicked");
            }
        });

        String temp = "";
        Integer count = 1;

        for(String string : jobDetails.getQualifications()){

            temp = temp + "\n" + count.toString() + ". " + string;
            count = count + 1;

        }

        myViewHolder.qualifications.setText(temp);

    }

    @Override
    public int getItemCount() {
        return JobList.size();
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        this.onBottomReachedListener = onBottomReachedListener;

    }


    public class myViewHolder extends RecyclerView.ViewHolder{

        public TextView compName;
        public TextView compTitle;
        public TextView compLocation;
        public TextView compExp;
        public TextView compDead;
        public TextView qualifications;
        public Button buttonView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            compName = (TextView) itemView.findViewById(R.id.compName);
            compTitle = (TextView) itemView.findViewById(R.id.compTitle);
            compLocation = (TextView) itemView.findViewById(R.id.compLocation);
            compExp = (TextView) itemView.findViewById(R.id.compExp);
            compDead = (TextView) itemView.findViewById(R.id.compDead);
            qualifications = (TextView) itemView.findViewById(R.id.compQualifications);
            buttonView = (Button) itemView.findViewById(R.id.buttonView);

        }
    }

}
