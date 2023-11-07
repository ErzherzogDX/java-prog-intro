package expression;

import java.util.Objects;

public class Multiply extends Operations implements BothEvaluations {

    public Multiply(BothEvaluations a, BothEvaluations b) {
        super(a, b);
    }

    @Override
    public int evaluate(int x) {
        return super.evaluate(x);
    }
    @Override
    protected int count(int x, int y) {
        return x * y;
    }
    @Override
    protected double count(double x, double y) {
        return x * y;
    }

    @Override
    protected int hash() {
        return 3;
    }

    public String toString() {
        return super.toString();
    }
    @Override
    protected String symbol() {
        return "*";
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
        return Priority.MULTIPLY.getLevel();
    }

    public int msGetPriority(){
        return Priority.MULTIPLY.ordinal();
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
