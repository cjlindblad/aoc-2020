(ns aoc-2020.02.solution
  (:require [clojure.string :as str]))

(defn parse-letter-limit [input]
  (map
   #(Integer/parseInt %)
   (str/split
    (re-find #"\d+-\d+" input) #"-")))

(defn parse-letter [input]
  (last (re-find #"([a-z])\:" input)))

(defn parse-password [input]
  (last (re-find #" ([a-z]+)$" input)))

(defn letter-occurences [letter password]
  (count (re-seq (re-pattern letter) password)))

(defn is-valid-password [input]
  (let [limit (parse-letter-limit input)
        letter (parse-letter input)
        password (parse-password input)
        letter-count (letter-occurences letter password)]
    (and (>= letter-count (first limit))
         (<= letter-count (last limit)))))

(defn part-1 [input]
  (count (filter is-valid-password input)))