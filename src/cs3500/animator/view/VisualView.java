package cs3500.animator.view;

import java.awt.*;
import java.util.List;


import javax.swing.*;

import cs3500.animator.model.tools.Screen;
import cs3500.animator.model.shapes.IReadOnlyShapes;

/**
 * A class that renders the animation of the motions of all the shapes on the canvas.
 */
public class VisualView extends AbstractView {
  private int tempo;
  private final Drawing panel;


  /**
   * A constructor that will later be used in the controller.
   *
   * @param tempo given tick per second
   */
  public VisualView(int tempo) {
    super();
    this.tempo = tempo;
    panel = new Drawing();
    panel.setBackground(Color.WHITE);

    JScrollPane scrollPane = new JScrollPane(panel);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    add(scrollPane);

    setVisible(true);
  }

  /**
   * Renders the given output depends on with view the user is selecting.
   *
   * @param shapes given IReadOnlyShapes that are from the model
   */
  @Override
  public void render(List<IReadOnlyShapes> shapes) {
    panel.draw(shapes);
  }

  /**
   * setter that sets the default canvas to the given size.
   *
   * @param c given canvas size
   */
  @Override
  public void setCanvas(Screen c) {
    this.canvas = c;
    panel.setBounds(canvas.getLocX(), canvas.getLocY(), canvas.getCanvasW(), canvas.getCanvasH());
    setSize(1000, 800);
    panel.setPreferredSize(new Dimension(canvas.getCanvasW(), canvas.getCanvasH()));
  }

  /**
   * Gets the tempo that the user passes in.
   *
   * @return the tempo
   */
  @Override
  public int getTempo() {
    return this.tempo;
  }

  /**
   * Adds all the IViewFeatures to the listeners to execute.
   *
   * @param vf given IViewFeatures
   * @throws UnsupportedOperationException if the given view doesn't support the listeners
   */
  @Override
  public void addListener(IViewFeatures vf) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("yeehaw");
  }

}


