package expression;

import java.util.Objects;

public class Divide extends Operations implements BothEvaluations {
    public Divide(BothEvaluations a, BothEvaluations b) {
        super(a, b);
    }

    @Override
    public int evaluate(int x) {
        return super.evaluate(x);
    }
    @Override
    public int evaluate(int x, int y, int z) {
        return super.evaluate(x, y, z);
    }
    protected int count(int x, int y) {
        return x / y;
    }
    protected double count(double x, double y) {
        return x / y;
    }


    @Override
    protected int hash() {
        return 4;
    }

    public String toString() {
        return super.toString();
    }

    public String toMiniString() {
        return super.toMiniString();
    }

    public int msGetLevel(){
        return Priority.DIVISION.getLevel();
    }

    public int msGetPriority(){
        return Priority.DIVISION.ordinal();
    }

    @Override
    protected String symbol() {
        return "/";
    }

    @Override
    public boolean equals(Object e){
        return super.equals(e);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
