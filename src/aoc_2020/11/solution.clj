(ns aoc-2020.11.solution
  (:require [clojure.string :as str]))

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

(defn line-of-sight-indices [idx cols max-index]
  [;NW
   (for [n (range (- idx (inc cols)) (- 1) (- (inc cols))) :while (not (= (mod n cols) (dec cols)))] n)
   ;N
   (for [n (range (- idx cols) (- 1) (- cols))] n)
   ;NE
   (for [n (range (- idx (dec cols)) (- 1) (- (dec cols))) :while (not (= (mod n cols) 0))] n)
   ;W
   (for [n (range (dec idx) (- idx cols) (- 1)) :while (not (= (mod n cols) (dec cols)))] n)
   ;E
   (for [n (range (inc idx) (+ idx cols)) :while (not (= (mod n cols) 0))] n)
   ;SW
   (for [n (range (+ idx (dec cols)) (inc max-index) (dec cols)) :while (not (= (mod n cols) (dec cols)))] n)
   ;S
   (for [n (range (+ idx cols) (inc max-index) cols)] n)
   ;SE
   (for [n (range (+ idx (inc cols)) (inc max-index) (inc cols)) :while (not (= (mod n cols) 0))] n)
   ])

(def line-of-sight-indices-memo (memoize line-of-sight-indices))

(defn line-of-sights [idx cols input]
  (let [lines (line-of-sight-indices-memo idx cols (dec (count input)))]
    (map
     (fn [indices]
       (map #(nth input %) indices))
     lines)))

(defn occupied-visible [idx cols input]
  (let [lines (line-of-sights idx cols input)]
    (count (filter
            #(= \# %)
            (map
             (fn [line] (first (drop-while #(= \. %) line)))
             lines)))))

(defn next-seat-2 [idx seat input cols]
  (if (= seat \.) seat
      (let [occupied-neighbours (occupied-visible idx cols input)]
        (case seat
          \L (if (= 0 occupied-neighbours) \# \L)
          \# (if (>= occupied-neighbours 5) \L \#)))))

(defn get-next-state-2 [input cols]
  (vec (map-indexed (fn [idx seat] (next-seat-2 idx seat input cols)) input)))

(defn part-2 [raw-input]
  (let [parsed-input (parse-input raw-input)
        cols (:cols parsed-input)
        input (:input parsed-input)]
  (loop [current-state input
         next-state (get-next-state-2 input cols)
         n 0]
    (if (= current-state next-state) (count (filter #(= % \#) current-state))
        (recur next-state (get-next-state-2 next-state cols) (inc n))))))
