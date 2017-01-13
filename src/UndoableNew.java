import javafx.scene.canvas.Canvas;

import javax.swing.undo.AbstractUndoableEdit;

public class UndoableNew extends AbstractUndoableEdit {

    private Canvas referenceCanvas;
    private Canvas undoCanvas;
    private Canvas redoCanvas;

    public UndoableNew(Canvas canvas) {
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
        return false;
    }

    @Override
    public String getPresentationName() {
        return "New";
    }
}
