public class Cell {
    private int currentI;
    private int currentJ;

    Cell() {
        this.currentI = 0;
        this.currentJ = 0;
    }

    Cell(Cell cc) {
        this.currentI = cc.getCurrentI();
        this.currentJ = cc.getCurrentJ();
    }

    @Override
    public String toString() {
        return "Cell{" + currentI +
                ", " + currentJ +
                '}';
    }

    int getCurrentJ() {
        return currentJ;
    }

    void setCurrentJ(int currentJ) {
        this.currentJ = currentJ;
    }

    int getCurrentI() {
        return currentI;
    }

    void setCurrentI(int currentI) {
        this.currentI = currentI;
    }
}