package cse.job.asif.job4cse.recyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import cse.job.asif.job4cse.HelperClass.JobDetails;
import cse.job.asif.job4cse.R;
import cse.job.asif.job4cse.interfaces.OnBottomReachedListener;
import cse.job.asif.job4cse.interfaces.OnJobDeleteListener;
import cse.job.asif.job4cse.interfaces.OnJobSaveListener;
import cse.job.asif.job4cse.interfaces.OnJobisAppliedListener;
import cse.job.asif.job4cse.interfaces.onJobApplyListener;

public class JobSavedAdapter extends RecyclerView.Adapter<JobSavedAdapter.myViewHolder> {

    private ArrayList<JobDetails> JobList;

    private cse.job.asif.job4cse.interfaces.onJobViewListener onJobViewListener;

    private OnJobDeleteListener onJobDeleteListener;

    private cse.job.asif.job4cse.interfaces.onJobApplyListener onJobApplyListener;

    private cse.job.asif.job4cse.interfaces.OnJobisAppliedListener onJobisAppliedListener;

    public JobSavedAdapter(ArrayList<JobDetails> JobList){
        this.JobList = JobList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_saved,parent,false);

        return new myViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int position) {

        final JobDetails jobDetails = JobList.get(position);

        myViewHolder.compTitle.setText(jobDetails.getTitle());
        myViewHolder.compName.setText(jobDetails.getCompname());
        myViewHolder.compLocation.setText("Location : " + jobDetails.getLocation());
        myViewHolder.compExp.setText("Exp : " + jobDetails.getExp());
        myViewHolder.compDead.setText("DeadLine : " + jobDetails.getDeadline());

        String temp = "";
        Integer count = 1;

        for(String string : jobDetails.getQualifications()){

            if(!string.equals("")) {
                temp = temp + "\n" + count.toString() + ". " + string;
                count = count + 1;
            }

        }

        myViewHolder.qualifications.setText(temp);

        myViewHolder.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onJobViewListener.ViewJob(jobDetails.getJobUrl());
            }
        });

        myViewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onJobDeleteListener.onDelete(jobDetails);
            }
        });

        if(!onJobisAppliedListener.isApplied()) {

            myViewHolder.buttonApply.setVisibility(View.VISIBLE);
            myViewHolder.buttonApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onJobApplyListener.apply(jobDetails);
                }
            });
        }
        else{
            myViewHolder.buttonApply.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return JobList.size();
    }

    public void setOnJobViewListener(cse.job.asif.job4cse.interfaces.onJobViewListener onJobViewListener){

        this.onJobViewListener = onJobViewListener;

    }

    public void setOnJobDeleteListener(OnJobDeleteListener onJobDeleteListener){

        this.onJobDeleteListener = onJobDeleteListener;
    }

    public void setOnJobApplyListener(onJobApplyListener onJobApplyListener){

        this.onJobApplyListener = onJobApplyListener;

    }

    public void setOnJobisAppliedListener(OnJobisAppliedListener onJobisAppliedListener){

        this.onJobisAppliedListener = onJobisAppliedListener;

    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        public TextView compName;
        public TextView compTitle;
        public TextView compLocation;
        public TextView compExp;
        public TextView compDead;
        public TextView qualifications;
        public Button buttonView;
        public Button buttonDelete;
        public Button buttonApply;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            compName = (TextView) itemView.findViewById(R.id.SavedcompName);
            compTitle = (TextView) itemView.findViewById(R.id.SavedcompTitle);
            compLocation = (TextView) itemView.findViewById(R.id.SavedcompLocation);
            compExp = (TextView) itemView.findViewById(R.id.SavedcompExp);
            compDead = (TextView) itemView.findViewById(R.id.SavedcompDead);
            qualifications = (TextView) itemView.findViewById(R.id.SavedcompQualifications);
            buttonView = (Button) itemView.findViewById(R.id.SavedbuttonView);
            buttonDelete = (Button) itemView.findViewById(R.id.SavedbuttonDelete);
            buttonApply = (Button) itemView.findViewById(R.id.savedbuttonApply);

        }
    }

}
