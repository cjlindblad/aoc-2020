(ns aoc-2020.17.solution
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/17/test-input.txt" "\n"))
(def input (parser/read-input "src/aoc_2020/17/input.txt" "\n"))

(defn get-board-vals [board]
  (flatten (map #(vals %) (flatten (map #(vals %) (vals board))))))

(defn active-cells [board]
  (count (filter
   #(= % \#)
   (get-board-vals board))))

(defn coord-range [count]
  (let [start (- (quot count 2))]
    (range start (+ start count))))

(defn parse-board [input]
  (let [ys (count input)
        xs (count (first input))]
    (reduce
     (fn [result [y x]]
       (assoc-in result [(- x (quot xs 2)) (- y (quot ys 2)) 0] (nth (nth input y) x)))
     {}
     (for [y (range ys) x (range xs)]
       [y x]))))

(defn coord-ranges [coords]
  [(coord-range (count (keys coords)))
   (coord-range (count (keys (coords 0))))
   (coord-range (count (keys (get-in coords [0 0]))))])

(defn print-board [board]
  (let [[xs ys zs] (coord-ranges board)]
    (for [z zs]
      (print
       (str "z=" z "\n"
            (str/join
             (for [y ys]
               (str (str/join
                     (for [x xs]
                       (get-in board [x y z]))) "\n"))))
       "\n"))))

(defn expanded-coord-range [count]
  (coord-range (+ count 2)))

(defn expanded-coord-ranges [coords]
  [(expanded-coord-range (count (keys coords)))
   (expanded-coord-range (count (keys (coords 0))))
   (expanded-coord-range (count (keys (get-in coords [0 0]))))])

(defn neighbour-indices [x-coord y-coord z-coord]
  (let [xs (range (dec x-coord) (+ 2 x-coord))
        ys (range (dec y-coord) (+ 2 y-coord))
        zs (range (dec z-coord) (+ 2 z-coord))]
    (filter
     #(not (= % [x-coord y-coord z-coord]))
     (for [x xs y ys z zs]
       [x y z]))))

(def neighbour-indices-memo (memoize neighbour-indices))

(defn active-neighbours [[x y z] board]
  (let [indices (neighbour-indices-memo x y z)]
    (count
     (filter
      #(= % \#)
      (map
       #(get-in board %)
       indices)))))

(defn next-cycle [board]
  (let [[xs ys zs] (expanded-coord-ranges board)]
    (reduce
     (fn [result [x y z]]
       (let [neighbours (active-neighbours [x y z] board)
             current-cell (get-in board [x y z] \.)]
         (assoc-in result [x y z] (case current-cell
                                    \. (if (= neighbours 3) \#
                                           \.)
                                    \# (if (or (= 2 neighbours) (= 3 neighbours)) \#
                                           \.)))))
     {}
     (for [x xs y ys z zs]
       [x y z]))))

(defn part-1 [input]
  (let [board (parse-board input)]
    (loop [n 0
           current-board board]
      (if (= n 6) (active-cells current-board)
          (recur (inc n) (next-cycle current-board))))))

(part-1 input)
