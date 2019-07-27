package cs3500.animator.view;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.BoxLayout;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.JColorChooser;
import javax.swing.SpinnerNumberModel;

import javax.swing.DefaultListModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cs3500.animator.model.shapes.IReadOnlyShapes;
import cs3500.animator.model.shapes.Oval;
import cs3500.animator.model.shapes.Rect;
import cs3500.animator.model.shapes.Shapes;
import cs3500.animator.model.tools.Screen;

/**
 * A class that allows the user to edit the animations.
 */
public class EditorView extends AbstractView implements IView, ActionListener, ChangeListener,
        ListSelectionListener {
  private final VisualView delegate;
  private JPanel editPanel;
  private DefaultListModel listenerShapes;
  private DefaultListModel listenerMotions;

  private DefaultComboBoxModel comboShapes;

  private JFrame remShapeWindow;
  private JFrame addShapeWindow;

  private DefaultComboBoxModel comboKey;
  private JFrame remKeyWindow;
  private JFrame addKeyWindow;
  private JFrame editKeyWindow;
  private JFrame colorWindow = new JFrame();

  private final JSpinner spinner;
  private final JSlider slider;

  private final JRadioButton loop;

  private Color currShapeColor = Color.WHITE;
  private final JComboBox<String> types = new JComboBox<>(new String[]{"Rectangle", "Oval"});
  private final JTextField tc = new JTextField(5);
  private final JTextField tName = new JTextField(6);

  private final JTextField ac = new JTextField(5);
  private final JTextField aName = new JTextField(6);
  private final JTextField aWidth = new JTextField(6);
  private final JTextField aHeight = new JTextField(6);
  private final JTextField aX = new JTextField(6);
  private final JTextField aY = new JTextField(6);

  private final JTextField ec = new JTextField(5);
  private final JTextField eName = new JTextField(6);
  private final JTextField eWidth = new JTextField(6);
  private final JTextField eHeight = new JTextField(6);
  private final JTextField eX = new JTextField(6);
  private final JTextField eY = new JTextField(6);

  private String currShapeCombo = "";
  private String currShapeSel = "";

  private List<IViewFeatures> listeners;

  /**
   * A constructor that creates the edit panel.
   *
   * @param delegate the visual view that is rendered from the given model
   */
  public EditorView(VisualView delegate) {
    super();
    listeners = new ArrayList<>();
    this.delegate = delegate;
    JMenuBar menuBar = new JMenuBar();
    listenerShapes = new DefaultListModel();
    listenerMotions = new DefaultListModel();
    comboShapes = new DefaultComboBoxModel();
    comboKey = new DefaultComboBoxModel();
    remShapeWindow = new JFrame();
    addShapeWindow = new JFrame();

    remKeyWindow = new JFrame();
    addKeyWindow = new JFrame();
    editKeyWindow = new JFrame();

    delegate.setTitle("Editor");

    ImageIcon img = new ImageIcon("resources/pictures/doge-icon.jpg");

    ImageIcon startImg = new ImageIcon("resources/pictures/start-icon.jpg");
    ImageIcon pauseImg = new ImageIcon("resources/pictures/pause-icon.jpg");
    ImageIcon resumeImg = new ImageIcon("resources/pictures/resume-icon.jpg");
    ImageIcon restartImg = new ImageIcon("resources/pictures/restart-icon.jpg");

    makeEditPanel();
    JMenu control = new JMenu("Control");
    JMenuItem start = new JMenuItem("Start", startImg);
    JMenuItem pause = new JMenuItem("Pause", pauseImg);
    JMenuItem resume = new JMenuItem("Resume", resumeImg);
    JMenuItem restart = new JMenuItem("Restart", restartImg);

    start.setActionCommand("Start");
    start.addActionListener(this);
    pause.setActionCommand("Pause");
    pause.addActionListener(this);
    resume.setActionCommand("Resume");
    resume.addActionListener(this);
    restart.setActionCommand("Restart");
    restart.addActionListener(this);

    JMenuItem ood = new JMenuItem("OOD");
    JMenuItem woah = new JMenuItem("Woah!");
    JMenuItem goat = new JMenuItem("GOAT");
    JMenuItem marble = new JMenuItem("Marbles");
    JMenuItem cel = new JMenuItem("Cel");
    JMenuItem drones = new JMenuItem("Drones");
    JMenuItem tax = new JMenuItem("Tax");
    JMenuItem hw = new JMenuItem("Every Hw");
    JMenuItem thank = new JMenuItem("Thanks!");

    ood.setActionCommand("OOD");
    ood.addActionListener(this);
    woah.setActionCommand("Woah!");
    woah.addActionListener(this);
    goat.setActionCommand("GOAT");
    goat.addActionListener(this);
    marble.setActionCommand("Marbles");
    marble.addActionListener(this);
    cel.setActionCommand("Cel");
    cel.addActionListener(this);
    drones.setActionCommand("Drones");
    drones.addActionListener(this);
    tax.setActionCommand("Tax");
    tax.addActionListener(this);
    hw.setActionCommand("Every Hw");
    hw.addActionListener(this);
    thank.setActionCommand("Thanks!");
    thank.addActionListener(this);

    SpinnerNumberModel speed = new SpinnerNumberModel(delegate.getTempo(), 1,
            Double.MAX_VALUE, 1);
    spinner = new JSpinner(speed);
    ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setColumns(3);

    spinner.addChangeListener(this);

    loop = new JRadioButton("Loop");
    loop.setActionCommand("Loop");
    loop.addActionListener(this);

    control.add(start);
    control.add(pause);
    control.add(resume);
    control.add(restart);
    menuBar.add(control);

    JLabel speedLabel = new JLabel("Speed");
    JPanel bottomPanel = new JPanel(new FlowLayout());

    slider = new JSlider(JSlider.HORIZONTAL, 0, 200, 0);
    slider.addChangeListener(this);
    JLabel sliderLabel = new JLabel("Scrub");

    bottomPanel.add(sliderLabel);
    bottomPanel.add(slider);
    bottomPanel.add(speedLabel);
    bottomPanel.add(spinner);
    bottomPanel.add(loop);

    delegate.setJMenuBar(menuBar);
    delegate.add(bottomPanel, BorderLayout.SOUTH);
    delegate.add(editPanel, BorderLayout.EAST);

    delegate.setIconImage(img.getImage());
  }

  private void makeEditPanel() {
    editPanel = new JPanel();
    editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));

    JList shapeList = new JList(listenerShapes);
    shapeList.setPreferredSize(new Dimension(200, 1000));
    shapeList.setFixedCellWidth(200);

    JScrollPane shapeScroll = new JScrollPane();
    shapeScroll.setViewportView(shapeList);

    shapeList.addListSelectionListener(this);

    JList motionList = new JList(listenerMotions);
    motionList.setPreferredSize(new Dimension(500, 1000));
    motionList.setFixedCellWidth(200);

    JScrollPane motionScroll = new JScrollPane();
    motionScroll.setViewportView(motionList);

    JPanel shapePanel = new JPanel();
    shapePanel.setLayout(new GridLayout(3, 1));
    shapePanel.setMaximumSize(new Dimension(300, 300));
    shapePanel.setBackground(Color.CYAN);

    JButton remShape = new JButton("Remove Shape");
    remShape.setActionCommand("Remove Shape");
    remShape.addActionListener(this);

    JButton addShape = new JButton("Add Shape");
    addShape.setActionCommand("Add Shape");
    addShape.addActionListener(this);

    JLabel shapeText = new JLabel("Shapes");
    shapeText.setHorizontalAlignment(JLabel.CENTER);

    shapePanel.add(shapeText);
    shapePanel.add(remShape);
    shapePanel.add(addShape);

    JPanel motionPanel = new JPanel();
    motionPanel.setLayout(new GridLayout(4, 1));
    motionPanel.setMaximumSize(new Dimension(300, 300));
    motionPanel.setBackground(Color.CYAN);

    JButton remMotion = new JButton("Remove Motion");
    remMotion.setActionCommand("Remove Motion");
    remMotion.addActionListener(this);

    JButton addMotion = new JButton("Add Motion");
    addMotion.setActionCommand("Add Motion");
    addMotion.addActionListener(this);

    JButton editMotion = new JButton("Edit Motion");
    editMotion.setActionCommand("Edit Motion");
    editMotion.addActionListener(this);

    JLabel motionText = new JLabel("Motions");
    motionText.setHorizontalAlignment(JLabel.CENTER);

    motionPanel.add(motionText);
    motionPanel.add(remMotion);
    motionPanel.add(addMotion);
    motionPanel.add(editMotion);

    editPanel.add(shapePanel);
    editPanel.add(shapeScroll);
    editPanel.add(motionPanel);
    editPanel.add(motionScroll);
    initAddShapeWin();
    initRemShapeWin();
    initRemKeyWin();
    initAddKeyWin();
    initEditKeyWin();
  }

  private void initRemShapeWin() {
    JPanel panel = new JPanel();
    remShapeWindow.setTitle("Editor- Remove a Shape");
    remShapeWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    remShapeWindow.setSize(new Dimension(200, 200));
    remShapeWindow.setResizable(false);
    ImageIcon img = new ImageIcon("resources/pictures/doge-icon.jpg");
    remShapeWindow.setIconImage(img.getImage());

    JLabel text = new JLabel("List of Shapes");
    text.setFont(new Font("Open Sans", Font.BOLD, 12));
    text.setHorizontalAlignment(JLabel.CENTER);
    JPanel textPanel = new JPanel();
    textPanel.add(text);

    JComboBox shapeList = new JComboBox(comboShapes);
    currShapeCombo = String.valueOf(shapeList.getSelectedItem());
    shapeList.addItemListener((e) -> currShapeCombo = String.valueOf(e.getItem()));
    shapeList.setPreferredSize(new Dimension(100, 30));
    JPanel comboPanel = new JPanel(new FlowLayout());
    comboPanel.add(shapeList);

    JPanel buttonPanel = new JPanel();
    JButton remButton = new JButton("Let's remove this shape!");
    remButton.setActionCommand("Let's remove this shape!");
    remButton.addActionListener(this);
    buttonPanel.add(remButton);

    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(textPanel);
    panel.add(comboPanel);
    panel.add(buttonPanel);

    remShapeWindow.add(panel);
  }

  private void removeShapeWindow() {
    if (!remShapeWindow.isVisible()) {
      remShapeWindow.setVisible(true);
    }
  }

  private void initRemKeyWin() {
    JPanel panel = new JPanel();
    remKeyWindow.setTitle("Editor- Remove a Motion");
    remKeyWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    remKeyWindow.setSize(new Dimension(700, 200));
    remKeyWindow.setResizable(false);
    ImageIcon img = new ImageIcon("resources/pictures/doge-icon.jpg");
    remKeyWindow.setIconImage(img.getImage());

    JLabel type = new JLabel("Selected Shape");
    JLabel text = new JLabel("List of Motion");
    text.setFont(new Font("Open Sans", Font.BOLD, 12));
    text.setHorizontalAlignment(JLabel.CENTER);

    JComboBox<String> tType = new JComboBox<>(comboShapes);

    JPanel top = new JPanel();

    top.add(type);
    top.add(tType);

    tType.addItemListener((e) -> {
      comboKey.removeAllElements();
      for (IViewFeatures feat : listeners) {
        for (String motion : feat.getControllerMotion(tType.getSelectedItem().toString())) {
          comboKey.addElement(motion);
        }
      }
    });
    JComboBox shapeList = new JComboBox(comboKey);
    shapeList.setPreferredSize(new Dimension(500, 30));
    JPanel comboPanel = new JPanel(new FlowLayout());
    comboPanel.add(shapeList);

    JButton remButton = new JButton("Let's remove this motion!");
    remButton.setActionCommand("Let's remove this motion!");
    remButton.addActionListener(this);

    panel.setLayout(new GridLayout(4, 1));
    panel.setBackground(Color.LIGHT_GRAY);
    panel.add(text);
    panel.add(top);
    panel.add(comboPanel);
    panel.add(remButton);

    remKeyWindow.add(panel);
  }

  private void removeKeyWindow() {
    if (!remKeyWindow.isVisible()) {
      remKeyWindow.setVisible(true);
    }
  }

  private void initAddShapeWin() {
    JPanel panel = new JPanel();
    addShapeWindow.setTitle("Editor- Add a Shape");
    addShapeWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    addShapeWindow.setSize(new Dimension(400, 200));
    addShapeWindow.setResizable(false);
    ImageIcon img = new ImageIcon("resources/pictures/doge-icon.jpg");
    addShapeWindow.setIconImage(img.getImage());

    JLabel name = new JLabel("Name");
    JLabel type = new JLabel("Type");

    tc.setBackground(currShapeColor);
    tc.setEditable(false);

    JPanel top = new JPanel();
    top.setBackground(Color.LIGHT_GRAY);
    top.add(type);
    top.add(types);

    JPanel buttonGroup = new JPanel();
    buttonGroup.setBackground(Color.LIGHT_GRAY);
    buttonGroup.add(name);
    buttonGroup.add(tName);

    JPanel submitPanel = new JPanel(new FlowLayout());
    submitPanel.setBackground(Color.LIGHT_GRAY);
    JButton addShapeButton = new JButton("Let's add this shape!");
    addShapeButton.setForeground(Color.WHITE);
    addShapeButton.setBorder(new LineBorder(Color.decode("#243665"), 4));
    addShapeButton.setBackground(Color.decode("#8BD8BD"));
    addShapeButton.setActionCommand("Let's add this shape!");
    addShapeButton.addActionListener(this);
    submitPanel.add(addShapeButton);

    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(top);
    panel.add(buttonGroup);
    panel.add(submitPanel);

    addShapeWindow.add(panel);
  }

  private void addShapeWindow() {
    if (!addShapeWindow.isVisible()) {
      addShapeWindow.setVisible(true);
    }
  }

  private void colorPicker() {
    if (!colorWindow.isVisible()) {
      initColorWindow();
    }
  }

  private void initColorWindow() {
    ImageIcon img = new ImageIcon("resources/pictures/color-wheel.jpg");
    colorWindow.setIconImage(img.getImage());
    currShapeColor = JColorChooser.showDialog(colorWindow, "Editor- Color Picker",
            colorWindow.getBackground());
    tc.setBackground(currShapeColor);
    ac.setBackground(currShapeColor);
    ec.setBackground(currShapeColor);
  }

  private Shapes editorAddShape() {
    Shapes s;

    switch (String.valueOf(types.getSelectedItem())) {
      case "Rectangle":
        s = new Rect(tName.getText());
        break;
      case "Oval":
        s = new Oval(tName.getText());
        break;
      default:
        s = null;
    }
    return s;
  }

  private void initAddKeyWin() {
    JPanel panel = new JPanel();
    addKeyWindow.setTitle("Editor- Add a Motion");
    addKeyWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    addKeyWindow.setSize(new Dimension(600, 200));
    addKeyWindow.setResizable(false);
    ImageIcon img = new ImageIcon("resources/pictures/doge-icon.jpg");
    addKeyWindow.setIconImage(img.getImage());


    JLabel name = new JLabel("t");
    JLabel widthLabel = new JLabel("Width");
    JLabel heightLabel = new JLabel("Height");
    JLabel xCord = new JLabel("x");
    JLabel yCord = new JLabel("y");
    JLabel type = new JLabel("Selected Shape");

    JPanel buttonGroup = new JPanel();
    JPanel top = new JPanel();
    JComboBox<String> tType = new JComboBox<>(comboShapes);


    JButton color = new JButton("Click for Color!");
    color.setFont(new Font("Futura", Font.BOLD, 10));
    color.setActionCommand("Click for Color!");
    color.addActionListener(this);

    ac.setBackground(currShapeColor);
    ac.setEditable(false);

    top.add(type);
    top.add(tType);
    tType.addItemListener((e) -> {
      comboKey.removeAllElements();
      for (IViewFeatures feat : listeners) {
        for (String motion : feat.getControllerMotion(tType.getSelectedItem().toString())) {
          comboKey.addElement(motion);
        }
      }
    });

    buttonGroup.add(name);
    buttonGroup.add(aName);
    buttonGroup.add(widthLabel);
    buttonGroup.add(aWidth);
    buttonGroup.add(heightLabel);
    buttonGroup.add(aHeight);
    buttonGroup.add(xCord);
    buttonGroup.add(aX);
    buttonGroup.add(yCord);
    buttonGroup.add(aY);
    buttonGroup.add(color);
    buttonGroup.add(ac);

    JPanel submitPanel = new JPanel(new FlowLayout());
    JButton addShapeButton = new JButton("Let's add this motion!");
    addShapeButton.setActionCommand("Let's add this motion!");
    addShapeButton.addActionListener(this);
    submitPanel.add(addShapeButton);

    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(top);
    panel.add(buttonGroup);
    panel.add(submitPanel);

    addKeyWindow.add(panel);
  }

  private void addKeyWindow() {
    if (!addKeyWindow.isVisible()) {
      addKeyWindow.setVisible(true);
    }
  }

  private void initEditKeyWin() {
    JPanel panel = new JPanel();
    editKeyWindow.setTitle("Editor- Edit a Motion");
    editKeyWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    editKeyWindow.setSize(new Dimension(650, 400));
    editKeyWindow.setResizable(false);
    ImageIcon img = new ImageIcon("resources/pictures/doge-icon.jpg");
    editKeyWindow.setIconImage(img.getImage());

    JLabel type = new JLabel("Selected Key Frame");
    JLabel name = new JLabel("t");
    JLabel widthLabel = new JLabel("Width");
    JLabel heightLabel = new JLabel("Height");
    JLabel xCord = new JLabel("x");
    JLabel yCord = new JLabel("y");
    JComboBox<String> tType = new JComboBox<>(comboKey);

    JButton color = new JButton("Click for Color!");
    color.setFont(new Font("Futura", Font.BOLD, 10));
    color.setActionCommand("Click for Color!");
    color.addActionListener(this);

    ec.setBackground(currShapeColor);
    ec.setEditable(false);

    JLabel sel = new JLabel("Selected Shape");
    JComboBox<String> tSel = new JComboBox<>(comboShapes);

    JPanel topTop = new JPanel();

    topTop.add(sel);
    topTop.add(tSel);

    tSel.addItemListener((e) -> {
      comboKey.removeAllElements();
      for (IViewFeatures feat : listeners) {
        for (String motion : feat.getControllerMotion(tSel.getSelectedItem().toString())) {
          comboKey.addElement(motion);
        }
      }
    });

    JPanel buttonGroup = new JPanel();
    JPanel top = new JPanel();

    top.add(type);
    top.add(tType);

    buttonGroup.add(name);
    buttonGroup.add(eName);
    buttonGroup.add(widthLabel);
    buttonGroup.add(eWidth);
    buttonGroup.add(heightLabel);
    buttonGroup.add(eHeight);
    buttonGroup.add(xCord);
    buttonGroup.add(eX);
    buttonGroup.add(yCord);
    buttonGroup.add(eY);
    buttonGroup.add(color);
    buttonGroup.add(ec);

    JPanel submitPanel = new JPanel(new FlowLayout());
    JButton addShapeButton = new JButton("Let's edit this motion!");
    addShapeButton.setActionCommand("Let's edit this motion!");
    addShapeButton.addActionListener(this);
    submitPanel.add(addShapeButton);

    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(topTop);
    panel.add(top);
    panel.add(buttonGroup);
    panel.add(submitPanel);

    editKeyWindow.add(panel);
  }

  private void editKeyWindow() {
    if (!editKeyWindow.isVisible()) {
      editKeyWindow.setVisible(true);
    }
  }

  /**
   * Adds all the IViewFeatures to the listeners to execute.
   *
   * @param vf given IViewFeatures
   * @throws UnsupportedOperationException if the given view doesn't support the listeners
   */
  @Override
  public void addListener(IViewFeatures vf) throws UnsupportedOperationException {
    this.listeners.add(vf);
    for (IReadOnlyShapes s : vf.getControllerShape()) {
      listenerShapes.addElement(s.getName());
      comboShapes.addElement(s.getName());
    }
  }

  /**
   * Renders the given output depends on with view the user is selecting.
   *
   * @param shapes given IReadOnlyShapes that are from the model
   */
  @Override
  public void render(List<IReadOnlyShapes> shapes) {
    delegate.render(shapes);
  }


  /**
   * setter that sets the default canvas to the given size.
   *
   * @param c given canvas size
   */
  @Override
  public void setCanvas(Screen c) {
    delegate.setCanvas(c);
  }

  /**
   * Converts the rendering information into a string.
   *
   * @return the output information in a string form
   */
  @Override
  public StringBuilder getText() {
    return delegate.getText();
  }

  /**
   * Gets the tempo that the user passes in.
   *
   * @return the tempo
   */
  @Override
  public int getTempo() {
    return delegate.getTempo();
  }

  /**
   * Checks if the given view has a listener field.
   *
   * @return true if it has a listener field
   */
  @Override
  public boolean hasListener() throws UnsupportedOperationException {
    return true;
  }

  /**
   * Action performed by the listener.
   *
   * @param e Action Event
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    boolean checkSel = comboShapes.getIndexOf(comboShapes.getSelectedItem())
            == listenerShapes.indexOf(currShapeSel);
    for (IViewFeatures feat : listeners) {
      switch (e.getActionCommand()) {
        case "Start":
          feat.start();
          break;
        case "Resume":
          feat.resume();
          break;
        case "Pause":
          feat.paused();
          break;
        case "Restart":
          feat.restart();
          break;
        case "Loop":
          feat.loop(loop.isSelected());
          break;
        case "Remove Shape":
          removeShapeWindow();
          break;
        case "Let's remove this shape!":
          try {
            feat.removeShape(currShapeCombo);
            listenerShapes.removeElement(currShapeCombo);
            comboShapes.removeElement(currShapeCombo);
            if (listenerShapes.indexOf(currShapeSel) == -1) {
              listenerMotions.clear();
            }
          } catch (NullPointerException p) {
            tryAgain("D'oh! Can't remove shape!!");
          }
          break;
        case "Add Shape":
          addShapeWindow();
          break;
        case "Let's add this shape!":
          try {
            feat.addShape(editorAddShape());
            listenerShapes.addElement(tName.getText());
            comboShapes.addElement(tName.getText());
          } catch (Exception o) {
            tryAgain("Can't add this shape :/");
          }
          break;
        case "Click for Color!":
          colorPicker();
          break;
        case "Remove Motion":
          removeKeyWindow();
          break;
        case "Let's add this motion!":
          try {
            feat.addKeyFrame((String) comboShapes.getSelectedItem(), makeArray(false));
            if (checkSel) {
              listenerMotions.addElement(makeFrameFormat(makeArray(false)));
            }
            comboKey.addElement(makeFrameFormat(makeArray(false)));
            sortList(listenerMotions, true);
            sortList(comboKey, false);
          } catch (IllegalArgumentException p) {
            tryAgain("D'oh! Can't add this motion!!");
          }
          break;
        case "Add Motion":
          addKeyWindow();
          break;
        case "Edit Motion":
          editKeyWindow();
          break;
        case "Let's remove this motion!":
          try {
            String item = (String) comboKey.getSelectedItem();
            String time = item.split(",", 2)[0];
            int second = Integer.parseInt(time.replaceAll("[\\D]", ""));
            feat.removeKeyFrame((String) comboShapes.getSelectedItem(), second);
            if (checkSel) {
              listenerMotions.remove(comboKey.getIndexOf(comboKey.getSelectedItem()));
            }
            comboKey.removeElement(comboKey.getSelectedItem());
          } catch (Exception p) {
            tryAgain("D'oh! No motion to be removed!!");
          }
          break;
        case "Let's edit this motion!":
          try {
            String shapeKey = (String) comboKey.getSelectedItem();
            String t = shapeKey.split(",", 2)[0];
            int sec = Integer.parseInt(t.replaceAll("[/D]", ""));
            feat.editKeyFrame((String) comboShapes.getSelectedItem(), sec, makeArray(true));
            if (checkSel) {
              listenerMotions.remove(comboKey.getIndexOf(comboKey.getSelectedItem()));
              listenerMotions.addElement(makeFrameFormat(makeArray(true)));
            }
            comboKey.removeElement(comboKey.getSelectedItem());
            comboKey.addElement(makeFrameFormat(makeArray(true)));
            sortList(listenerMotions, true);
            sortList(comboKey, false);
          } catch (IllegalArgumentException p) {
            tryAgain("D'oh! Can't edit this motion!!");
          }
          break;
        default:
          tryAgain("How did you even get here?");
      }
    }
  }

  /**
   * Detects change of state.
   *
   * @param e ChangeEvent
   */
  @Override
  public void stateChanged(ChangeEvent e) {
    for (IViewFeatures feat : listeners) {
      if (e.getSource() == spinner) {
        feat.changeSpeed(((Number) spinner.getValue()).intValue());
      }
      if (e.getSource() == slider) {
        feat.scrub(slider, ((Number) slider.getValue()).intValue());
      }
    }
  }

  /**
   * Detects change of value.
   *
   * @param e ListSelectionEvent
   */
  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (e.getValueIsAdjusting()) {
      JList list = (JList) e.getSource();
      String item = (String) list.getSelectedValue();
      currShapeSel = item;
      for (IViewFeatures feat : listeners) {
        listenerMotions.clear();
        for (String in : feat.getControllerMotion(item)) {
          listenerMotions.addElement(in);
        }
      }
    }
  }

  private void tryAgain(String s) {
    JFrame tryAgainWindow = new JFrame();
    JLabel text = new JLabel(s);
    text.setFont(new Font("Impact", Font.BOLD, 16));
    ImageIcon img = new ImageIcon("resources/gentleman-meme.jpg");
    UIManager.put("OptionPane.background", Color.WHITE);
    UIManager.put("Panel.background", Color.WHITE);

    JOptionPane.showMessageDialog(tryAgainWindow, text,
            "What? An Error?", JOptionPane.ERROR_MESSAGE, img);
  }

  private String makeFrameFormat(int[] work) {
    Objects.requireNonNull(work);
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("t=%d, pos=(%d,%d), size=(%d,%d), color=(%d,%d,%d)",
            work[0], work[1], work[2], work[3], work[4],
            work[5], work[6], work[7]));
    return sb.toString();
  }


  private void sortList(ListModel<String> dm, boolean b) {
    String temp;
    String[] ob = new String[dm.getSize()];
    for (int i = 0; i < dm.getSize(); i++) {
      ob[i] = dm.getElementAt(i);
    }
    int n = ob.length;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (Integer.parseInt(ob[j].split(",", 2)[0].replaceAll("[/D]",
                "")) > Integer.parseInt(ob[j + 1].split(",", 2)[0]
                .replaceAll("[/D]", ""))) {
          temp = ob[j];
          ob[j] = ob[j + 1];
          ob[j + 1] = temp;
        }
      }
    }

    if (b) {
      ((DefaultListModel<String>) dm).removeAllElements();
      for (String i : ob) {
        ((DefaultListModel<String>) dm).addElement(i);
      }
    } else {
      ((DefaultComboBoxModel<String>) dm).removeAllElements();
      for (String i : ob) {
        ((DefaultComboBoxModel<String>) dm).addElement(i);
      }
    }

  }

  private int[] makeArray(boolean b) {
    if (b) {
      return new int[]{Integer.valueOf(eName.getText()),
              Integer.valueOf(eX.getText()), Integer.valueOf(eY.getText()),
              Integer.valueOf(eWidth.getText()), Integer.valueOf(eHeight.getText()),
              currShapeColor.getRed(), currShapeColor.getGreen(), currShapeColor.getBlue()};

    } else {
      return new int[]{Integer.valueOf(aName.getText()),
              Integer.valueOf(aX.getText()), Integer.valueOf(aY.getText()),
              Integer.valueOf(aWidth.getText()), Integer.valueOf(aHeight.getText()),
              currShapeColor.getRed(), currShapeColor.getGreen(), currShapeColor.getBlue()};
    }
  }
}