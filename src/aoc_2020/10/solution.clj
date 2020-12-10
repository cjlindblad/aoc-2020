(ns aoc-2020.10.solution
  (:require [clojure.test :refer :all]
            [clojure.math.numeric-tower :as math]))

(defn get-diffs [input]
  (map
   #(- (last %) (first %))
   (partition 2 1 (sort input))))

(defn part-1 [input]
  (let [diffs (get-diffs input)]
    (* (+ 1 (count (filter #(= % 1) diffs)))
       (+ 1 (count (filter #(= % 3) diffs))))))

(defn map-count [input]
  (let [input (sort (conj input 0))]
    (map-indexed
     (fn [idx item]
       (count (filter #(<= % (+ 3 item)) (take 3 (drop (inc idx) input)))))
     input)))

(defn part-2 [input]
  (let [options-string (apply str (map-count input))
        sevens (count (re-seq #"332" options-string))
        fours (count (re-seq #"132" options-string))
        twos (count (re-seq #"121" options-string))]
    (* (math/expt 7 sevens)
       (math/expt 4 fours)
       (math/expt 2 twos))))
