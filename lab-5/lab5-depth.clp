(deftemplate state
	(slot v1 (type INTEGER))
  (slot v2 (type INTEGER))
	(slot r1 (type INTEGER))
  (slot r2 (type INTEGER))
  (slot p (type STRING))
  (slot d (type INTEGER))
)

(deffacts initial-data
  (state (v1 5) (v2 9) (r1 0) (r2 0) (p "") (d 0))
)

(defrule init-breadth-search
  (declare (salience 10))
  ?c <- (state (v1 ?v1) (v2 ?v2) (r1 ?r1) (r2 ?r2) (p ?p) (d ?d))
  (and (test (= ?r1 0)) (test (= ?r2 0)))
  =>
  (retract ?c)
  (assert (state (v1 ?v1) (v2 ?v2) (r1 ?v1) (r2 ?r2) (p "+B1") (d (+ ?d 1))))
  (assert (state (v1 ?v1) (v2 ?v2) (r1 ?r1) (r2 ?v2) (p "+B2") (d (+ ?d 1))))
)

(defrule breadth-search
  (declare (salience 5))
  ?c <- (state (v1 ?v1) (v2 ?v2) (r1 ?r1) (r2 ?r2) (p ?p) (d ?d))
  (test (< ?d 10))
  =>
  (retract ?c)
  (bind ?f1 (- ?v1 ?r1))
  (bind ?f2 (- ?v2 ?r2))

  (bind ?r2_21 (if (< ?r2 ?f1) then 0 else (- ?r2 ?f1)))
  (bind ?r1_21 (- (+ ?r1 ?r2) ?r2_21))
  (bind ?r1_12 (if (< ?r1 ?f2) then 0 else (- ?r1 ?f2)))
  (bind ?r2_12 (- (+ ?r2 ?r1) ?r1_12))

  (assert (state (v1 ?v1) (v2 ?v2) (r1 ?r1_12) (r2 ?r2_12) (p (str-cat ?p ";B1->B2")) (d (+ ?d 1))))
  (assert (state (v1 ?v1) (v2 ?v2) (r1 ?r1_21) (r2 ?r2_21) (p (str-cat ?p ";B2->B1")) (d (+ ?d 1))))
  (assert (state (v1 ?v1) (v2 ?v2) (r1 ?v1) (r2 ?r2) (p (str-cat ?p ";+B1")) (d (+ ?d 1))))
  (assert (state (v1 ?v1) (v2 ?v2) (r1 ?r1) (r2 ?v2) (p (str-cat ?p ";+B2")) (d (+ ?d 1))))
  (assert (state (v1 ?v1) (v2 ?v2) (r1 0) (r2 ?r2) (p (str-cat ?p ";-B1")) (d (+ ?d 1))))
  (assert (state (v1 ?v1) (v2 ?v2) (r1 ?r1) (r2 0) (p (str-cat ?p ";-B2")) (d (+ ?d 1))))
)

(defrule find_result
  (declare (salience 5))
  ?c <- (state (v1 ?v1) (v2 ?v2) (r1 ?r1) (r2 ?r2) (p ?p) (d ?d))
  (or (test (= ?r1 3))
      (test (= ?r2 3)))
  =>
  (retract ?c)
  (printout t "Solution found with path: " ?p crlf)
  (halt)
)