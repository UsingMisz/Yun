package zzy.com.Manager;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/3/12.
 */

public class pagerScroll extends Scroller {

    private int mduration=1500;
    public pagerScroll(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public pagerScroll(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    public pagerScroll(Context context) {
        super(context);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mduration);
    }

}
