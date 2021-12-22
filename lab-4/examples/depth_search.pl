	initial_state([white,black,white,black,white,black,white,black,empty,empty]).
	final_state([black,black,black,black,white,white,white,white,empty,empty]).

	main(Decision):-
		initial_state(State),
		depth_first(State,[],Decision),
		%it could be done much better
		writeln(Decision),
		statistics.

	%CurentState, ListOfOpenState,-ListOfMovesFromCurentToFinalState
	depth_first(State,_,[]):-
		final_state(State).
	depth_first(State,History,[Move|Moves]):-
		choise2(State,1,N,X,Y),
		move(X,Y,N,State,State1,Move),
		not(member(State1,History)),
		depth_first(State1,[State1|History],Moves).


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










