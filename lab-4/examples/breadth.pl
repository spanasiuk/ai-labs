initial_state([white,black,white,black,white,black,white,black,empty,empty]).
final_state([black,black,black,black,white,white,white,white,empty,empty]).
main(Decision):-
	initial_state(State),
	breadth_search([s(State,[])],[],[],Decision),
	writeln(Decision).

%CurentFront,NextFront,ListOfOpenState,-ListOfMovesFromCurentToFinalState
breadth_search([s(State,Path)|_],_,_,Path):-
	writeln(State),
	final_state(State).
breadth_search([s(State,Path)|Fr],NextFr,History,EndPath):-
	findall(State1-Move,(choise2(State,1,N,X,Y),move(X,Y,N,State,State1,Move)),NextMoves),
	next_fr_add(NextMoves,NextFr,NextFr1,History,Path),
	history_add(State,History,History1),
	breadth_search(Fr,NextFr1,History1,EndPath).


breadth_search([],[],_,_):-
	!,
	fail.
breadth_search([],NextFr,History,Move):-
	breadth_search(NextFr,[],History,Move).

%MoveOfCurentState,CurentFront,AddingFront,PathToCurentState
next_fr_add([ State-Move|NextMoves],NextFr,[s(State,[Move|Path])|NextFr1],History,Path):-
	not(member(State,History)),
	!,
	next_fr_add(NextMoves,NextFr,NextFr1,History,Path).
next_fr_add([_|NextMoves],NextFr,NextFr1,History,Path):-
	next_fr_add(NextMoves,NextFr,NextFr1,History,Path).
next_fr_add([],NextFr,NextFr,_,_).

%State,History,AddingHistory
history_add(State,History,History):-
	final_state(State),
	!.
history_add(State,History,[State|History]).


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














