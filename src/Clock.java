import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Clock extends Region {

    private static final double _face = 10.0f;
    private static final double _radius = _face * 0.94;
    private static final double _sHandLength = _radius * 0.94f;
    private static final double TIMER_PERIOD = 250; //time in milliseconds
    private static final double[] _mPolygonX = {0f, -0.5f, 0f, 0.5f};
    private static final double[] _mPolygonY = {-1, 0, 9, 0};
    private static final double[] _hPolygonX = {0, -0.5, 0, 0.5};
    private static final double[] _hPolygonY = {-1, 0, 6, 0};
    private Canvas _canvas;
    private double _prevTime = 0;
    private SimpleIntegerProperty _SECONDS, _MINUTES, _HOURS;


    public Clock() {
        _canvas = new Canvas();
        this.getChildren().add(_canvas);
        _canvas.setHeight(400);
        _canvas.setWidth(400);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onTimer();
            }
        };
        _SECONDS = new SimpleIntegerProperty();
        _MINUTES = new SimpleIntegerProperty();
        _HOURS = new SimpleIntegerProperty();
        timer.start();
    }

    private void onTimer() {

        double now = System.currentTimeMillis();
        double elapsed = now - _prevTime;
        if (elapsed > TIMER_PERIOD) {
            draw();
            _prevTime = now;
        }
    }

    private void draw() {

        GraphicsContext context = _canvas.getGraphicsContext2D();
        context.clearRect(0, 0, _canvas.getWidth(), _canvas.getHeight());
        context.setStroke(Color.BLACK);
        context.setLineWidth(0.1);
        context.save();

        context.translate(_canvas.getWidth()/2, _canvas.getHeight()/2);
        context.scale(_canvas.getWidth()/(2*_face), _canvas.getHeight()/(2*_face));

        GregorianCalendar now = new GregorianCalendar();

        drawClockFace(context);
        drawHourHand(context, now);
        drawMinuteHand(context, now);
        drawSecondHand(context, now);

        _SECONDS.setValue(now.get(Calendar.SECOND));
        _MINUTES.setValue(now.get(Calendar.MINUTE));
        _HOURS.setValue(now.get(Calendar.HOUR_OF_DAY));

        context.restore();
    }

    private void drawClockFace(GraphicsContext context) {

        context.save();
        context.strokeOval(-_radius, -_radius, _radius*2, _radius*2);

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 4; j++) {
                context.rotate(6);
                context.strokeLine(0, _radius - _radius*0.025, 0, _radius);
            }

            context.rotate(6);
            context.strokeLine(0, _radius - _radius*0.075, 0, _radius);
        }
        context.restore();
    }

    private void drawHourHand(GraphicsContext context, GregorianCalendar now) {
        context.save();
        double hTime = now.get(Calendar.HOUR);
        double mTime = now.get(Calendar.MINUTE);

        context.rotate(30*hTime + 0.5*mTime + 180);
        context.fillPolygon(_hPolygonX, _hPolygonY, 4);
        context.restore();
    }

    private void drawMinuteHand(GraphicsContext context, GregorianCalendar now) {
        context.save();
        double mTime = now.get(Calendar.MINUTE);
        double sTime = now.get(Calendar.SECOND);

        context.rotate(6*mTime + 0.1*sTime + 180);
        context.fillPolygon(_mPolygonX, _mPolygonY, 4);
        context.restore();
    }

    private void drawSecondHand(GraphicsContext context, GregorianCalendar now) {

        context.save();
        double time = now.get(Calendar.SECOND);
        context.rotate(6*time + 180);
        context.strokeLine(0, -1, 0, _sHandLength);
        context.restore();
    }

    @Override
    public void layoutChildren() {
        double availHeight = this.getHeight();
        double availWidth = this.getWidth();
        double actualHeight = Math.min(availHeight, availWidth);
        _canvas.setWidth(actualHeight);
        _canvas.setHeight(actualHeight);

        this.layoutInArea(_canvas, 0, 0, this.getWidth(),
                this.getHeight(), 0, HPos.CENTER, VPos.CENTER);
        this.draw();
    }

    public SimpleIntegerProperty secondsProperty() {
        return _SECONDS;
    }

    public SimpleIntegerProperty minutesProperty() {
        return _MINUTES;
    }

    public SimpleIntegerProperty hoursProperty() {
        return _HOURS;
    }
}
