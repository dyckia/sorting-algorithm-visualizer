import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class SortingVisualizer {
    // frame properties
    private JFrame frame;
    private final int WIDTH = 835;
    private final int HEIGHT = 650;

    // array properties
    private float[] array;
    private int arraySize = 50;

    // right column: canvas
    Canvas canvas = new Canvas();

    // left column: control panel
    JPanel controlPanel = new JPanel();
    JButton sortButton = new JButton("Start Sorting");
    JButton shuffleButton = new JButton("Shuffle Array");
    // algorithm selection dropdown menu
    JLabel algoLabel = new JLabel("Algorithm");
    private int algoIndex = 0;
    private final String[] algorithms = {"Bubble Sort", "Insertion Sort",
                                   "Merge Sort", "Heap Sort", "Quick Sort"};
    JComboBox algoComboBox = new JComboBox(algorithms);
    // speed slider
    JLabel speedLabel = new JLabel("Speed:");

    JLabel speedValueLabel = new JLabel(Integer.toString(canvas.speed));
    JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, canvas.speed);
    // array size slider
    JLabel sizeLabel = new JLabel("Size:");
    JLabel sizeValueLabel = new JLabel(arraySize + " ");
    JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 10, 200, arraySize);
    // algorithm analysis text
    private final String[] analysisText = {"Bubble Sort\n\nTime\nAverage: n^2\nBest Case: n\nWorst Case: n^2\n\nSpace: 1\n\nStable: Yes",
                                     "Insertion Sort\n\nTime\nAverage: n^2\nBest Case: n\nWorst Case: n^2\n\nSpace: 1\n\nStable: Yes",
                                     "Merge Sort\n\nTime\nAverage: nlogn\nBest Case: nlogn\nWorst Case: nlogn\n\nSpace: n\n\nStable: Yes",
                                     "Heap Sort\n\nTime\nAverage: nlogn\nBest Case: nlogn\nWorst Case: nlogn\n\nSpace: 1\n\nStable: No",
                                     "Quick Sort\n\nTime\nAverage: nlogn\nBest Case: nlogn\nWorst Case: n^2\n\nSpace: logn\n\nStable: No"};
    JTextArea analysisTextArea = new JTextArea(analysisText[algoIndex]);
    String creditText = "Built by Jason Feng.";
    JLabel creditLabel = new JLabel(creditText);
    // border style
    Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);


    public static void main(String[] args) {
        new SortingVisualizer();
    }

    // constructor
    public SortingVisualizer() {
        shuffleArray();
        initialize();
    }

    private void shuffleArray() {
        createArray();
        // swap i, j with any numbers in the array
        int middle =arraySize / 2;
        for (int i = 0, j = middle; i < middle; i++, j++) {
            int randomIndex = new Random().nextInt(arraySize);
            Helper.swap(i, randomIndex, array);
            randomIndex = new Random().nextInt(arraySize);
            Helper.swap(j, randomIndex, array);
        }
    }

    private void createArray() {
        array = new float[arraySize];
        float step = (float)canvas.CANVAS_HEIGHT / arraySize;
        for (int i = 0; i < arraySize; i++) {
            array[i] = (i + 1) * step;
        }
    }

    private void initialize() {
        initFrame();
        initCanvasPanel();
        initControlPanel();
    }

    private void initFrame() {
        // initialize frame
        frame = new JFrame();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(WIDTH,HEIGHT);
        frame.setTitle("Sorting Algorithms Demystified");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
    }

    private void initCanvasPanel() {
        // initialize the canvas panel
        canvas.setBounds(225, 10, canvas.CANVAS_WIDTH, canvas.CANVAS_HEIGHT);
        frame.getContentPane().add(canvas);
    }

    private void initControlPanel() {
        // initialize the control panel
        controlPanel.setBorder(BorderFactory.createTitledBorder(border,"Controls"));
        controlPanel.setLayout(null);
        controlPanel.setBounds(10, 10, 210, 600);
        int yValue = 40;
        int margin = 45;

        // sort button
        sortButton.setBounds(35, yValue, 130, 25);
        controlPanel.add(sortButton);
        yValue += margin;
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if sorting is already in process, do nothing
                if (!canvas.isSorting) {
                    sort();
                }
            }
        });

        // shuffle button
        shuffleButton.setBounds(35, yValue, 130, 25);
        controlPanel.add(shuffleButton);
        yValue += margin;
        shuffleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // prevent shuffling array while sorting
                if (!canvas.isSorting) {
                    shuffleArray();
                    canvas.resetPointers();
                    canvas.repaint();
                }
            }
        });

        // algorithm selection box
        algoLabel.setBounds(35, yValue, 130, 25);
        controlPanel.add(algoLabel);
        yValue += 25;
        algoComboBox.setBounds(35, yValue, 130, 25);
        controlPanel.add(algoComboBox);
        yValue += margin + 5;
        algoComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                shuffleArray();
                canvas.resetPointers();
                canvas.repaint();
                algoIndex = algoComboBox.getSelectedIndex();
                analysisTextArea.setText(analysisText[algoIndex]);
            }
        });

        // speed slider
        speedLabel.setBounds(15, yValue, 50, 25);
        controlPanel.add(speedLabel);
        speedSlider.setMajorTickSpacing(5);
        speedSlider.setBounds(55, yValue, 105, 25);
        controlPanel.add(speedSlider);
        speedValueLabel.setBounds(160, yValue, 50, 25);
        controlPanel.add(speedValueLabel);
        yValue += margin;
        speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                canvas.speed = speedSlider.getValue();
                speedValueLabel.setText(Integer.toString(canvas.speed));
            }
        });

        // array size slider
        sizeLabel.setBounds(15, yValue, 50, 25);
        controlPanel.add(sizeLabel);
        sizeSlider.setMajorTickSpacing(10);
        sizeSlider.setBounds(55, yValue, 105, 25);
        controlPanel.add(sizeSlider);
        sizeValueLabel.setBounds(160, yValue, 50, 25);
        controlPanel.add(sizeValueLabel);
        yValue += margin + 5;
        sizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // prevent changing array size while sorting
                if (!canvas.isSorting) {
                    arraySize = sizeSlider.getValue();
                    sizeValueLabel.setText(arraySize + " ");
                    shuffleArray();
                    canvas.resetPointers();
                    canvas.repaint();
                }
            }
        });

        // analysis text area
        analysisTextArea.setBounds(15, yValue, 180, 200);
        analysisTextArea.setEditable(false);
        controlPanel.add(analysisTextArea);
        yValue += 180 + 30;

        // credit text
        creditLabel.setBounds(40, yValue, 180, 25);
        controlPanel.add(creditLabel);

        frame.getContentPane().add(controlPanel);
    }

    // decided which sorting algorithm to use
    private void sort() {
        switch(algoIndex) {
            case 0:
                Algorithms.bubbleSort(array, canvas);
                break;
            case 1:
                Algorithms.insertionSort(array, canvas);
                break;
            case 2:
                Algorithms.mergeSort(array, canvas);
                break;
            case 3:
                Algorithms.heapSort(array, canvas);
                break;
            case 4:
                Algorithms.quickSort(array, canvas);
                break;
        }
    }


    // sub-class canvas
    class Canvas extends JPanel {
        // canvas size
        protected final int CANVAS_WIDTH = 600;
        protected final int CANVAS_HEIGHT = 600;
        // dividing position between sorted and unsorted
        protected int dividePosition = -1;
        // current bar
        protected int currentBar = -1;
        // state flags
        protected boolean isSorting = false;
        protected int speed = 86;


        // constructor
        public Canvas() {
            setBackground(Color.DARK_GRAY);
        }

        // paint the graph
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // cast g to Graphics2D to allow float height
            Graphics2D g2d = (Graphics2D) g;
            Rectangle2D.Float bar;
            float barWidth = (float) CANVAS_WIDTH / arraySize;
            for (int i = 0; i < arraySize; i++) {
                float barHeight = array[i];
                // fill bar
                g2d.setColor(Color.CYAN);

                if (dividePosition > -1 && i == dividePosition) {
                    g2d.setColor(Color.ORANGE);
                }

                if (currentBar > -1 && i == currentBar) {
                    g2d.setColor(Color.RED);
                }

                bar = new Rectangle2D.Float(i * barWidth, CANVAS_HEIGHT - barHeight, barWidth, barHeight);
                g2d.fill(bar);
                // draw bar border
                g2d.setColor(Color.DARK_GRAY);
                bar = new Rectangle2D.Float(i * barWidth, CANVAS_HEIGHT - barHeight, barWidth, barHeight);
                g2d.draw(bar);
            }
        }

        // reset canvas pointers
        protected void resetPointers() {
            dividePosition = -1;
            currentBar = -1;
        }

    }

}
