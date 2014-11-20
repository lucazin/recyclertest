package mioriticus.ro.recyclertest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    protected List<String> mDataset;

    private int mPageSize = 10;
    private int mCurrentMultiplier = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> initialDataset = initDataset();
        Collections.reverse(initialDataset);

        mDataset = initialDataset;

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int firstPos = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (firstPos == 0) {
                    mRecyclerView.stopScroll();
                    getNextPage();
                } else {
                    super.onScrolled(recyclerView, dx, dy);
                }
            }
        });
    }

    private void getNextPage() {
        List<String> firstPage = getMoreData();
        Collections.reverse(firstPage);
        for (String item : firstPage) {
            mDataset.add(0, item);
        }

        mAdapter.notifyItemRangeInserted(0, mPageSize);
    }

    private List<String> initDataset() {
        List<String> initialDataset = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            initialDataset.add("This is element #" + i);
        }

        return initialDataset;
    }

    private List<String> getMoreData() {
        List<String> crtDataset = new ArrayList<String>();
        int start = mPageSize * mCurrentMultiplier + 10;
        int end = start + mPageSize;
        for (int i = end - 1; i >= start; i--) {
            crtDataset.add("This is element #" + i);
        }

        mCurrentMultiplier++;

        return crtDataset;
    }

}
