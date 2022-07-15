package com.example.blooddonor.staff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.blooddonor.R;
import com.example.blooddonor.adapter.DonorFragmentStateAdapter;
import com.example.blooddonor.staff.donor.ApproveFragment;
import com.example.blooddonor.staff.donor.DisapproveFragment;
import com.example.blooddonor.staff.donor.PendingFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class DonorListActivity extends AppCompatActivity implements TabLayoutMediator.TabConfigurationStrategy {

    private ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);
        addFragment();
    }

    private void addFragment() {
        TabLayout tabLayout = findViewById(R.id.tabLayoutDonor);
        ViewPager2 viewPager = findViewById(R.id.viewPagerDonor);

        titles = new ArrayList<>();
        titles.add("Pending");
        titles.add("Approve");
        titles.add("Disapprove");

        // setup viewpager2adapter
        DonorFragmentStateAdapter donorFragmentStateAdapter = new DonorFragmentStateAdapter(this);
        ArrayList<Fragment> fragments = new ArrayList<>();

        // add fragments to arraylist
        fragments.add(new PendingFragment());
        fragments.add(new ApproveFragment());
        fragments.add(new DisapproveFragment());

        donorFragmentStateAdapter.setFragmentArrayList(fragments);
        viewPager.setAdapter(donorFragmentStateAdapter);

        new TabLayoutMediator(tabLayout, viewPager, this).attach();

    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        tab.setText(titles.get(position));
    }
}