(ns aoc-2020.17.solution
  (:require [utils.input-parser :as parser]
            [clojure.math.combinatorics :as combo]))

(defn coord-path [x xs y ys dimensions]
  (concat[(- x (quot xs 2)) (- y (quot ys 2))] (repeat (- dimensions 2) 0)))

(defn parse-board [input dimensions]
  (let [ys (count input)
        xs (count (first input))]
    (reduce
     (fn [result [y x]]
       (assoc-in result (coord-path x xs y ys dimensions) (nth (nth input y) x)))
     {}
     (for [y (range ys) x (range xs)]
       [y x]))))

(defn coord-range [count]
  (let [start (- (quot count 2))]
    (range start (+ start count))))

(defn expanded-coord-range [count]
  (coord-range (+ count 2)))

(defn expanded-coord-ranges [coords dimensions]
  (conj
   (map
    #(expanded-coord-range (count (keys (get-in coords (repeat % 0)))))
    (range 1 dimensions))
   (expanded-coord-range (count (keys coords)))))

(defn neighbour-indices [coord]
  (let [ranges (map #(range (dec %) (+ 2 %)) coord)]
    (filter
     #(not (= % coord))
     (apply combo/cartesian-product ranges))))

(def neighbour-indices-memo (memoize neighbour-indices))

(defn active-neighbours [coord board]
  (let [indices (neighbour-indices-memo coord)]
    (count
     (filter
      #(= % \#)
      (map
       #(get-in board %)
       indices)))))

(defn next-cell [current-cell neighbours]
  (case current-cell
    \. (if (= neighbours 3) \#
           \.)
    \# (if (or (= 2 neighbours) (= 3 neighbours)) \#
           \.)))

(defn next-cycle [board dimensions]
  (let [ranges (expanded-coord-ranges board dimensions)]
    (reduce
     (fn [result coords]
       (let [neighbours (active-neighbours coords board)
             current-cell (get-in board coords \.)]
         (assoc-in result coords (next-cell current-cell neighbours))))
     {}
     (apply combo/cartesian-product ranges))))

(defn board-vals [board dimensions]
  (let [base-vals (vals board)]
    (loop [n (dec dimensions)
           result base-vals]
      (if (= n 0) result
          (recur (dec n) (flatten (map #(vals %) result)))))))

(defn active-cells [board dimensions]
  (count (filter
   #(= % \#)
   (board-vals board dimensions))))

(defn solver [input dimensions]
  (let [board (parse-board input dimensions)]
    (loop [n 0
           current-board board]
      (if (= n 6) (active-cells current-board dimensions)
          (recur (inc n) (next-cycle current-board dimensions))))))

(defn part-1 [input]
  (solver input 3))

(defn part-2 [input]
  (solver input 4))
