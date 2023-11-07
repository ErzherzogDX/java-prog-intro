package expression;

import java.util.Objects;

public class Subtract extends Operations implements BothEvaluations {

    public Subtract(BothEvaluations a, BothEvaluations b) {
        super(a, b);
    }
    @Override
    public int evaluate(int x) {
        return super.evaluate(x);
    }
    protected int count(int x, int y) {
        return x - y;
    }
    protected double count(double x, double y) {
        return x - y;
    }

    @Override
    protected int hash() {
        return 2;
    }

    public String toString() {
        return super.toString();
    }
    @Override
    protected String symbol() {
        return "-";
    }

    @Override
    public boolean equals(Object e){
        return super.equals(e);
    }
    @Override
    public String toMiniString() {
        return super.toMiniString();
    }

    public int msGetLevel(){
        return Priority.SUBTRACTION.getLevel();
    }

    public int msGetPriority(){
        return Priority.ADDSUB.ordinal();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return super.evaluate(x, y, z);
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
