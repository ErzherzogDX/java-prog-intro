% TASK NO. 13
% Base:

% Building the sieve (prime&composite)
iteration(X, ToAdd, Max) :-
  X < Max,
	X1 is X + ToAdd,
	assert(com_table(X1)),
	iteration(X1, ToAdd, Max).
	
global_iteration(X, Max) :-
  not com_table(X),
  iteration(X, X, Max).
   
global_iteration(X, Max) :-
  X < sqrt(Max),
  X1 is X+1,
  global_iteration(X1, Max).

init(Max) :- global_iteration(2, Max).

composite(X) :- com_table(X).
prime(X) :- not com_table(X).

% Getting list of prime divisors
get_head(N, Div, Res) :-
		Ch is N mod Div,
		Ch = 0,
		Res is Div,
		!.
		
get_head(N, Div, Res) :-
  Div < (N+1),
  NewDiv is Div+1,
  get_head(N, NewDiv, Res).

prime_divisors(1, []) :- !.
prime_divisors(N, [N]) :- prime(N), !.
prime_divisors(N, [H | T]) :- 
	number(N), !,
	get_head(N, 2, H),
	N1 is N / H,
	prime_divisors(N1, T).

product_list([], 1).
product_list([Head], Head).
product_list([Head, Next|Tail], Res) :-
    Next >= Head,
    product_list([Next|Tail], RestProd),
    Res is Head * RestProd.
    
prime_divisors(N, [H | T]) :- 
	product_list([H | T], R),
	N is R.
	

% Modification [Divisors]

map([], _, []).
map([H | T], F, [RH | RT]) :- G =.. [F, H, 0, RH], call(G), map(T, F, RT).


count_dublicates(List, Res) :-
    findall([X, L], (member(X, List), bagof(true, member(X, List), Xs), length(Xs, L)), Res).

clear_dublicates([],[]) :- !.
clear_dublicates([H|T],R) :- member(H,T), !, clear_dublicates(T,R).
clear_dublicates([H|T], [H|R]) :- clear_dublicates(T,R).

% Converting [2,2,3] -> [[2,2],[3,1]]
compact_prime_divisors(N, CDs) :-
		prime_divisors(N, X),
		count_dublicates(X, CD),
		clear_dublicates(CD, CDs).
		
repeat(0, _, []) :- !.
repeat(N,V,[V|T]) :- N1 is N - 1, repeat(N1,V,T), !.

% Converting pair [2, 3] -> [[], [2], [2, 2], [2, 2, 2]] aand

generate_toadd([X, R], R1, []):- R1 is R+1, !.
generate_toadd([X, R], Cnt, [H | T]) :-
	Cnt =< R,
	Cnt1 is Cnt + 1,
	repeat(Cnt, X, H),
	generate_toadd([X,R], Cnt1, T).

% Making the list of all converted pairs
make_lists_gta(Arr, R) :-
		map(Arr, generate_toadd, R).

% Get the cross product of all elements of two lists

concat([], B, B).
concat([H | T], B, [H | R]) :- concat(T, B, R).

merge_lists([], _, []).
merge_lists([X|Xs], Ys, Merged) :-
    merge(X, Ys, MergedX),
    merge_lists(Xs, Ys, RestMerged),
    concat(MergedX, RestMerged, Merged).

merge(_, [], []).
merge(X, [Y|Ys], [XY|Merged]) :-
		 concat(X,Y, XY),
     merge(X, Ys, Merged).

% Getting result
getSum([], Z, _, Z).
getSum([H | T], Z, F, R) :- G =.. [F, Z, H, RH], call(G), getSum(T, RH, F, R).

get_final_list(X, R) :- getSum(X, [[]], merge_lists, R).

divisors_divisors(1, [[]])  :- !.
divisors_divisors(N, [[], [N]]) :- prime(N), !.
divisors_divisors(N, X) :-
 compact_prime_divisors(N, X1),
 make_lists_gta(X1, X2),
 get_final_list(X2, X).
		