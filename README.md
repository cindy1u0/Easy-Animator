# Design Choices and Decisions:
The project provides an easy animator which converts motions in texts to an animation as well as
allowing the user to edit the animation. This program was finished with a friend in CS 3500
(Object Oriented Design) at Northeastern.


## Model Design Choices and Decisions:

For this assignment, we created 3 interfaces:
>
* AnimationModel
* Shapes
* Command

to represent the
the main methods that are used to set up the model. **AnimationModelImpl** implements **AnimationModel**
to write the methods that are in the interface.There are two abstract classes for shape
interface and command interface. We decided to have those abstract classes because there can be
different shapes and commands that are being used in this model while they share some common field
representations as their abstract classes. Classes
 >
 * Rect
 * Oval

are instances of the
shape abstract class, and commands
 >
 * Move
 * Resize
 * ChangeColor

are instances of the
command abstract class. We decided to write commands as classes because later
if the user wants to add another operation, they can create a new class using command pattern
instead of adding methods to the class where contains all the motions.

Moving forward, there are also some Utility classes
>
* Posn2D
* RGB
* Size

since the built-in java classes take in integer and their getters return integers
instead of double, which later on will cause some rounding error. Tree comparator class is used for
the TreeMap in the shape class.

Our **AnimationModelImpl** takes in a list of shapes and a list of commands. Commands are independent
from shapes because a shape can exist without a command, but a command cannot without a shape. Our
Impl class implements methods from the interface such as get state which prints out the state as
well as editing features such as add shape, remove shape, add command, and remove command, from
which the user can edit their motions according to the change in what they want. To print out the
state, we first loop through the list of commands using method
```java
void follow();
```
to modify the state of each interval for the shape in
```java
TreeMap<Interval, double[]> history;
```

Next, simply loop through the list of shapes' histories to print out the state corresponding to
each interval.

The shape model consists name, current position, size, color, shape type, appearing time,
disappearing time as well as history. The name field and shape type ensures the uniqueness of each
shape. Position, size, and color can be modified depends on which time it is at right now. The
method **follow()** in command class provides the modification. More importantly, the history is
represented with a treeMap, a sorted map that sorts the interval. Therefore, when we print out the
state, we can simply print out the arrays in order of the keys and they will be sored.It consists an
array that corresponds a certain interval representing the modified position, size, and color.
Therefore, whenever we need to print out the state, we can use interval as the key to get the
corresponding status of the shape from the treeMap.

Additionally, the method
```java
void checkGap();
```
fills up the gaps that no movement is made and nothing is changing while still appearing. (HOWEVER, WHILE
WE ARE DOING THE VIEW DESIGN, WE DECIDED TO GET RID OF APPEARING AND DISAPPEARING TIME BECAUSE THEY
ARE NOT NECESSARY SINCE THE FILE WILL GIVE YOU EXACTLY WHERE THE FIRST MOVEMENT STARTS.)

The shape model doesn't have the field for command because we try to keep it independent from
commands, but also since we have a list of commands in our animation model class, it will be
redundant to have another one here.

The command model follows the command pattern. It takes in the shape and the interval representing
when and to which shape the command is doing the job for. Its methods follow the command pattern to
modify the current state to the desired state. Additionally, it can also get access to the state
at a particular second by using for loop to loop through each state. However, in get description,
it will only print out the last state because it stops at the ending time of the interval.



## View Design:

For the view design, first we used the builder method that the assignment provided because this way
it is easier to read the information from the file. In our builder, for setBound, we simply modify
the canvas size; for declareShapes, we put it into our list of shapes so that they can be used later
on; for addMotions, instead of converting back to motion and calling the methods that involve loops
again, we simply store them into the corresponding shapes' history so that each has the interval as
well as the initial states and final states. For view, according the requirements, we have three
views:
>
* Text
* SVG
* Visual

Because of that, we decided to have an interface
IView that contains methods following the command pattern.

Since three of the views have similar fields such as canvas and output text, we also used an
abstract class that implement the same methods. For visual and svg classes, we have the tempo field
because only those classes care about the speed of the animation. The main method, render, only
takes in a list of IReadOnlyShapes because the shapes in the list have all the necessary data. The
reason why we used IReadOnlyShapes since we didn't want to mutate the shapes' positions, colors,
and sizes. Instead, IReadOnlyShapes will prevent the mutation since it only has all the non void
methods.

### TextView
The method **.render(IReadOnlyShapes shapes)** will take in a list of IReadOnlyShapes and appends the output to the
StringBuilder to show the desired output. The reason we used StringBuilder because it has better
performance than using String. Similar to the method getState in the AnimationModel, we used similar
code to append the output to the StringBuilder. And obviously, since tempo doesn't affect this, it
will always be 0.

### SVGView
Since tempo has an effect on how fast the animation will be when opening the svg
file, it also takes in tempo as a field. For render, there are a few of helper methods in the
Shapes class that format the Shapes to svg tags. However, each of the helper methods only return
either IReadOnlyShapes or a list of IReadOnlyShapes because we don't want the original shapes to be
mutated. Helper methods in the Shapes class take in speed as a parameter because the beginning and
duration time change according to how fast the user wants the animation to be.

### VisualView
We used another interface **IDrawing** with the draw method to draw the shapes according
what it looks like. Therefore, in our VisualView class, it also takes in the Drawing class as a
field because it extends JPanel that we can use to setBackground and size etc. In the visual class,
we initialize the panel size and other conditions in the constructor. And for the render method, we
just needed to call the draw method from **IDrawing** to draw all the shapes instead of doing all the
drawing things under that method. Additionally, the tempo will have an effect for the visual
animation, so it also takes the tempo as a field.

Before doing the main method, we also have a **FactoryView** to switch which view to use in the main
methods instead of switching there. For our main method, we initialized in, out, view, and speed
to default. Whenever there are some other directions in the command line, we will change the default
values accordingly. Instead of output the error message, we used the JOptionPane to create a window
showing the error messages.


For the sake of rotation, we used the method
```java
void shape.getRotation();
```
to rotate the shape for the visual view. We also added another value for the current degree relative
to the original in the **TextView** as well.


## Editor Design:

For the EditorView, using delegation, we implemented the class to **IView** instead of extending the
visual view class. Therefore, in our editor view, we took in a visual view as a delegate. We used
delegation to override the following methods from **IView**.
```java
void render(List<IReadOnlyShapes>);
void setCanvas(Screen);
void getText();
void getTempo();
```

The panel of the **EditorView** includes actual animation panel, control menu bar for applications
including restart, start, pause, and resume, east panel to add or remove shapes, or add or remove
motions, and bottom panel to adjust speed and select if loop. Each panel has its own job so that
the user knows exactly what to change and where to do those changes.

We used listeners including ActionListener for control menu and buttons, changeValueListener for
motion list that corresponds to the selected shapes, and stateChangeListener for selecting loop.
We used switch statement for actionListener because they consist of many cases. Whenever adding or
removing a shape, the shape list as well as the combo box will show immediately of the changes, and
same as the motion list when adding, removing, or editing a motion.

More importantly, since we are using key frames, we changed our model a little bit. In animation
model, when using builder pattern, we also added key frames to the shapes when adding a motion to
the shapes. Therefore, in our shape class, we have another field for storing key frames. This field
is a **Hashmap** that maps current tick to an array of doubles that indicate the current state. With
tweening, instead of using intervals to tween, we used keyframes to tween each tick with
corresponding shape state. Thus, when playing the animation, the animation is produced using the
keyframes. We basically combined two keyframes to make it an interval. It's very similar to
interval tweening, but keyframes make it easier for add, remove, and edit keyframe features in the
EditorView.

## Run Configuration
In order to run the program, the user can download the **Animator.jar** from the folder **resources** as well as all the text files from the folder **demos**. Then, put all the files in a new folder, type out the configuration in the command-prompt/terminal. In the run configuration, the user can also specify command-line arguments, such as the file you want to read in, the location you want the output to be printed, the view name you want to use, and the speed of the animation. The options for the view name are "text," "visual," "edit," and "svg". For example,
```
java -jar Animator.jar -in smalldemo.txt -speed 50 -view visual -out out.txt
```
This command will use smalldemo.txt for the animation file with its output going to the file out.txt, and create a visual view to show the animation at a speed of 50 ticks per second.
