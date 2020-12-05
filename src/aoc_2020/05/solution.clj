(ns aoc-2020.05.solution
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn binary-str->int [boarding-pass]
  (Integer/parseInt
   (str/replace boarding-pass #"B|R|F|L" {"B" "1", "R" "1", "F" "0", "L" "0"})
   2))

(defn seat-id [boarding-pass]
  (+ (* 8 (binary-str->int (subs boarding-pass 0 7)))
     (binary-str->int (subs boarding-pass 7))))

(defn part-1 [input]
  (apply max (map seat-id input)))

(defn part-2 [input]
  (let [ids-with-gap (sort (map seat-id input))
        all-ids (range (first ids-with-gap) (inc (last ids-with-gap)))]
    (first (set/difference (set all-ids) (set ids-with-gap)))))
