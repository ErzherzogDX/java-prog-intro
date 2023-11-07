
package expression;

public class Main {
    public static void main(String[] args) {

        double res = new Subtract(
                new Multiply(
                        new Const(12.2),
                        new Variable("x")
                ),
                new Const(3.66)
        ).evaluate(15.3);

        int res2 = new Subtract(
                new Multiply(
                        new Variable("z"),
                        new Variable("x")
                ),
                new Variable("y")
        ).evaluate(15, 3, 2);

        String test = new Subtract(new Add(new Const(2.0), new Const(1.99)), new Add(new Const(2), new Const(1))).toMiniString();
        String ww = new Multiply( new Divide(new Const(2), new Variable("y")),new Variable("x") ).toMiniString();

        boolean r = new Variable("x").equals(new Multiply(new Const(2), new Variable("x")));
        boolean z = new Multiply(new Variable("x"), new Const(2))
                .equals(new Multiply(new Const(3), new Const(2)));

        System.out.println(res);

    }
}
