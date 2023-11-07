package expression;

public class Add extends Operations implements BothEvaluations {
    public Add(BothEvaluations a, BothEvaluations b) {
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
    @Override
    public double evaluate(double x) {
        return super.evaluate(x);
    }

    public String toString() {
        return super.toString();
    }
    @Override
    public String toMiniString() {
        return super.toMiniString();
    }

    public int msGetLevel(){
        return Priority.ADDSUB.getLevel();
    }

    public int msGetPriority(){
        return Priority.ADDSUB.ordinal();
    }

    @Override
    public boolean equals(Object e){
        return super.equals(e);
    }

    protected int count(int x, int y) {
        return x + y;
    }

    @Override
    protected double count(double x, double y) {
        return x + y;
    }

    @Override
    protected int hash() {
        return 1;
    }
    @Override
    protected String symbol() {
        return "+";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}
