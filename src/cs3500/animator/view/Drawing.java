package cs3500.animator.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JPanel;

import cs3500.animator.model.shapes.IReadOnlyShapes;
import cs3500.animator.model.shapes.ShapeType;

/**
 * A drawing class that will draw all the shapes with corresponding in their positions, colors, and
 * sizes on a canvas with the given size using swing and corresponding methods within the library.
 */
public class Drawing extends JPanel implements IDrawing {
  private List<IReadOnlyShapes> shapes = null;

  /**
   * A constructor that is called in the controller to enable this action.
   */
  public Drawing() {
    super();
  }

  /**
   * Draws a shape with corresponding position, color, and size.
   *
   * @param g given graphics
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (shapes != null) {
      Graphics2D g2d = (Graphics2D) g;
      for (IReadOnlyShapes shape : shapes) {
        g2d.setColor(new Color((int) shape.getColor().getRed(),
                (int) shape.getColor().getGreen(),
                (int) shape.getColor().getBlue()));
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(shape.getRotation()),
                shape.getPosition().getX() + shape.getSize().getWidth() / 2,
                shape.getPosition().getY() + shape.getSize().getHeight() / 2);

        g2d.setTransform(transform);
        if (shape.getShapeType().equals(ShapeType.RECT)) {
          g2d.fillRect((int) shape.getPosition().getX(), (int) shape.getPosition().getY(),
                  (int) shape.getSize().getWidth(), (int) shape.getSize().getHeight());
        }
        if (shape.getShapeType().equals(ShapeType.OVAL)) {
          g2d.fillOval((int) shape.getPosition().getX(), (int) shape.getPosition().getY(),
                  (int) shape.getSize().getWidth(), (int) shape.getSize().getHeight());
        }
      }

    }
  }

  /**
   * draws the shapes on the canvas with the corresponding animations.
   *
   * @param shapes Given IReadOnlyShapes that are able to be mutated
   */
  @Override
  public void draw(List<IReadOnlyShapes> shapes) {
    this.shapes = shapes;
    repaint();
  }
}

