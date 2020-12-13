(ns aoc-2020.13.solution
  (:require [utils.input-parser :as parser]
            [clojure.string :as str]))

(def test-input (parser/read-input "src/aoc_2020/13/test-input.txt"))
(def input (parser/read-input "src/aoc_2020/13/input.txt"))

(defn parse-input-1 [input]
  {:base-timestamp (Integer/parseInt (first input))
  :bus-ids (map #(Integer/parseInt %) (filter #(re-matches #"\d+" %) (str/split (last input) #",")))})

(defn first-possible-timestamp [base-timestamp bus-id]
  (+
   bus-id
   (last (take-while #(< % base-timestamp) (for [x (range)] (* x bus-id))))))

(defn part-1 [input]
  (let [parsed-input (parse-input-1 input)
        base-timestamp (:base-timestamp parsed-input)
        bus-ids (:bus-ids parsed-input)
        departure (first (sort-by :timestamp (map (fn [bus-id] {:bus-id bus-id :timestamp (first-possible-timestamp base-timestamp bus-id)}) bus-ids)))]
    (* (:bus-id departure) (- (:timestamp departure) base-timestamp))))
