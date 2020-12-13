(ns aoc-2020.11.solution
  (:require [utils.input-parser :as parser]
            [clojure.string :as str]))

(def test-input (parser/read-input "src/aoc_2020/11/test-input.txt"))
(def input (parser/read-input "src/aoc_2020/11/input.txt"))

(defn parse-input [input]
  {:rows (count input)
   :cols (count (first input))
   :input (vec (char-array (str/join input)))})

(defn strip-out-of-bounds [indices max-idx]
  (filter #(and (>= % 0) (<= % max-idx)) indices))

(defn neighbour-indices [idx cols]
  (let [leftmost (= (mod idx cols) 0)
        rightmost (= (mod idx cols) (dec cols))]
    (filter
     #(not (= % :wat))
     [(if leftmost :wat (- idx (inc cols))) (- idx cols) (if rightmost :wat (- idx (dec cols)))
     (if leftmost :wat (- idx 1) )                      (if rightmost :wat (+ idx 1))
     (if leftmost :wat (+ idx (dec cols))) (+ idx cols) (if rightmost :wat (+ idx (inc cols)))])))

(def neighbour-indices-memo (memoize neighbour-indices))

(defn occupied-neighbours [idx input cols]
  (count (filter
   #(= % \#)
   (map
   #(nth input %)
   (strip-out-of-bounds (neighbour-indices-memo idx cols) (dec (count input)))))))

(defn next-seat [idx seat input cols]
  (if (= seat \.) seat
      (let [occupied-neighbours (occupied-neighbours idx input cols)]
        (case seat
          \L (if (= 0 occupied-neighbours) \# \L)
          \# (if (>= occupied-neighbours 4) \L \#)))))

(defn get-next-state [input cols]
  (vec (map-indexed (fn [idx seat] (next-seat idx seat input cols)) input)))

(defn part-1 [raw-input]
  (let [parsed-input (parse-input raw-input)
        cols (:cols parsed-input)
        input (:input parsed-input)]
  (loop [current-state input
         next-state (get-next-state input cols)
         n 0]
    (if (= current-state next-state) (count (filter #(= % \#) current-state))
        (recur next-state (get-next-state next-state cols) (inc n))))))
