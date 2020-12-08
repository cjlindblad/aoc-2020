(ns aoc-2020.08.solution
  (:require [utils.input-parser :as parser]
            [clojure.string :as str]
            [clojure.set :as set]))

(def test-input (parser/read-input "src/aoc_2020/08/test-input.txt" "\n"))
(def input (parser/read-input "src/aoc_2020/08/input.txt" "\n"))

(defn parse-instructions [input]
  (map
   (fn [x]
     (let [[op value] (str/split x #" ")]
       {:op op :value (Integer/parseInt value)}))
   input))

(defn in? [col e]
  (some #(= e %) col))

(defn interpreter [input]
  (let [instructions (parse-instructions input)]
    (loop [acc 0
           executed-indices []
           current-index 0]
      (let [current-instruction (nth instructions current-index)
            op (:op current-instruction)
            value (:value current-instruction)]
        (cond
          (in? executed-indices current-index)
          acc
          (= op "nop")
          (recur
           acc
           (conj executed-indices current-index)
           (inc current-index))
          (= op "acc")
          (recur
           (+ acc value)
           (conj executed-indices current-index)
           (inc current-index))
          (= op "jmp")
          (recur
           acc
           (conj executed-indices current-index)
           (+ current-index value))
          )))))

(interpreter input)

(defn interpreter-2 [input]
  (let [instructions (parse-instructions input)]
    (loop [acc 0
           executed-indices []
           current-index 0]
      (let [current-instruction (nth instructions current-index)
            op (:op current-instruction)
            value (:value current-instruction)]
        (cond
          (= (count instructions) current-index)
          {:correct true :acc acc}
          (in? executed-indices current-index)
          {:correct false}
          (= op "nop")
          (recur
           acc
           (conj executed-indices current-index)
           (inc current-index))
          (= op "acc")
          (recur
           (+ acc value)
           (conj executed-indices current-index)
           (inc current-index))
          (= op "jmp")
          (recur
           acc
           (conj executed-indices current-index)
           (+ current-index value))
          )))))

(defn part-2 [input])

(part-2 test-input)