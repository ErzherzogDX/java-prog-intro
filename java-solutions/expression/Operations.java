package expression;

import java.util.Objects;

public abstract class Operations implements BothEvaluations {

    public final BothEvaluations a;
    public final BothEvaluations b;
    protected Operations(BothEvaluations a, BothEvaluations b) {
        this.a = a;
        this.b = b;
    }
    public BothEvaluations getA(){
        return a;
    }
    public BothEvaluations getB(){
        return b;
    }

    protected abstract int count(int left, int right);
    protected abstract double count(double left, double right);

    protected abstract int hash();
    protected abstract String symbol();

    public boolean equals(Object e){
        if(e == null || !(e.getClass().equals(this.getClass()))){
            return false;
        } else{
            return (((Operations) e).getA().equals(a)) && (((Operations) e).getB().equals(b));
        }
    }

    public int evaluate(int x) {
        return count(a.evaluate(x) , b.evaluate(x));
    }
    public int evaluate(int x, int y, int z) {
        return count(a.evaluate(x,y,z) , b.evaluate(x,y,z));
    }
    public double evaluate(double x) {
        return count(a.evaluate(x) , b.evaluate(x));
    }


    public String toString() {
        return "(" + a.toString() + " " + symbol() + " " + b.toString() + ")";
    }

    public abstract int msGetLevel();
    public abstract int msGetPriority();

    public String toMiniString() {
        int al = a.msGetLevel();
        int bl = b.msGetLevel();
     //   int ap = a.msGetPriority();
        int bp = b.msGetPriority();

        String left = a.toMiniString();
        String right = b.toMiniString();

        boolean isCommutative = false;
        if(msGetLevel() == 1 || (msGetLevel() == 3 && msGetPriority() == 0)){
            isCommutative = true;
        }

        if(al < 3 && msGetLevel() >= 3){
            left = "(" + left + ")";
        }

        if((bl > msGetLevel())){

        }
        else{
            if(isCommutative && (bp == msGetPriority())){

            } else {
                right = "(" + right + ")";
            }
        }

        return left + " " + symbol() + " " + right;
    }

    public int hashCode() {
        return Objects.hash(getA(), getB(), hash());
    }

}
