(ns aoc-2020.22.solution
  (:require [clojure.string :as str]))

(defn parse-player [player]
  (vec (map
   #(Integer/parseInt %)
   (rest (str/split player #"\n")))))

(defn parse-input [input]
  (let [[player-1 player-2] (map parse-player input)]
    [player-1 player-2]))

(defn calculate-score [hand]
  (loop [[top & remaining] hand
         score 0]
    (if (empty? remaining)
      (+ score top)
      (recur remaining (+ score (* top (inc (count remaining))))))))

(defn part-1 [input]
  (let [[player-1 player-2] (parse-input input)]
    (loop [[player-1-top & player-1-rest] player-1
           [player-2-top & player-2-rest] player-2]
      (cond
        (nil? player-1-top) (calculate-score (conj player-2-rest player-2-top))
        (nil? player-2-top) (calculate-score (conj player-1-rest player-1-top))
        :else (if (> player-1-top player-2-top)
                (recur (conj (vec player-1-rest) player-1-top player-2-top) player-2-rest)
                (recur player-1-rest (conj (vec player-2-rest) player-2-top player-1-top)))))))

(defn in? 
  "true if coll contains elm"
  [coll elm]  
  (some #(= elm %) coll))

(defn recursive-combat [player-1 player-2]
  (loop [player-1 player-1
         player-2 player-2
         player-1-history []
         player-2-history []]
    (let [player-1-top (first player-1)
          player-1-rest (rest player-1)
          player-2-top (first player-2)
          player-2-rest (rest player-2)
          player-1-can-recurse (and (not (= player-1-top nil)) (>= (count player-1-rest) player-1-top))
          player-2-can-recurse (and (not (= player-2-top nil)) (>= (count player-2-rest) player-2-top))]
      (cond
        ; infinite recursion rule
        (or (in? player-1-history player-1) (in? player-2-history player-2))
        {:winner :player-1}
        
        ; player 1 basic win rule
        (nil? player-1-top)
        {:winner :player-2
         :score (calculate-score player-2)}
        
        ; player 2 basic win rule
        (nil? player-2-top)
        {:winner :player-1
         :score (calculate-score player-1)}
        
        ; not able to recurse rule
        (or (not player-1-can-recurse) (not player-2-can-recurse))
        (if (> player-1-top player-2-top)
          (recur
           (conj (vec player-1-rest) player-1-top player-2-top)
           (vec player-2-rest)
           (conj player-1-history player-1)
           (conj player-2-history player-2))
          (recur
           (vec player-1-rest)
           (conj (vec player-2-rest) player-2-top player-1-top)
           (conj player-1-history player-1)
           (conj player-2-history player-2)))
        
        ; go into recursive game
        :else
        (let [result (recursive-combat (vec (take player-1-top player-1-rest)) (vec (take player-2-top player-2-rest)))]
          (if (= (:winner result) :player-1)
            (recur (conj (vec player-1-rest) player-1-top player-2-top)
                   (vec player-2-rest)
                   (conj player-1-history player-1)
                   (conj player-2-history player-2))
            (recur (vec player-1-rest)
                   (conj (vec player-2-rest) player-2-top player-1-top)
                   (conj player-1-history player-1)
                   (conj player-2-history player-2))))))))
    
(defn part-2 [input]
  (let [[player-1 player-2] (parse-input input)]
    (:score (recursive-combat player-1 player-2))))
