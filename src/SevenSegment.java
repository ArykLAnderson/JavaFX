import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aryka on 1/30/2017.
 */
public class SevenSegment extends Region {

    private int[] _displayScale = {20, 20};
    private Canvas _canvas;
    private int _currentValue;
    private final int _segmentLength = 8;
    private final double[] _scale = {10, 18};
    private final int[] _margins = {2, 2};
    private final double[] _xVerts = {0, 1, 7, 8, 7 , 1};
    private final double[] _yVerts = {0, 1, 1, 0, -1, -1};
    private final Color _o = new Color(0.3, 1.0, 0.2, 1.0f);
    private final Color _f = new Color(0.3, 1.0, 0.2, 0.1f);
    private Map<SegmentType, Color[]> _map;


    public SevenSegment() {
        _currentValue = 10;
        _canvas = new Canvas();
        this.getChildren().add(_canvas);
        this.setPrefSize((_scale[0] + 2*_margins[0]) * _displayScale[0], (_scale[1] + 2*_margins[1]) * _displayScale[1]);
        _map = this.initMap();
    }

    public SevenSegment(int curValue) {
        this();
        if (curValue >= 0 && curValue <= 10) {
            _currentValue = curValue;
        }
    }

    public void draw() {
        GraphicsContext context = _canvas.getGraphicsContext2D();

        context.save();
        context.clearRect(0, 0, _canvas.getWidth(), _canvas.getHeight());

        context.setFill(Color.BLACK);
        context.fillRect(0, 0, _canvas.getWidth(), _canvas.getHeight());

        double width = _canvas.getWidth() / (_scale[0] + _margins[0]);
        double height = _canvas.getHeight() / (_scale[1] + _margins[1]);
        context.scale(width, height);
        context.translate(_margins[0], _margins[1]);
        context.save();

        //top segment
        context.setFill(_map.get(SegmentType.TOP)[_currentValue]);
        context.fillPolygon(_xVerts, _yVerts, 6);
        context.restore();
        context.save();

        //top-left segment
        context.setFill(_map.get(SegmentType.TOP_LEFT)[_currentValue]);
        context.fillPolygon(_yVerts, _xVerts, 6);
        context.restore();
        context.save();

        //top-right segment
        context.translate(_segmentLength, 0);
        context.setFill(_map.get(SegmentType.TOP_RIGHT)[_currentValue]);
        context.fillPolygon(_yVerts, _xVerts, 6);
        context.restore();
        context.save();

        //center segment
        context.translate(0, _segmentLength);
        context.setFill(_map.get(SegmentType.CENTER)[_currentValue]);
        context.fillPolygon(_xVerts, _yVerts, 6);
        context.restore();
        context.save();

        //bottom segment
        context.translate(0, 2* _segmentLength);
        context.setFill(_map.get(SegmentType.BOTTOM)[_currentValue]);
        context.fillPolygon(_xVerts, _yVerts, 6);
        context.restore();
        context.save();

        //bottom left segment
        context.translate(0, _segmentLength);
        context.setFill(_map.get(SegmentType.BOTTOM_LEFT)[_currentValue]);
        context.fillPolygon(_yVerts, _xVerts, 6);
        context.restore();
        context.save();

        //bottom right segment
        context.translate( _segmentLength, _segmentLength);
        context.setFill(_map.get(SegmentType.BOTTOM_RIGHT)[_currentValue]);
        context.fillPolygon(_yVerts, _xVerts, 6);
        context.restore();

        context.restore();
    }

    public void setCurrentValue(int value) {
        if (value < 0 || value > 10)
            _currentValue = 10;

        _currentValue = value;
    }

    public int getCurrentValue() {
        return _currentValue;
    }

    @Override
    public void layoutChildren() {
        double availHeight = this.getHeight();
        double availWidth = this.getWidth();
        double aspect = _scale[0]/_scale[1];

        double candidateHeight = availWidth/aspect;
        double candidateWidth = availHeight*aspect;

        double heightActual= 0, widthActual = 0;

        if (candidateHeight > availHeight) {
            heightActual = availHeight;
            widthActual = candidateWidth;
        } else if (candidateWidth > availWidth) {
            heightActual = candidateHeight;
            widthActual = availWidth;
        }

        _canvas.setWidth(widthActual);
        _canvas.setHeight(heightActual);

        this.layoutInArea(_canvas, 0, 0, this.getWidth(), this.getHeight(), 0, HPos.CENTER, VPos.CENTER);
        this.draw();
    }

    private Map<SegmentType, Color[]> initMap() {

        HashMap<SegmentType, Color[]> map = new HashMap<>();

        //                    0,  1,  2,  3,  4,  5,  6,  7,  8,  9, OFF
        Color[][] colors = {{_o, _f, _o, _o, _f, _o, _o, _o, _o, _o, _f},   //TOP
                            {_o, _f, _f, _f, _o, _o, _o, _f, _o, _o, _f},   //TOP_LEFT
                            {_o, _o, _o, _o, _o, _f, _f, _o, _o, _o, _f},   //TOP_RIGHT
                            {_f, _f, _o, _o, _o, _o, _o, _f, _o, _o, _f},   //CENTER
                            {_o, _f, _o, _f, _f, _f, _o, _f, _o, _f, _f},   //BOTTOM_LEFT
                            {_o, _o, _f, _o, _o, _o, _o, _o, _o, _o, _f},   //BOTTOM_RIGHT
                            {_o, _f, _o, _o, _f, _o, _o, _f, _o, _f, _f}};  //BOTTOM

        map.put(SegmentType.TOP, colors[0]);
        map.put(SegmentType.TOP_LEFT, colors[1]);
        map.put(SegmentType.TOP_RIGHT, colors[2]);
        map.put(SegmentType.CENTER, colors[3]);
        map.put(SegmentType.BOTTOM_LEFT, colors[4]);
        map.put(SegmentType.BOTTOM_RIGHT, colors[5]);
        map.put(SegmentType.BOTTOM, colors[6]);
        return map;
    }
}
