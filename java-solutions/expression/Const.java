package expression;

import java.util.Objects;

public class Const implements BothEvaluations {
     //int i = 0;
     Number i = 0;
    public Const(int i) {
        this.i = i;

    }
    public Const(double j) {
        i = j;
    }

    private int getConst(){
        return (int)i;
    }

    @Override
    public int evaluate(int x) {
        return (int)i;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int)i;
    }

    @Override
    public double evaluate(double x) {
        return (double)i;
    }

    public String toString() {
        return i.toString();
    }

    public String toMiniString() {
        return i.toString();
    }

    public int msGetLevel(){
        return Priority.CONSTVAR.getLevel();
    }

    public int msGetPriority(){
        return Priority.CONSTVAR.ordinal();
    }

    @Override
    public boolean equals(Object e){
        if(!(e instanceof Const)){
            return false;
        } else{
            return Objects.equals(i, ((Const) e).i);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(i) * 13;
    }
}
