(defglobal ?*input* = 1)

(defmodule MAIN 
	(export deftemplate status))

(deftemplate MAIN::status 
	(slot search-depth (type INTEGER) (range 1 ?VARIABLE))
   	(slot parent (type FACT-ADDRESS SYMBOL) (allowed-symbols no-parent))
   	(multislot last-move )
  	 (multislot chips ))
  
    
  
	
;;;*****************
;;;* INITIAL STATE *
;;;*****************
 (deffacts MAIN::initial-position
 	(status (search-depth 1) 
    	(parent no-parent)
    	(last-move no-move)
	(chips white black white black white black white black empty empty ) 
 ))


(defrule MAIN::move-0
	?node<-(status (search-depth ?num)  ;curent state of search 
	        (chips $?first ?ne1&~empty ?ne2&~empty $?last))
	(test (> (length$ ?first) 0))
      => 
     (bind ?cur $?first ?ne1 ?ne2 $?last) 
	 
   	 (bind ?empty_pos (member$ empty $?cur))
	 (bind $?chip1 (replace$ $?cur ?empty_pos (+ 1 ?empty_pos) ?ne1 ?ne2))
      
     (bind ?ne_pos1 ( + 1 (length$ $?first)))	  
	 (bind $?chip2(replace$ $?chip1  ?ne_pos1 (+ 1 ?ne_pos1) empty empty))
	      
	 (bind ?*input* (+ 1 ?*input*))
	 (duplicate ?node (search-depth (+ ?*input* ?num))  
                   	  (parent ?node)
	 		          (last-move (member$ empty $?cur) ?ne_pos1)
	  		          (chips $?chip2)	
	 ))
	
	
(defrule MAIN::move-1 
	?node<-(status (search-depth ?num)  ;curent state of search 
	        (chips ?ne1&~empty ?ne2&~empty $?chip))
	
      => 
     (bind ?cur ?ne1 ?ne2 $?chip) 
	 
   	 (bind ?empty_pos (member$ empty $?cur))
	 (bind $?chip1 (replace$ $?cur ?empty_pos (+ 1 ?empty_pos) ?ne1 ?ne2)) 		 
	 (bind $?chip2(replace$ $?chip1  1 2 empty empty))
	 (bind ?*input* (+ 1 ?*input*))
	 (duplicate ?node (search-depth (+ ?*input* ?num)) 
                   	  (parent ?node)
	 		          (last-move (member$ empty $?cur) 1)
	  		          (chips $?chip2)	
	 ))	

;;;*********************************
;;;* FIND AND PRINT SOLUTION RULES *
;;;*********************************
	
(defmodule CONSTRAINTS 
  (import MAIN deftemplate status)
 
  )
      
 (defrule CONSTRAINTS::circular-path 
  (declare (auto-focus TRUE))
  (status (search-depth ?sd1)
    (chips $?chip))	
  
  ?node <- (status (search-depth ?sd2&:(< ?sd1 ?sd2))
   (chips $?chip))
  
  =>
  (retract ?node))   
 
 
  (defmodule SOLUTION 
  (import MAIN deftemplate status)
)
  
  (deftemplate SOLUTION::moves 
   (slot id (type FACT-ADDRESS SYMBOL) (allowed-symbols no-parent)) 
   (multislot moves-list (type SYMBOL) ))
	
	(defrule SOLUTION::recognize-solution 
  (declare (auto-focus TRUE))
	  ?node <- (status 
		               (parent ?parent)
			           (last-move $?move)
		               ( chips empty empty white white white white black black black black  ))
	  
  =>
  
  (retract ?node)
   (assert (moves (id ?parent) (moves-list $?move)))
  
  )

  
  (defrule SOLUTION::further-solution 
  ?node <- (status (parent ?parent)
                   (last-move $?move))
  ?mv <- (moves (id ?node) (moves-list $?rest))
  =>
  
  (modify ?mv (id ?parent) (moves-list $?move $?rest))
   )
  
  (defrule SOLUTION::print-solution 
  ?mv <- (moves (id no-parent) (moves-list no-move $?m))
  =>
   
  (retract ?mv)
  (printout t  "Solution found: " crlf )
  (bind ?length (length $?m))
  (bind ?i 1)
  (while (<= ?i ?length)
     (bind ?thing (nth ?i $?m))  
	 (bind ?thing1 (nth (+ 1 ?i) $?m))
         (printout t ?thing " " (+ 1 ?thing)  " reposition with " ?thing1 " "(+ 1 ?thing1)"." crlf)
		 (bind ?i (+ 2 ?i))	)	 
		 (retract *)		
     )
	