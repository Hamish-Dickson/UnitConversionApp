package uk.ac.stir.cs.yh.unitconvassignment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * manages the loading and provision of fragments to the main activity
 */
class PagerAdapter extends FragmentStatePagerAdapter {
    private final int mNumOfTabs;

    public PagerAdapter(FragmentManager fragmentManager, int numOfTabs) {
        super(fragmentManager);
        this.mNumOfTabs = numOfTabs;
    }

    /**
     * gets a fragment based on the position provided
     *
     * @param position the position of the tab
     * @return the related fragment
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PageOneFragment();
            case 1:
                return new PageTwoFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
