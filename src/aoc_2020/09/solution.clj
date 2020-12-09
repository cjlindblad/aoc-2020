(ns aoc-2020.09.solution
  (:require [clojure.math.combinatorics :as comb]
            [utils.input-parser :as parser]))

(def test-input (parser/read-numbers "src/aoc_2020/09/test-input.txt"))
(def input (parser/read-numbers "src/aoc_2020/09/input.txt"))

(defn not-in? [col e]
  (not-any? #(= e %) col))

(defn get-sums [preamble]
  (map
   #(apply +' %)
   (comb/combinations preamble 2)))

(defn part-1 [input preamble-length]
  (loop [input input]
    (let [preamble (take preamble-length input)
          transmission (drop preamble-length input)
          next-number (first transmission)
          sums (get-sums preamble)]
      (cond
        (empty? transmission) :wat
        (not-in? sums next-number) next-number
        :else (recur (drop 1 input))))))

(defn part-2 [input preamble-length]
  (let [target (part-1 input preamble-length)]
    (loop [sum (first input)
           slice (vector (first input))
           remaining (rest input)]
      (cond
        (= sum target) (+ (apply min slice) (apply max slice))
        (> sum target) (recur (- sum (first slice)) (subvec slice 1) remaining)
        (< sum target) (recur (+ sum (first remaining)) (conj slice (first remaining)) (drop 1 remaining))))))

  