package com.cocorporation.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

public class BouncingBallView extends View {
	public final static int SET_FIRST_POS = 0;
	public final static int SET_INTERMEDIATE_POS = 1;
	public final static int SET_LAST_POS = 2;
	private static final String TAG = "BouncingBallView";

	private int xMin = 0; // This view's bounds
	private int xMax;
	private int yMin = 0;
	private int yMax;
	private float xLineOrigin;
	private float yLineOrigin;
	private float xLineLast;
	private float yLineLast;
	private final float outLineSize = 10;
	private final float halfSQRTTwo = (float) (Math.sqrt(2) / 2);
	private boolean finger1Down = false;
	private boolean finger2Down = false;
	private boolean ballLaunched = false;
	private float ballRadius = 80; // Ball's radius
	private float ballX = ballRadius + 20; // Ball's center (x,y)
	private float ballY = ballRadius + 40;
	private float ballSpeedX = 10; // Ball's speed (x,y)
	private float ballSpeedY = 10;
	private RectF ballBounds; // Needed for Canvas.drawOval
	private RectF underBall;
	private Paint paintBall; // The paint (e.g. style, color) used for drawing
	private Paint paintLine;
	private Paint paintUnderBall;
	private Color ballColor = new Color();
	private int alpha = 255, red = 255, green = 255, blue = 255;
	private boolean moveAllowed = false; // Determines whether the ball has to
											// automatically move or not
	private boolean collision = false;

	// Constructor
	public BouncingBallView(Context context) {
		super(context);
		ballBounds = new RectF();
		underBall = new RectF();
		paintBall = new Paint();
		paintBall.setColor(Color.GREEN);
		paintLine = new Paint();
		paintLine.setColor(Color.YELLOW);
		paintUnderBall = new Paint();
		paintUnderBall.setColor(Color.WHITE);
		Log.i(TAG, "Style = " + paintLine.getStyle().values());
	}

	// Called back to draw the view. Also called by invalidate().
	@Override
	protected void onDraw(Canvas canvas) {
		// Draw the ball
		ballBounds.set(ballX - ballRadius, ballY - ballRadius, ballX
				+ ballRadius, ballY + ballRadius);
		underBall.set(ballX - ballRadius + outLineSize, ballY - ballRadius + outLineSize, ballX
				+ ballRadius - outLineSize, ballY + ballRadius - outLineSize);
		canvas.drawOval(ballBounds, paintBall);
		canvas.drawOval(underBall, paintUnderBall);
		if (this.finger1Down && this.finger2Down) {
			canvas.drawLine(xLineOrigin, yLineOrigin, xLineLast, yLineLast,
					paintLine);
		}

		// Update the position of the ball, including collision detection and
		// reaction.
		if (moveAllowed) {
			if (!collision)
				update();
			if (this.finger1Down && this.finger2Down) {
				if (checkSegmentCross(xLineOrigin, yLineOrigin, xLineLast,
						yLineLast, ballX - ballRadius, ballY, ballX
								+ ballRadius, ballY)
						|| checkSegmentCross(xLineOrigin, yLineOrigin,
								xLineLast, yLineLast, ballX,
								ballY - ballRadius, ballX, ballY + ballRadius)
						|| checkSegmentCross(xLineOrigin, yLineOrigin,
								xLineLast, yLineLast, ballX - halfSQRTTwo,
								ballY + halfSQRTTwo, ballX + halfSQRTTwo, ballY
										- halfSQRTTwo)
						|| checkSegmentCross(xLineOrigin, yLineOrigin,
								xLineLast, yLineLast, ballX - halfSQRTTwo,
								ballY - halfSQRTTwo, ballX + halfSQRTTwo, ballY
										+ halfSQRTTwo)) {
					collision = true;
					Log.i(TAG, "Segment ball = " + (ballX - ballRadius) + ", "
							+ (ballX + ballRadius) + " - "
							+ (ballY - ballRadius) + ", "
							+ (ballY + ballRadius));
					Log.i(TAG, "Segment fingers = " + xLineOrigin + ", "
							+ yLineOrigin + " - " + xLineLast + ", "
							+ yLineLast);
				} else {
					if (collision) {
						collision = false;
						red += 10 %255;
						green += 6 %255;
						blue += 3 %255;
						paintBall.setColor(ballColor.argb(alpha, red, green, blue));
					}
				}

			} else {
				if (collision) {
					collision = false;
					red += 10 %255;
					green += 6 %255;
					blue += 3 %255;
					paintBall.setColor(ballColor.argb(alpha, red, green, blue));
				}
			}
		}

		// Delay
		/*
		 * try { Thread.sleep(3); } catch (InterruptedException e) { }
		 */

		invalidate(); // Force a re-draw
	}

	// Detect collision and update the position of the ball.
	private void update() {
		// Get new (x,y) position
		ballX += ballSpeedX;
		ballY += ballSpeedY;
		// Detect collision and react
		if (ballX + ballRadius > xMax) {
			ballSpeedX = -ballSpeedX;
			ballX = 2 * xMax - 2 * ballRadius - ballX;
		} else if (ballX - ballRadius < xMin) {
			ballSpeedX = -ballSpeedX;
			ballX = 2 * xMin + 2 * ballRadius - ballX;
		}
		if (ballY + ballRadius > yMax) {
			ballSpeedY = -ballSpeedY;
			ballY = 2 * yMax - 2 * ballRadius - ballY;
		} else if (ballY - ballRadius < yMin) {
			ballSpeedY = -ballSpeedY;
			ballY = 2 * yMin + 2 * ballRadius - ballY;
		}
	}

	// Called back when the view is first created or its size changes.
	@Override
	public void onSizeChanged(int w, int h, int oldW, int oldH) {
		// Set the movement bounds for the ball
		xMax = w - 1;
		yMax = h - 1;
	}

	public void updateFromTouchFirstFingerDown(float touchX, float touchY) {
		if (!this.ballLaunched) {
			updateCordonnates(touchX, touchY);
			this.moveAllowed = false;
		} else {
			this.xLineOrigin = touchX;
			this.yLineOrigin = touchY;
			this.finger1Down = true;
			Log.i(TAG, "first = " + touchX + ", " + touchY);
		}

	}

	public void updateFromTouchSecondFingerDown(float touchX, float touchY) {
		if (this.ballLaunched) {
			this.xLineLast = touchX;
			this.yLineLast = touchY;
			this.finger2Down = true;
			Log.i(TAG, "second = " + touchX + ", " + touchY);
		}
	}

	public void updateFromTouchFirstFingerMove(float touchX, float touchY) {
		if (!this.ballLaunched) {
			updateCordonnates(touchX, touchY);
		} else {
			this.xLineOrigin = touchX;
			this.yLineOrigin = touchY;
		}
	}

	public void updateFromTouchSecondFingerMove(float touchX, float touchY) {
		if (this.ballLaunched) {
			this.xLineLast = touchX;
			this.yLineLast = touchY;
		}
	}

	public void updateFromTouchFirstFingerUp(float touchX, float touchY) {
		if (!this.ballLaunched) {
			updateCordonnates(touchX, touchY);
			this.ballLaunched = true;
			this.moveAllowed = true;
		} else {
			this.finger1Down = false;
		}
	}

	public void updateFromTouchSecondFingerUp(float touchX, float touchY) {
		this.finger2Down = false;
	}

	private void updateCordonnates(float ballX, float ballY) {
		if (ballX < xMin + ballRadius)
			ballX = xMin + ballRadius;
		if (ballX > xMax - ballRadius)
			ballX = xMax - ballRadius;
		this.ballX = ballX;

		if (ballY < yMin + ballRadius)
			ballY = yMin + ballRadius;
		if (ballY > yMax - ballRadius)
			ballY = yMax - ballRadius;
		this.ballY = ballY;

	}

	private boolean checkSegmentCross(float x1, float y1, float x2, float y2,
			float x3, float y3, float x4, float y4) {

		if (x1 > x2) {
			float temp = x2;
			x2 = x1;
			x1 = temp;
			temp = y2;
			y2 = y1;
			y1 = temp;
		}
		if (x3 > x4) {
			float temp = x4;
			x4 = x3;
			x3 = temp;
			temp = y4;
			y4 = y3;
			y3 = temp;
		}

		boolean vert1 = (x1 == x2);
		boolean vert2 = (x3 == x4);

		if (vert1 && vert2)
			return false;
		else {
			if (vert1) {
				float a2 = (y4 - y3) / (x4 - x3);
				float b2 = y3 - (a2 * x3);
				float yIntersection = a2 * x1 + b2;
				if (yIntersection >= y1 && yIntersection <= y2
						&& yIntersection >= y3 && yIntersection <= y4)
					return true;
				return false;
			} else if (vert2) {
				float a1 = (y2 - y1) / (x2 - x1);
				float b1 = y1 - (a1 * x1);
				float yIntersection = a1 * x3 + b1;
				if (yIntersection >= y1 && yIntersection <= y2
						&& yIntersection >= y3 && yIntersection <= y4)
					return true;
				return false;
			} else {
				float a1 = (y2 - y1) / (x2 - x1);
				float a2 = (y4 - y3) / (x4 - x3);
				if (a1 == a2)
					return false;

				float b1 = y1 - (a1 * x1);
				float b2 = y3 - (a2 * x3);
				float xIntersection = (b2 - b1) / (a1 - a2);
				if (xIntersection >= x1 && xIntersection <= x2
						&& xIntersection >= x3 && xIntersection <= x4)
					return true;
				return false;
			}
		}
	}

	public float getBallX() {
		return ballX;
	}

	public void setBallX(float ballX) {
		if (ballX < xMin + ballRadius)
			ballX = xMin + ballRadius;
		if (ballX > xMax - ballRadius)
			ballX = xMax - ballRadius;
		this.ballX = ballX;
	}

	public float getBallY() {
		return ballY;
	}

	public void setBallY(float ballY) {
		if (ballY < yMin + ballRadius)
			ballY = yMin + ballRadius;
		if (ballY > yMax - ballRadius)
			ballY = yMax - ballRadius;
		this.ballY = ballY;
	}
}
