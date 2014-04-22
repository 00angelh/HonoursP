
package adapter;
 
import cumaps.HomeScreenFragment;
import cumaps.MapScreenFragment;
import cumaps.ModifyScreenFragment;
import cumaps.TestPageFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class TabMenu extends FragmentPagerAdapter {
 
    public TabMenu(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            return new HomeScreenFragment();
        case 1:
            return new MapScreenFragment();
        case 2:
            return new ModifyScreenFragment();
        case 3:
            return new TestPageFragment();
        }
        
 
        return null;
    }
 
    @Override
    public int getCount() {
        return 4;
    }
 
}