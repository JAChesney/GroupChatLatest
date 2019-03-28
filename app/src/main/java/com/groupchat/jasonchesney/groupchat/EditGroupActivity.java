package com.groupchat.jasonchesney.groupchat;

import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditGroupActivity extends AppCompatActivity {
    private RecyclerView sremainlist;
    Toolbar mtoolbar;
    AppBarLayout apb;
    private EditGroupRecyclerAdapter firebasereycleradapter;

    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    private DatabaseReference rootRef, memberref, gRef, userRef, gnameRef, editRef;

    private String currentUserID, userProfileimage, currentUserName, newGroupName, randfetch, randfetch1, memtype;
    private String MEMBER_TYPE = "Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        editRef = FirebaseDatabase.getInstance().getReference().child("Admin").child("EditGroup");
        gnameRef = FirebaseDatabase.getInstance().getReference("Groups");
        currentUserID = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        rootRef = FirebaseDatabase.getInstance().getReference();

        setUpRecyclerView();

        apb = (AppBarLayout) findViewById(R.id.sappBarLayout);

        mtoolbar = (Toolbar) findViewById(R.id.smainpagetoolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#10101'><h6>Groups</h6></font>"));
    }

    private void setUpRecyclerView() {

        FirebaseRecyclerOptions<EditModel> sgoptions = new FirebaseRecyclerOptions.Builder<EditModel>()
                .setQuery(editRef, EditModel.class).build();

        firebasereycleradapter = new EditGroupRecyclerAdapter(sgoptions);

        sremainlist = (RecyclerView) findViewById(R.id.sremainlist);
        sremainlist.setHasFixedSize(true);
        sremainlist.setLayoutManager(new LinearLayoutManager(this));
        sremainlist.setAdapter(firebasereycleradapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebasereycleradapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        firebasereycleradapter.stopListening();
    }
}