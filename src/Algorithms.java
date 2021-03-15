import javax.swing.*;

public class Algorithms {

    protected static void bubbleSort(float[] array, SortingVisualizer.Canvas canvas) {
        SwingWorker<Void, Void> sorter = new SwingWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                int c = 1;
                canvas.isSorting = true;
                int len = array.length;
                boolean swaped = true;
                while (swaped) {
                    canvas.dividePosition = len - c;
                    swaped = false;
                    for (int i = 0; i < len - c; i++) {
                        canvas.currentBar = i + 1;
                        if (array[i] > array[i + 1]) {
                            Helper.swap(i, i + 1, array);
                            swaped = true;
                        }
                        canvas.repaint();
                        // 100 - canvas.speed is delay in millisecond
                        Thread.sleep(100 - canvas.speed);
                    }
                    c++;
                }
                canvas.resetPointers();
                canvas.repaint();
                canvas.isSorting = false;
                return null;
            }
        };
        sorter.execute();
    }


    protected static void insertionSort(float[] array, SortingVisualizer.Canvas canvas) {
        SwingWorker<Void, Void> sorter = new SwingWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                canvas.isSorting = true;
                int len = array.length;
                for (int i = 0; i < len; i++) {
                    canvas.dividePosition = i;
                    int j = i;
                    while (j - 1 >= 0 && array[j] < array[j - 1]) {
                        canvas.currentBar = j - 1;
                        Helper.swap(j, j - 1, array);
                        j--;
                        canvas.repaint();
                        Thread.sleep(100 - canvas.speed);
                    }
                }
                canvas.resetPointers();
                canvas.repaint();
                canvas.isSorting = false;
                return null;
            }
        };
        sorter.execute();
    }


    protected static void mergeSort(float[] array, SortingVisualizer.Canvas canvas) {
        SwingWorker<Void, Void> sorter = new SwingWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                canvas.isSorting = true;
                int len = array.length;
                canvas.resetPointers();
                canvas.repaint();
                canvas.isSorting = false;
                return null;
            }
        };
        sorter.execute();
    }


    protected static void heapSort(float[] array, SortingVisualizer.Canvas canvas) {
        SwingWorker<Void, Void> sorter = new SwingWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                canvas.isSorting = true;
                int len = array.length;
                canvas.resetPointers();
                canvas.repaint();
                canvas.isSorting = false;
                return null;
            }
        };
        sorter.execute();
    }


    protected static void quickSort(float[] array, SortingVisualizer.Canvas canvas) {
        SwingWorker<Void, Void> sorter = new SwingWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                canvas.isSorting = true;
                int len = array.length;
                canvas.resetPointers();
                canvas.repaint();
                canvas.isSorting = false;
                return null;
            }
        };
        sorter.execute();
    }
}
