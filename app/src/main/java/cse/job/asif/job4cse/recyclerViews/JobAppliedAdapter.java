package cse.job.asif.job4cse.recyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import cse.job.asif.job4cse.HelperClass.AppliedJobDetails;
import cse.job.asif.job4cse.R;
import cse.job.asif.job4cse.interfaces.OnCvListener;
import cse.job.asif.job4cse.interfaces.OnJobAcceptListener;
import cse.job.asif.job4cse.interfaces.OnJobRejectListener;

public class JobAppliedAdapter extends RecyclerView.Adapter<JobAppliedAdapter.myViewHolder> {


    private ArrayList<AppliedJobDetails> JobList;

    private OnJobAcceptListener onJobAcceptListener;
    private OnJobRejectListener onJobRejectListener;
    private OnCvListener onCvListener;

    public JobAppliedAdapter(ArrayList<AppliedJobDetails> JobList){
        this.JobList = JobList;
    }

    @NonNull
    @Override
    public JobAppliedAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appliedjob,parent,false);

        return new JobAppliedAdapter.myViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull JobAppliedAdapter.myViewHolder myViewHolder, int position) {

        final AppliedJobDetails jobDetails = JobList.get(position);

        myViewHolder.applierName.setText(jobDetails.getApplierName());
        myViewHolder.jobTitle.setText(jobDetails.getJobTitle());

        myViewHolder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onJobAcceptListener.onAccept(jobDetails);
            }
        });

        myViewHolder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onJobRejectListener.onReject(jobDetails);
            }
        });

        myViewHolder.btnCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCvListener.onCV(jobDetails);
            }
        });

    }

    @Override
    public int getItemCount() {
        return JobList.size();
    }

    public void setOnJobAcceptListener(OnJobAcceptListener onJobAcceptListener){

        this.onJobAcceptListener = onJobAcceptListener;

    }

    public void setOnJobRejectListener(OnJobRejectListener onJobRejectListener){


        this.onJobRejectListener = onJobRejectListener;

    }

    public void setOnCvListener(OnCvListener onCvListener){

        this.onCvListener = onCvListener;

    }

    public class myViewHolder extends RecyclerView.ViewHolder{

       public TextView jobTitle;
       public TextView applierName;
       public Button btnCV;
       public  Button btnAccept;
       public Button btnReject;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            jobTitle = itemView.findViewById(R.id.applyJobTitle);
            applierName = itemView.findViewById(R.id.applierName);
            btnCV = itemView.findViewById(R.id.buttonApplierCV);
            btnAccept = itemView.findViewById(R.id.buttonAccept);
            btnReject = itemView.findViewById(R.id.buttonReject);

        }
    }

}
