import javafx.scene.canvas.Canvas;

import javax.swing.undo.AbstractUndoableEdit;

/**
 * Created by aryka on 1/12/2017.
 */
public class UndoableScribble extends AbstractUndoableEdit {

    private Canvas referenceCanvas;
    private Canvas undoCanvas;
    private Canvas redoCanvas;

    public UndoableScribble(Canvas canvas) {
        this.referenceCanvas = canvas;
        this.undoCanvas = Hw1Scribble2Main.cloneCanvas(canvas);
    }

    @Override
    public void undo() {
        this.redoCanvas = Hw1Scribble2Main.cloneCanvas(this.referenceCanvas);
        Hw1Scribble2Main.overrideCanvas(this.referenceCanvas, this.undoCanvas);
    }

    @Override
    public void redo() {
        this.undoCanvas = Hw1Scribble2Main.cloneCanvas(this.referenceCanvas);
        Hw1Scribble2Main.overrideCanvas(this.referenceCanvas, this.redoCanvas);
    }

    @Override
    public boolean canRedo() {
        //return this.redoCanvas != null;
        return true;
    }

    @Override
    public String getPresentationName(){
        return "Scribble";
    }
}
