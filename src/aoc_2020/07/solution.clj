(ns aoc-2020.07.solution
  (:require [clojure.string :as str]))

(defn parse-bag [bag]
  (let [matches (re-seq #"(\d+) (.*) bag" bag)]
    (when matches
      {:amount (second (first matches)) :type (last (first matches))})))

(defn input->bag-map [input]
  (reduce
   (fn [acc cur]
     (assoc
      acc
      (second (first (re-seq #"(.*) bag" (first (str/split cur #" contain ")))))
      (map
       parse-bag
       (str/split (last (str/split cur #" contain ")) #", ")
      )))
   {}
   input))

(defn has-shiny-gold? [type bag-map]
  (let [children (bag-map type)]
    (cond
      (= children '(nil))
      false
      (some #(= "shiny gold" (:type %)) children)
      true
      :else
      (reduce
       #(or %1 %2)
       false
       (for [child (keep identity children)]
         (has-shiny-gold? (:type child) bag-map))))))

(defn find-bags [bag-map]
  (filter #(has-shiny-gold? % bag-map) (keys bag-map)))

(defn part-1 [input]
  (count (find-bags (input->bag-map input))))

(defn bag-count [type bag-map]
  (let [children (bag-map type)]
    (cond
      (= children '(nil))
      1
      :else
      (reduce
       #(+ %1 %2)
       1
       (for [child (keep identity children)]
         (*
          (Integer/parseInt (:amount child))
          (bag-count (:type child) bag-map)))))))

(defn part-2 [input]
  (-
   (bag-count "shiny gold" (input->bag-map input))
   1))