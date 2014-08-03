package be.crydust.measure;

import java.util.Objects;

public class Dimension implements Comparable<Dimension> {

    private final String description;
    private final String symbol;
    
    Dimension(String description, String symbol) {
        assert description != null && !description.isEmpty();
        assert symbol != null && !symbol.isEmpty();
        this.description = description;
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.symbol);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dimension other = (Dimension) obj;
        return Objects.equals(this.symbol, other.symbol);
    }

    @Override
    public int compareTo(Dimension other) {
        Objects.requireNonNull(other);
        return this.symbol.compareTo(other.symbol);
    }
    
    @Override
    public String toString() {
        return "Dimension{" + "symbol=" + symbol + '}';
    }

}
