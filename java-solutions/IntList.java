import java.io.*;
import java.util.*;

public class IntList {
    public IntList() {
    }

    private int numOfPositions = 320;
    private int[] positionsList = new int[numOfPositions];

    private int positionIndex = 0; //Позиция в списке номеров вхождений
    private int numOfOccurrences = 0; //число вхождений

    public void set(int position) {
        if (positionIndex < numOfPositions) {
            positionsList[positionIndex] = position;
            positionIndex++;
            numOfOccurrences++;
        } else {
            duplication();
            set(position);
        }
    }

    public void set(int position, int nstring) {
        if (positionIndex < numOfPositions) {
            positionsList[positionIndex] = nstring;
            positionsList[positionIndex + 1] = position;

            positionIndex += 2;
            numOfOccurrences++;
        } else {
            duplication();
            set(position, nstring);
        }
    }

    private void duplication() {
        numOfPositions = 2 * numOfPositions;
        positionsList = Arrays.copyOf(positionsList, numOfPositions);
    }

    public int[] getList() {
        return positionsList;
    }

    public int getNumOfOccurrences() {
        return numOfOccurrences;
    }

}