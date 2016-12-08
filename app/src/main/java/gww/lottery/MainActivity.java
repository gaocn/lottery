package gww.lottery;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gww.lottery.activities.base.BaseActivity;
import gww.lottery.adapter.FragmentAdapter;
import gww.lottery.fragments.FuLiFragment;
import gww.lottery.fragments.TuiJianFragment;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.tab)
    TabLayout mTab;
/*    @BindView(R.id.title_bar)
    TitleBar mTitleBar;*/
    @BindView(R.id.vPager)
    ViewPager mVPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
    }


    private void initData() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TuiJianFragment());
        fragments.add(new FuLiFragment());
        fragments.add(new FuLiFragment());
        fragments.add(new FuLiFragment());
        fragments.add(new FuLiFragment());
        List<String> titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("视频");
        titles.add("福利");
        titles.add("发现");
        titles.add("更多");

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mVPager.setAdapter(adapter);
        mTab.setupWithViewPager(mVPager);
        for(int i = 0; i < mTab.getTabCount(); i++) {
            TabLayout.Tab tab = mTab.getTabAt(i);
            Drawable draw = null;
            switch (i) {
                case 0:
                    draw = getResources().getDrawable(R.drawable.selector_tuijian);
                    break;
                case 1:
                    draw = getResources().getDrawable(R.drawable.selector_tongji);
                    break;
                case 2:
                    draw = getResources().getDrawable(R.drawable.selector_fuli);
                    break;
                case 3:
                    draw = getResources().getDrawable(R.drawable.selector_faxian);
                    break;
                case 4:
                    draw = getResources().getDrawable(R.drawable.selector_more);
                    break;
            }
            tab.setIcon(draw);
        }
    }

}
