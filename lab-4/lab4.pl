%String concatenation logic
join_str(Str1, Str2, Res) :-
  name(Str1, Str1List),
  name(Str2, Str2List),
  append(Str1List, Str2List, Str3List),
  name(Res, Str3List).

%Triggers in case the first bucket has an expected result
limited_depth_search(V1, V2, 3, _, L, I, P) :-
  format('Solution found! Path: ~w', [P]).

%Triggers in case the second bucket has an expected result
limited_depth_search(V1, V2, _, 3, L, I, P) :-
  format('Solution found! Path: ~w', [P]).

%Main logic
%Max first volume, Max second volume, Current first volume, Current second volume, Max depth, Current depth, Path
limited_depth_search(V1, V2, R1, R2, L, I, P) :-
  J is I + 1,
  J < L,

  %Free space in each bucket
  F1 is V1 - R1,
  F2 is V2 - R2,

  %How much would left after pouring from one bucket to another
  R2_21 is max(0, R2 - F1),
  R1_21 is R1 + R2 - R2_21,
  R1_12 is max(0, R1 - F2),
  R2_12 is R2 + R1 - R1_12,

  %All possible path strings
  join_str(P, ';B1->B2', P12),
  join_str(P, ';B2->B1', P21),
  join_str(P, ';+B1', P11),
  join_str(P, ';+B2', P22),
  join_str(P, ';-B1', P10),
  join_str(P, ';-B2', P20),

  %All possible shuffles
  (limited_depth_search(V1, V2, R1_12, R2_12, L, J, P12);
  limited_depth_search(V1, V2, R1_21, R2_21, L, J, P21);
  limited_depth_search(V1, V2, V1, R2, L, J, P11);
  limited_depth_search(V1, V2, R1, V2, L, J, P22);
  limited_depth_search(V1, V2, 0, R2, L, J, P10);
  limited_depth_search(V1, V2, R1, 0, L, J, P20)).

%Entrypoint
%First volume, Second volume, Max depth
main(V1, V2, L) :-
  I is 0,
  (limited_depth_search(V1, V2, V1, 0, L, I, '+B1');
  limited_depth_search(V1, V2, 0, V2, L, I, '+B2')).