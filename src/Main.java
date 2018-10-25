public class Main {

    public static void main(String[] args) {
        //TODO solveris
        //TODO tikimybes apskaiciuotu
        //TODO surastu strategija

        long startTime = System.currentTimeMillis();

        MinesweeperField aa = new MinesweeperField(10,10,0.16);
        System.out.println(aa);

        long stopTime = System.currentTimeMillis();
        System.out.println("Time elapsed " + (stopTime - startTime) + " ms");
    }
}
