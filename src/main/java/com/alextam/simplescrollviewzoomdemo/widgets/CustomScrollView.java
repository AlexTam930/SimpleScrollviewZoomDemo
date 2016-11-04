package com.alextam.simplescrollviewzoomdemo.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;


/**
 * @author AlexTam
 */
public class CustomScrollView extends ScrollView {

	/**
	 * interface for the movment of ScrollView's scolling.
	 */
	public interface ScrollViewListener {
		void onScrollChanged(CustomScrollView customScrollView, int l, int t,
							 int oldl, int oldt);
	}

	/**
	 * interface for notifying change of scolling when scroll to top or bottom of ScrollView.
	 */
	public interface ScrollViewTopAndBottomListener{

		void onScrollViewTopAndBottom(int scrollX, int scrollY,
									  boolean clampedX, boolean clampedY);

	}

	private ScrollViewListener scrollViewListener = null;
	private ScrollViewTopAndBottomListener scrollViewTopAndBottomListener = null;

	public CustomScrollView(Context context) {
		super(context);
	}

	public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScrollViewListener(ScrollViewListener scrollViewListener) {
		this.scrollViewListener = scrollViewListener;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (scrollViewListener != null) {
			scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
		}
	}

	public void setScrollViewTopAndBottomListener(ScrollViewTopAndBottomListener scrollViewTopAndBottomListener) {
		this.scrollViewTopAndBottomListener = scrollViewTopAndBottomListener;
	}

	@Override
	protected void onOverScrolled(int scrollX, int scrollY,
								  boolean clampedX, boolean clampedY) {

		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

		if (scrollViewTopAndBottomListener != null) {
			scrollViewTopAndBottomListener.onScrollViewTopAndBottom(scrollX, scrollY, clampedX, clampedY);
		}
	}

}
