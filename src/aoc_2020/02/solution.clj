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

(defn is-valid-password-part-1 [input]
  (let [limit (parse-letter-limit input)
        letter (parse-letter input)
        password (parse-password input)
        letter-count (letter-occurences letter password)]
    (and (>= letter-count (first limit))
         (<= letter-count (last limit)))))

(defn part-1 [input]
  (count (filter is-valid-password-part-1 input)))

(defn has-letter-at-index [letter index word]
  (= letter
     (str (nth word (- index 1)))))

(defn xor [a b]
  (and
   (or a b)
   (not (and a b))))

(defn is-valid-password-part-2 [input]
  (let [positions (parse-letter-limit input)
        letter (parse-letter input)
        password (parse-password input)
        position-matches (map #(has-letter-at-index letter % password) positions)]
    (xor (first position-matches) (last position-matches))))

(defn part-2 [input]
  (count (filter is-valid-password-part-2 input)))
