package cs3500.animator.controller;

/**
 * A controller interface that enables the program to output the description of the shapes, draws
 * the animation on a canvas, or write a SVG form.
 */
public interface Controller {

  /**
   * The method that will render the text, draw the shapes, or write a SVG file.
   */
  void run(Appendable ap) throws IllegalStateException;
}

