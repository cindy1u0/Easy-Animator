package cs3500.animator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs3500.animator.controller.Controller;
import cs3500.animator.controller.ControllerImpl;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModelImpl;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IView;

import static cs3500.animator.view.FactoryView.makeView;


/**
 * The main class that enables the program to run in three forms: text view, visual, or SVG.
 */
public class Excellence {
  /**
   * runs the program in three forms: text, visual, or SVG.
   *
   * @param args command lines that the user inputs in
   */
  public static void main(String[] args) {
    AnimationModel model = null;
    String viewType = null;
    String out = null;
    int tempo = 1;
    JFrame frame = new JFrame();
    frame.setSize(100, 100);
    for (int i = 0; i < args.length; i++) {
      // reads input file
      if (args[i].equals("-in")) {
        try {
          InputStream inputStream = new FileInputStream(args[i + 1]);
          model = AnimationReader.parseFile(new InputStreamReader(inputStream),
                  new AnimationModelImpl.Builder());
        } catch (FileNotFoundException e) {
          // catch file not found error
          JOptionPane.showMessageDialog(frame,
                  "File not found",
                  "File warning",
                  JOptionPane.WARNING_MESSAGE);
        }
      }
      // reads output file path
      if (args[i].equals("-out")) {
        try {
          if (!args[i + 1].equals("out")) {
            out = args[i + 1];
          }
        } catch (Exception e) {
          JOptionPane.showMessageDialog(frame,
                  "Output source not found", "Output warning",
                  JOptionPane.WARNING_MESSAGE);
        }
      }
      // reads which kind of view the user wants
      if (args[i].equals("-view")) {
        try {
          viewType = args[i + 1];
        } catch (Exception e) {
          // catch no input given
          JOptionPane.showMessageDialog(frame, "please give me a view",
                  "View warning",
                  JOptionPane.WARNING_MESSAGE);
        }
      }
      // reads the speed
      try {
        if (args[i].equals("-speed")) {
          tempo = Integer.valueOf(args[i + 1]);
        }
      } catch (Exception e) {
        // catch no input given
        JOptionPane.showMessageDialog(frame, "speed sir speed",
                "speed warning",
                JOptionPane.WARNING_MESSAGE);
      }
    }
    // runs the program
    try {
      IView view = makeView(viewType, tempo);
      Controller controller = new ControllerImpl(model, view);
      if (out == null) {
        controller.run(System.out);
      } else {
        try {
          FileWriter output = new FileWriter(out);
          controller.run(output);
          output.flush();
          output.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (NullPointerException e) {
      JOptionPane.showMessageDialog(frame, "oh well I guess nothing happens",
              "Null Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}

