(ns aoc-2020.06.solution
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn part-1 [input]
  (->>
   input
   (map #(str/replace % #"\s" ""))
   (map distinct)
   (map count)
   (apply +)))

(defn intersection-count [group]
  (->>
   (str/split group #"\s")
   (map #(str/split % #""))
   (map set)
   (apply set/intersection)
   (map count)))

(defn part-2 [input]
  (->>
   input
   (map intersection-count)
   (map #(reduce + %))
   (apply +)))
