// helper functions
public class Helper {

    // given two indexes in an array, swap their values
    protected static void swap(int indexA, int indexB, float[] array) {
        float temp = array[indexA];
        array[indexA] = array[indexB];
        array[indexB] = temp;
    }

}
