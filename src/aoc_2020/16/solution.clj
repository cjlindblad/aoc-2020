(ns aoc-2020.16.solution
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn parse-rules [input]
  (let [lines (str/split input #"\n")]
    (map
     (fn [line]
       (let [[name lower-from lower-to upper-from upper-to] (rest (re-matches #"(.*): (\d+)-(\d+) or (\d+)-(\d+)" line))
             lower-from-int (Integer/parseInt lower-from)
             lower-to-int (Integer/parseInt lower-to)
             upper-from-int (Integer/parseInt upper-from)
             upper-to-int (Integer/parseInt upper-to)]
         {:name name
          :lower-range #(range lower-from-int (inc lower-to-int))
          :upper-range #(range upper-from-int (inc upper-to-int))
          :contains? #(or (and (>= % lower-from-int) (<= % lower-to-int)) (and (>= % upper-from-int) (<= % upper-to-int)))}))
     lines)))

(defn parse-my-ticket [input]
  (map
   #(Integer/parseInt %)
   (str/split (last (str/split input #"\n")) #",")))

(defn parse-nearby-tickets [input]
  (map
   (fn [line]
     (map #(Integer/parseInt %) (str/split line #",")))
   (rest (str/split input #"\n"))))

(defn parse-input [input]
  (let [[rules my-ticket nearby-tickets] input]
    {:rules (parse-rules rules)
     :my-ticket (parse-my-ticket my-ticket)
     :nearby-tickets (parse-nearby-tickets nearby-tickets)}))

(defn ticket-scanning-error-rate [raw-input]
  (let [input (parse-input raw-input)
        contains-predicates (map #(:contains? %) (:rules input))]
    (loop [invalid-values []
           remaining-tickets (:nearby-tickets input)]
      (if (empty? remaining-tickets) (apply + invalid-values)
          (let [current-ticket (first remaining-tickets)
                current-invalid (map (fn [ticket-value] (reduce #(or %1 %2) false (map (fn [predicate] (predicate ticket-value)) contains-predicates))) current-ticket)
                invalid (keys (filter #(not (val %)) (zipmap current-ticket current-invalid)))]
            (recur (concat invalid-values invalid) (rest remaining-tickets)))))))

(defn part-1 [input]
  (ticket-scanning-error-rate input))

(defn filter-valid-tickets [raw-input]
  (let [input (parse-input raw-input)
        contains-predicates (map #(:contains? %) (:rules input))]
    (filter
     (fn [ticket]
       (let [current-invalid (map (fn [ticket-value] (reduce #(or %1 %2) false (map (fn [predicate] (predicate ticket-value)) contains-predicates))) ticket)
             invalid (keys (filter #(not (val %)) (zipmap ticket current-invalid)))]
         (not invalid)))
     (:nearby-tickets input))
    ))

(defn get-possible-orders [rules nearby-tickets]
  (map
   (fn [ticket]
     (vec
      (map
       (fn [value]
         (map
          #(:name %)
          (filter
           (fn [rule] ((:contains? rule) value))
           rules)))
       ticket)))
   nearby-tickets))

(defn find-field-order [rules nearby-tickets]
  (let [possible-orders (get-possible-orders rules nearby-tickets)
        value-count (count (first nearby-tickets))
        sorted-order-set (sort-by
                          #(count (:set %))
                          (for [x (range value-count)]
                            {:idx x
                             :set (apply set/intersection
                                         (map
                                          (fn [order] (set (nth order x)))
                                          possible-orders))}))]
    (map-indexed
     (fn [idx order-set]
       (if (= idx 0) {:idx 0 :set (str/join (:set order-set))}
           {:idx (:idx order-set)
            :set (str/join (set/difference (:set order-set) (:set (nth sorted-order-set (dec idx)))))}))
     sorted-order-set)))
      

(defn part-2 [raw-input]
  (let [invalid-input (parse-input raw-input)
        input (assoc invalid-input :nearby-tickets (filter-valid-tickets raw-input))
        field-order (find-field-order (:rules input) (:nearby-tickets input))
        departure-indices (map #(:idx %) (filter #(re-matches #"departure.*" (:set %)) field-order))]
    (apply * (map #(nth (:my-ticket input) %) departure-indices))))
