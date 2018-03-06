package com.noonacademy.view.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.noonacademy.R;
import com.noonacademy.model.Subject;
import com.noonacademy.model.dbhandler.SubjectsDbHandler;
import com.noonacademy.view.adapter.SubjectsListAdapter;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


public class SubjectListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private RecyclerView recyclerView;
    private RelativeLayout relLoading;
    private TextView tv_noSubjects;
    SubjectsListAdapter subjectsListAdapter;
    RealmList<Subject> subjectList = new RealmList<Subject>();

    public SubjectListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SubjectListFragment newInstance(String param1, String param2) {
        SubjectListFragment fragment = new SubjectListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_subject_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_subjects_list);
        relLoading = (RelativeLayout) view.findViewById(R.id.rl_loading);
        tv_noSubjects = (TextView) view.findViewById(R.id.no_subject_title);
        fetchData();
        return view;
    }


    private void fetchData() {

        subjectList.clear();
        relLoading.setVisibility(View.VISIBLE);
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Subject> realmResults = SubjectsDbHandler.getInstance().getSubjects(realm);
                subjectList.addAll(realmResults.subList(0, realmResults.size()));
                if (subjectList != null && subjectList.size() > 0) {
                    relLoading.setVisibility(View.GONE);
                    tv_noSubjects.setVisibility(View.GONE);
                    setOnItemTouch();
                    setAdapter();
                } else {
                    relLoading.setVisibility(View.GONE);
                    tv_noSubjects.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private void setAdapter() {
        relLoading.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        subjectsListAdapter = new SubjectsListAdapter(this.getContext(), subjectList);
        recyclerView.setAdapter(subjectsListAdapter);
    }

    private void setOnItemTouch() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            public static final float ALPHA_FULL = 1.0f;

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;

                    Paint p = new Paint();
                    //color : right side (swiping towards left)
                    p.setARGB(255, 63, 81, 181);

                    c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                            (float) itemView.getRight(), (float) itemView.getBottom(), p);

                    // Fade out the view when it is swiped out of the parent
                    final float alpha = ALPHA_FULL - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);

                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }

            private int convertDpToPx(int dp) {
                return Math.round(dp * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition(); //swiped position

                if (direction == ItemTouchHelper.LEFT) { //swipe left

                    Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            SubjectsDbHandler.getInstance().deleteSubject(subjectList.get(position),realm);
                        }
                    });

                    subjectList.remove(position);
                    subjectsListAdapter.notifyItemRemoved(position);


                    Toast.makeText(getContext(), "Subject Deleted", Toast.LENGTH_SHORT).show();

                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
