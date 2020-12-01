(ns aoc-2020.01.solution)

(defn part-1 [input]
  (first
   (for [x input y input :when (= (+ x y) 2020)] (* x y))))

(defn part-2 [input]
  (first
   (for [x input y input z input :when (= (+ x y z) 2020)] (* x y z))))