lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

variable(Name, variable(Name)) .
%:- atom_chars(Name, C1), 
const(Value, const(Value)).

op_add(A, B, operation(op_add, A, B)).
op_subtract(A, B, operation(op_subtract, A, B)).
op_multiply(A, B, operation(op_multiply, A, B)).
op_divide(A, B, operation(op_divide, A, B)).
op_neg(A, operation(op_neg, A)).

op_and(A, B, operation(op_and, A, B)).
op_or(A, B, operation(op_or, A, B)).
op_xor(A, B, operation(op_xor, A, B)).
op_not(A, operation(op_not, A)).

%op_impl(A, B, operation(op_impl, A, B)).
%op_iff(A, B, operation(op_iff, A, B)).

operation(op_add, A, B, R) :- R is A + B.
operation(op_subtract, A, B, R) :- R is A - B.
operation(op_multiply, A, B, R) :- R is A * B.
operation(op_divide, A, B, R) :- R is A / B.
operation(op_negate, A, R) :- R is (-A).

is_pos(A, N) :- A > 0, N is 1.
is_pos(A, N) :- A =< 0, N is 0.

operation(op_and, A, B, R) :- is_pos(A, R1), is_pos(B, R2), R is R1 /\ R2, !.
operation(op_or, A, B, R) :- is_pos(A, R1), is_pos(B, R2), R is R1 \/ R2, !.
operation(op_xor, A, B, R) :- is_pos(A, R1), is_pos(B, R2), R3 is R1 + R2, R is R3 mod 2, !.

operation(op_not, A, R) :- A > 0, R is 0.
operation(op_not, A, R) :- A =< 0, R is 1.

operation(op_impl, A, B, R) :- operation(op_not, A, A1), operation(op_or, A1, B, R).
operation(op_iff, A, B, R) :- operation(op_xor, 1, A, R1), operation(op_xor, R1, B, R).

evaluate(const(Value), _, Value).
evaluate(variable(Name), Vars, R) :- atom_chars(Name, [NX | _]), lookup(NX, Vars, R).

evaluate(operation(Op, A, B), Vars, R) :- 
    evaluate(A, Vars, AV), 
    evaluate(B, Vars, BV), 
    operation(Op, AV, BV, R).
evaluate(operation(Op, A), Vars, R) :- 
    evaluate(A, Vars, AV), 
    operation(Op, AV, R).


nonvar(V, _) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).

:- load_library('alice.tuprolog.lib.DCGLibrary').

%expr_p(variable(Name)) --> { member(Name, [x, y, z, "X", "Y", "Z"]) }, [Name].


expr_p(variable(Value)) -->
  { nonvar(Value, atom_chars(Value, Chars)) },
  get_var(Chars),
  { Chars = [_ | _], atom_chars(Value, Chars) }.

is_var(H) --> { member(H, [x, y, z, 'X', 'Y', 'Z']) }, [H].
get_var([H]) --> is_var(H).
get_var([H | T]) --> is_var(H), get_var(T).


expr_p(const(Value)) -->
  { nonvar(Value, number_chars(Value, Chars)) },
  digits_p(Chars),
  { Chars = [_ | _], number_chars(Value, Chars) }.

is_number(H) --> { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.']) }, [H].
get_number([H]) --> is_number(H).
get_number([H | T]) --> is_number(H), get_number(T).

digits_p(Chars) --> get_number(Chars).
digits_p(['-' | Abs]) --> ['-'], get_number(Abs).

op_p(op_add) --> ['+'].
op_p(op_subtract) --> ['-'].
op_p(op_multiply) --> ['*'].
op_p(op_divide) --> ['/'].
op_p(op_negate) --> ['n', 'e', 'g', 'a', 't', 'e'].


op_p(op_impl) --> ['-', '>'].
op_p(op_iff) --> ['<', '-', '>'].

op_p(op_and) --> ['&', '&'].
op_p(op_or) --> ['|', '|'].
op_p(op_xor) --> ['^', '^'].
op_p(op_not) --> ['!'].


expr_p(operation(Op, A, B)) -->
  { var(Op) -> ExpGap = []; ExpGap = [' '] },
  ['('], expr_p(A), ExpGap, op_p(Op), ExpGap, expr_p(B), [')'].
  
expr_p(operation(Op, A)) --> 
  { var(Op) -> ExpGap = []; ExpGap = [' ']}, 
  op_p(Op), ExpGap, expr_p(A).

delete_dublicates( _, [], []).
delete_dublicates( R, [R|T], T2) :- delete_dublicates( R, T, T2).
delete_dublicates( R, [H|T], [H|T2]) :- H \= R, delete_dublicates( R, T, T2).

infix_str(E, A) :- ground(E), phrase(expr_p(E), C), atom_chars(A, C), !.
infix_str(E, A) :-   atom(A), atom_chars(A, C1), delete_dublicates(' ', C1, C), phrase(expr_p(E), C), !.