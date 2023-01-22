package interface_graphique;

import java.util.Objects;

public class Coordonnes2D {
    public int x;
    public int y;

    public Coordonnes2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordonnes2D that = (Coordonnes2D) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
