initial_state([white,black,white,black,white,black,white,black,empty,empty]).
final_state([white,white,white,white,empty,empty,black,black,black,black]).
main(Decision):-
	initial_state(State),
	hill_climbing(State,[],Decision),
	writeln(Decision).

%CurentState, ListOfOpenState,-ListOfMovesFromCurentToFinalState
hill_climbing(State,_,[]):-
	final_state(State).
hill_climbing(State,History,[Move|Moves]):-
	setof(Estimate-State1-Move,(choise2(State,1,N,X,Y),
				    move(X,Y,N,State,State1,Move),
				    criterion_function(State1,8,Estimate),
				    not(member(State1,History))),NextMoves),
	member(Estimate-State1-Move,NextMoves),
	hill_climbing(State1,[State1|History],Moves).

criterion_function(-, -, 1).
criterion_function([S1,S2|State],I,Estimate):-
	S1=S2,
	I1 is I-1,
	criterion_function([S2|State],I1,Estimate).
criterion_function([S1,S2|State],I,Estimate):-
	S1\=S2,
	criterion_function([S2|State],I,Estimate).
criterion_function([_],I,I).


%+List,+Counter,-PositionOfX,-X,-Y
choise2([X,Y|_],I,I,X,Y):-
	X\=empty,
	Y\=empty.
choise2([_|Xs],I,N,X,Y):-
	I1 is I+1,
	choise2(Xs,I1,N,X,Y).


%+X,+Y,+PositionOfX,+CurentState,-NextState,-Move
move(X,Y,N,State,State1,N-A):-
	search_empty(State,1,A),
	rearrange(State,X,Y,A,InterimState),
	rearrange(InterimState,empty,empty,N,State1).

%+list,+Counter,-PositionOfEmpty
search_empty([E|_],I,I):-
	E=empty.
search_empty([E|Es],I,N):-
	E\=empty,
	I1 is I+1,
	search_empty(Es,I1,N).

%+ListOfCurentState,+Item1,+Item2,+Counter,-ListOfNewState
rearrange([X|Xs],A,B,I,[X|Xn]):-
	I>1,
	I1 is I-1,
	rearrange(Xs,A,B,I1,Xn).
rearrange([_,_|Xs],A,B,1,[A,B|Xs]).










