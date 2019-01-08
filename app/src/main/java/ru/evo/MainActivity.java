package ru.evo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Collection;
import ru.evo.model.Gear;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
    }

    static class DrawView extends View {

        private float sizeX;
        private float sizeY;
        private float radius;
        private enum LineStyle {solid, dotted};
        private int OFFSET = 20;

        public DrawView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            sizeX = canvas.getWidth();
            sizeY = canvas.getHeight();
            radius = (float) (0.4 * (sizeX <= sizeY ? sizeX: sizeY));

            canvas.drawColor(0xfff0f0f0);

            drawAxis(canvas);

            Gear gear = new Gear(12, radius);
            drawGear(canvas, gear, sizeX/2, sizeY/2);
        }

        private void drawGear(Canvas canvas, Gear gear, float x, float y) {
            Paint paint = getPaint(1, 0xff404040, LineStyle.solid);

            //canvas.drawCircle(sizeX/2,sizeY/2, r, paint);
            //canvas.drawCircle(sizeX/2,sizeY/2, radius, paint);

            //paint = getPaint(1, 0xff404040, MainActivity.DrawView.LineStyle.dotted);
            //canvas.drawCircle(sizeX/2,sizeY/2, r, paint);

            paint = getPaint(4, 0xff404040, MainActivity.DrawView.LineStyle.solid);
            Collection<Point> points = gear.getPoints();

            for(Point point: points){
                canvas.drawPoint(point.x, point.y, paint);
            }
        }

        private void drawAxis(final Canvas canvas) {
            Paint paint = getPaint(1, 0xfff01010, LineStyle.dotted);
            Path line = new Path();

            line.moveTo(sizeX/2, sizeY/2 - radius - OFFSET);
            line.lineTo(sizeX/2, sizeY/2 + radius + OFFSET);
            canvas.drawPath(line, paint);

            line.moveTo(sizeX/2 - radius - OFFSET, sizeY/2);
            line.lineTo(sizeX/2 + radius + OFFSET, sizeY/2);
            canvas.drawPath(line, paint);
        }

        @NonNull
        private Paint getPaint(final int width, final int color, LineStyle style) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(width);
            paint.setColor(color);

            paint.setPathEffect(null);
            if(style == LineStyle.dotted){
                paint.setPathEffect(new DashPathEffect(new float[] {80, 10, 5, 10}, 0));
            }

            return paint;
        }
    }
}