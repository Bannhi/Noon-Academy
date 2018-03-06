package com.noonacademy.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noonacademy.R;
import com.noonacademy.model.Subject;
import com.noonacademy.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import io.realm.RealmList;

/**
 * Created by bannhi on 24/2/18.
 */

public class SubjectsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    private RealmList<Subject> subjectArrayList;
    public SubjectsListAdapter() {

    }

    public SubjectsListAdapter(Context context, RealmList<Subject> subjectArrayList) {
        this.context = context;
        this.subjectArrayList = subjectArrayList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.subject_item, null);
        return new SubjectsListAdapter.SubjectItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final int holderPosition = position;
        if (holder instanceof SubjectsListAdapter.SubjectItemHolder) {
            //display data
            System.out.println("IMAGE URL: "+subjectArrayList.get(holderPosition).getSubjectIconUrl());
            ((SubjectItemHolder) holder).tv_subject_name
                    .setText(subjectArrayList.get(holderPosition).getSubjectName());
            ((SubjectItemHolder) holder).tv_subject_desc
                    .setText(subjectArrayList.get(holderPosition).getSubjectDescription());
            ((SubjectItemHolder) holder).subject_main_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(((SubjectItemHolder) holder).tv_subject_desc.getVisibility()==View.VISIBLE){
                        ((SubjectItemHolder) holder).tv_subject_desc.setVisibility(View.GONE);
                    }else{
                        ((SubjectItemHolder) holder).tv_subject_desc.setVisibility(View.VISIBLE);
                    }

                }
            });
            if(subjectArrayList.get(holderPosition).getImgUri()!=null &&
                    !subjectArrayList.get(holderPosition).getImgUri().isEmpty() ){
                System.out.println("POS"+position+subjectArrayList.get(holderPosition).getImgUri());
                Picasso.with(context)
                        .load(Uri.parse(subjectArrayList.get(holderPosition).getImgUri()))
                        .transform(new CircleTransform())
                        .into(((SubjectItemHolder) holder).img_subject);
            }else if (subjectArrayList.get(holderPosition).getSubjectIconUrl()!=null &&
                    !subjectArrayList.get(holderPosition).getSubjectIconUrl().isEmpty()){
                System.out.println("POS"+position+subjectArrayList.get(holderPosition).getSubjectIconUrl());
                System.out.println("PATH:"+position+subjectArrayList.get(holderPosition).getSubjectIconUrl());
                Picasso.with(context)
                        .load("file://"+subjectArrayList.get(holderPosition).getSubjectIconUrl())
                        .transform(new CircleTransform())
                        .into(((SubjectItemHolder) holder).img_subject);
            }




        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if(subjectArrayList!=null){
            return subjectArrayList.size();
        }
        else return 0;

    }

    public void removeItem(int position) {
        subjectArrayList.remove(position);
        notifyItemRemoved(position);
    }
    private class SubjectItemHolder extends RecyclerView.ViewHolder {

        TextView tv_subject_name, tv_subject_desc;
        ImageView img_subject;
        LinearLayout subject_main_ll;
        public SubjectItemHolder(View itemView) {
            super(itemView);
            tv_subject_name = itemView.findViewById(R.id.tv_subject_name);
            tv_subject_desc = itemView.findViewById(R.id.tv_subject_desc);
            img_subject =     itemView.findViewById(R.id.img_subject);
            subject_main_ll = itemView.findViewById(R.id.subject_main_ll);

        }
    }

}