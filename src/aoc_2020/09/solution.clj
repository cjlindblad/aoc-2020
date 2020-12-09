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

(defn permutations [input]
  (for [n (range (count input))]
    (take (inc n) input)))

(defn permutation-for-sum [input sum]
  (let [perms (permutations input)]
    (first (filter #(= sum (apply +' %)) perms))))

(defn part-2 [input preamble-length]
  (let [weakness (part-1 input preamble-length)]
    (loop [input input]
      (cond
        (empty? input) :wat
        :else (let [perm (permutation-for-sum input weakness)]
                (cond
                  (not-empty perm) (+ (apply min perm) (apply max perm))
                  :else (recur (drop 1 input))))))))
  