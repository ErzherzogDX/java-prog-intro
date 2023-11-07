package expression;

public interface BothEvaluations extends Expression, TripleExpression, DoubleExpression{
    int msGetPriority();
    int msGetLevel();
}
