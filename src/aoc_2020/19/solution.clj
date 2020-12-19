(ns aoc-2020.19.solution
  (:require [clojure.string :as str]
            [instaparse.core :as insta]))

(defn parse-input [input]
  (let [[grammar strings] input]
    {:grammar (str/replace grammar ":" " =")
     :strings (str/split strings #"\n")}))

(defn replace-invalid-rules [grammar]
  (str/replace
   (str/replace grammar "8 = 42" "8 = 42 | 42 8")
   "11 = 42 31"
   "11 = 42 31 | 42 11 31"))

(defn count-matches [parser strings]
  (count
   (filter
    vector?
    (map
     #(parser % :start :0)
     strings))))

(defn part-1 [input]
  (let [{:keys [grammar strings]} (parse-input input)]
    (count-matches (insta/parser grammar) strings)))

(defn part-2 [input]
  (let [{:keys [grammar strings]} (parse-input input)]
    (count-matches (insta/parser (replace-invalid-rules grammar)) strings)))

