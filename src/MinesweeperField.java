import java.util.*;

public class MinesweeperField {
    private int horBlocks;
    private int verBlocks;
    private int noOfBombs;
    private int[][] field;
    private int[][] cover;

    /**
     * Block statuses:
     * 0 = No bomb, open block
     * 1 = 1 bomb around
     * 2 = 2 bombs around
     * 3 = 3 bombs around
     * 4 = 4 bombs around
     * 5 = 5 bombs around
     * 6 = 6 bombs around
     * 7 = 7 bombs around
     * 8 = 8 bombs around
     * 9 = A bomb on this block
     * 10 = Closed block
     * 11 = Flagged closed block
     */

    public MinesweeperField(int horBlocks, int verBlocks, int noOfBombs) {
        //TODO check horBlocks and verBlocks for invalid values
        this.horBlocks = horBlocks;
        this.verBlocks = verBlocks;

        int totalAmountOfBlocks = (horBlocks*verBlocks);

        //TODO check for invalid amount of bombs
        if (noOfBombs > totalAmountOfBlocks) {
            System.out.println(noOfBombs +
                    " is too many bombs, setting the bomb amount to the maximum ("
                    + totalAmountOfBlocks + ")");
            this.noOfBombs = totalAmountOfBlocks;
        }

        //Create the mine field
        int[][] field = new int[horBlocks][verBlocks];

        //Set all blocks to no bombs, iterating through each row
        for (int i = 0; i < horBlocks; i++) {
            Arrays.fill(field[i], 0);
        }

        //Generate random places for the bombs
        Set<Integer> bombs = generateUniqueRandomNumbers(0, totalAmountOfBlocks-1, noOfBombs);

        //Place the bombs and increase the status of the surrounding blocks
        for (int i = 0; i < horBlocks; i++) {
            for (int j = 0; j < verBlocks; j++) {
                if (bombs.contains((i*horBlocks) + j)) {
                    field[i][j] = 9;

                    ArrayList<Pair> surroundingBlocks = getSurroundingBlocks(i,j,horBlocks-1,verBlocks-1);
                    for (Pair coordsForSurrBlocks : surroundingBlocks) {
                        //If the doesn't have a bomb, increase it's nearby bomb indicator
                        if (field[coordsForSurrBlocks.a][coordsForSurrBlocks.b] != 9) {
                            field[coordsForSurrBlocks.a][coordsForSurrBlocks.b]++;
                        }
                    }
                }
            }
        }

        this.field = field;

        createCover();
    }

    public MinesweeperField(int horBlocks, int verBlocks, double percentageOfBombs) {
        //TODO check for invalid percentage
        this(horBlocks, verBlocks, (int)((double)horBlocks*(double)verBlocks*percentageOfBombs));
        //System.out.println((int)((double)horBlocks*(double)verBlocks*percentageOfBombs));
    }

    private Set<Integer> generateUniqueRandomNumbers(int from, int to, int amount) {
        //https://stackoverflow.com/questions/8115722/generating-unique-random-numbers-in-java

        //TODO check for invalid amount
        if (amount > (to - from + 1)) {
            System.out.println("Invalid amount " + amount + ": cannot be out of range from " + from + " to " + (to+1));
            return null;
        }

        //Add all the possible numbers to an ArrayList
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            list.add(i);
        }

        //Shuffle the ArrayList
        Collections.shuffle(list);

        //Put the first n numbers into a Set
        Set<Integer> numbers = new HashSet<>();
        for (int i = 0; i < amount; i++) {
            numbers.add(list.get(i));
        }

        return numbers;
    }

    public ArrayList<Pair> getSurroundingBlocks(int indexX, int indexY, int maxXindex, int maxYindex) {
        ArrayList<Pair> coordinates = new ArrayList<>();

        for (int i = indexX-1; i <= indexX+1; i++) {
            for (int j = indexY-1; j <= indexY+1; j++) {
                //Check for surrounding block that are at the edges
                if (i >= 0 && i <= maxXindex && j >= 0 && j <= maxYindex) {
                    coordinates.add(new Pair(i, j));
                }
            }
        }
        return coordinates;
    }

    public String toString() {
        return Arrays.deepToString(field).replace("], ", "]\n").replace("]]", "]").replace("[[", "[");
    }

    public int[][] getField() {
        return field;
    }

    public int[][] createCover() {
        //Create a new field
        int[][] cover = new int[horBlocks][verBlocks];

        //Set all blocks to closed blocks
        for (int i = 0; i < horBlocks; i++) {
            Arrays.fill(field[i], 10);
        }

        return cover;
    }
}