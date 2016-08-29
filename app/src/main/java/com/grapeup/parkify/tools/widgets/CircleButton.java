package com.grapeup.parkify.tools.widgets;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.grapeup.parkify.R;
import com.mikepenz.iconics.IconicsDrawable;

public class CircleButton extends ImageView {

	private static final int PRESSED_COLOR_LIGHTUP = 255 / 25;
	private static final int PRESSED_RING_ALPHA = 75;
	private static final int DEFAULT_PRESSED_RING_WIDTH_DIP = 4;
	private static final int ANIMATION_TIME_ID = android.R.integer.config_shortAnimTime;
	public static final int MIN_SMALL_RADIUS = 30;

	private int centerY;
	private int centerX;
	private int outerRadius;
	private int pressedRingRadius;

	private Paint circlePaint;
	private Paint focusPaint;
	private Paint smallCirclePaint;
	private IconicsDrawable smallCircleIcon;

	private float animationProgress;
	private int pressedRingWidth;
	private int defaultColor = Color.BLACK;
	private int pressedColor;
	private ObjectAnimator pressedAnimator;
	private float synchronizationAngle = 0;
	private ObjectAnimator smallIconAnimator;
	private boolean showSynchronization = false;

	public CircleButton(Context context) {
		super(context);
		init(context, null);
	}

	public CircleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public CircleButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		this.setFocusable(true);
		this.setScaleType(ScaleType.CENTER_INSIDE);
		setClickable(true);

		circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circlePaint.setStyle(Paint.Style.FILL);

		smallCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		smallCirclePaint.setStyle(Paint.Style.FILL);
		smallCirclePaint.setColor(getResources().getColor(R.color.defaultBackground));

		smallCircleIcon = new IconicsDrawable(getContext(), "faw_refresh"){
			public Rect bounds = new Rect();

			@Override
			public void draw(Canvas canvas) {
				copyBounds(bounds);
				canvas.save();
				canvas.rotate(synchronizationAngle, bounds.centerX(), bounds.centerY());
				super.draw(canvas);
				canvas.restore();
			}
		};

		focusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		focusPaint.setStyle(Paint.Style.STROKE);

		pressedRingWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_PRESSED_RING_WIDTH_DIP, getResources()
				.getDisplayMetrics());

		int color = Color.BLACK;
		if (attrs != null) {
			final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleButton);
			color = a.getColor(R.styleable.CircleButton_cb_color, color);
			pressedRingWidth = (int) a.getDimension(R.styleable.CircleButton_cb_pressedRingWidth, pressedRingWidth);
			a.recycle();
		}

		setColor(color);

		focusPaint.setStrokeWidth(pressedRingWidth);
		final int pressedAnimationTime = getResources().getInteger(ANIMATION_TIME_ID);
		pressedAnimator = ObjectAnimator.ofFloat(this, "animationProgress", 0f, 0f);
		pressedAnimator.setDuration(pressedAnimationTime);

		smallIconAnimator = ObjectAnimator.ofFloat(this, "synchronizationAngle", 0f, 0f);
		smallIconAnimator.setInterpolator(new DecelerateInterpolator());
		smallIconAnimator.setStartDelay(300);
		smallIconAnimator.setDuration(2000);
	}

	public void setColor(int color) {
		this.defaultColor = color;
		this.pressedColor = getHighlightColor(color, PRESSED_COLOR_LIGHTUP);

		circlePaint.setColor(defaultColor);
		focusPaint.setColor(defaultColor);
		focusPaint.setAlpha(PRESSED_RING_ALPHA);

		this.invalidate();
	}

	private int getHighlightColor(int color, int amount) {
		return Color.argb(Math.min(255, Color.alpha(color)), Math.min(255, Color.red(color) + amount),
				Math.min(255, Color.green(color) + amount), Math.min(255, Color.blue(color) + amount));
	}

	@Override
	public void setPressed(boolean pressed) {
		super.setPressed(pressed);

		if (circlePaint != null) {
			circlePaint.setColor(pressed ? pressedColor : defaultColor);
		}

		if (pressed) {
			showPressedRing();
		} else {
			hidePressedRing();
		}
	}

	private void hidePressedRing() {
		pressedAnimator.setFloatValues(pressedRingWidth, 0f);
		pressedAnimator.start();
	}

	private void showPressedRing() {
		pressedAnimator.setFloatValues(animationProgress, pressedRingWidth);
		pressedAnimator.start();
	}

	public void showSynchronization(){
		showSynchronization = true;
		smallIconAnimator.setFloatValues(synchronizationAngle, 180f);
		smallIconAnimator.setRepeatCount(ValueAnimator.INFINITE);
		smallIconAnimator.start();
		smallCircleIcon.invalidateSelf();
	}

	public void hideSynchronization() {
		showSynchronization = false;
		synchronizationAngle = 0;
		smallIconAnimator.setFloatValues(synchronizationAngle, 0f);
		smallIconAnimator.cancel();
		smallCircleIcon.invalidateSelf();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//ring
		canvas.drawCircle(centerX, centerY, pressedRingRadius + animationProgress, focusPaint);
		//circle button
		canvas.drawCircle(centerX, centerY, outerRadius - pressedRingWidth, circlePaint);

		if (showSynchronization) {
			createSmallCircle(canvas);
		}

		super.onDraw(canvas);
	}

	private void createSmallCircle(Canvas canvas) {
		int circleRadius = outerRadius - pressedRingWidth;
		int angle = 45;
		// counting position on circle circumstance at 45 degree angle
		double x = centerX + (circleRadius * Math.cos(angle * Math.PI / 180f));
		double y = centerY + (circleRadius * Math.sin(angle * Math.PI / 180f));

		int smallCircleRadius = (centerX / 4) - 20;
		if (smallCircleRadius < MIN_SMALL_RADIUS) {
            smallCircleRadius = MIN_SMALL_RADIUS;
        }


		canvas.drawCircle((int)x, (int)y, smallCircleRadius, smallCirclePaint);
		int smallCirclePadding = 25;
		smallCircleIcon.sizeDp(smallCircleRadius - smallCirclePadding);
		smallCircleIcon.color(circlePaint.getColor());
		int left = (int) x - smallCircleRadius + smallCirclePadding;
		int top = (int) y - smallCircleRadius + smallCirclePadding;
		int right = (int) x + smallCircleRadius - smallCirclePadding;
		int bottom = (int) y + smallCircleRadius - smallCirclePadding;
		smallCircleIcon.setBounds(left, top, right, bottom);
		smallCircleIcon.draw(canvas);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		centerX = w / 2;
		centerY = h / 2;
		outerRadius = Math.min(w, h) / 2;
		pressedRingRadius = outerRadius - pressedRingWidth - pressedRingWidth / 2;
	}

	public float getAnimationProgress() {
		return animationProgress;
	}

	public void setAnimationProgress(float animationProgress) {
		this.animationProgress = animationProgress;
		this.invalidate();
	}

	public float getSynchronizationAngle() {
		return animationProgress;
	}

	public void setSynchronizationAngle(float animationProgress) {
		this.synchronizationAngle = animationProgress;
		this.invalidate();
	}
}
