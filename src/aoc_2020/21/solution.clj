(ns aoc-2020.21.solution
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn parse-input [raw-input]
  (map
   (fn [line]
     (let [[ingredients allergens]
      (str/split
       (str/replace
        (str/replace
         (str/replace
          line
          "(" "")
         ")" "")
        "," "")
       #" contains ")]
  {:ingredients (str/split ingredients #" ")
   :allergens (str/split allergens #" ")}))
   raw-input)
  )

(defn input->allergens-by-ingredient [input]
  (reduce
   (fn [result, food]
     (let [ingredients (:ingredients food) allergens (:allergens food)]
       (into
        result
        (reduce
         (fn [inner-result allergen]
           (assoc inner-result allergen (conj (get result allergen []) (set ingredients))))
         {}
         allergens)))
     )
   {}
   input))

(defn reduce-multiple-ingredient-sets [allergens-by-ingredient]
  (into
   {}
   (map
    (fn [[k v]]
      [k (if (< 1 (count v))
           (apply set/intersection v)
           (first v))])
    allergens-by-ingredient)))

(defn get-single-set-ingredients [allergens-by-ingredient]
  (filter #(= 1 (count %)) (vals allergens-by-ingredient)))

(defn only-single-ingredients? [allergens-by-ingredient]
  (not-any? #(> (count %) 1) (vals allergens-by-ingredient)))

(defn reduce-to-single-ingredient [allergens-by-ingredient]
  (loop [food allergens-by-ingredient]
    (if (only-single-ingredients? food) food
        (recur
         (into
          {}
          (map
           (fn [[k v]]
             (let [single-set-ingredients (get-single-set-ingredients food)
                   next-ingredients (apply set/difference v single-set-ingredients)]
               [k (if (empty? next-ingredients) v next-ingredients)]))
           food))))))

(defn get-risky-ingredients [allergens-by-ingredient]
  (apply set/union (vals allergens-by-ingredient)))

(defn input->risky-ingredients [input]
  (->
   input
   (parse-input)
   (input->allergens-by-ingredient)
   (reduce-multiple-ingredient-sets)
   (reduce-to-single-ingredient)
   (get-risky-ingredients)))

(defn input->all-ingredients [raw-input]
  (let [input (parse-input raw-input)]
    (apply hash-set (flatten (map
     :ingredients
     input)))))

(defn input->safe-ingredients [raw-input]
  (let [all-ingredients (input->all-ingredients raw-input)
        risky-ingredients (input->risky-ingredients raw-input)]
    (set/difference all-ingredients risky-ingredients)))

(defn part-1 [input]
  (let [safe-ingredients (input->safe-ingredients input)
        ingredient-list (flatten (map :ingredients (parse-input input)))]
    (apply + (map
     (fn [ingredient] (count (filter #(= % ingredient) ingredient-list)))
     safe-ingredients))))

(defn part-2 [input]
  (->>
   input
   (parse-input)
   (input->allergens-by-ingredient)
   (reduce-multiple-ingredient-sets)
   (reduce-to-single-ingredient)
   (map (fn [[k v]] [(first v) k]))
   (sort-by (fn [[_ v]] v))
   (map first)
   (str/join ",")))
