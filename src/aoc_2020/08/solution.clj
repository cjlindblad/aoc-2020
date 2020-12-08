(ns aoc-2020.08.solution
  (:require [clojure.string :as str]))

(defn parse-instructions [input]
  (map
   (fn [x]
     (let [[op value] (str/split x #" ")]
       {:op op :value (Integer/parseInt value)}))
   input))

(defn in? [col e]
  (some #(= e %) col))

(defn interpreter [input swap-index]
  (let [instructions (parse-instructions input)]
    (loop [acc 0
           executed-indices []
           current-index 0]
      (let [current-instruction (nth instructions current-index -1)
            op (:op current-instruction)
            value (:value current-instruction)]
        (cond
          (= (count instructions) current-index)
          {:correct true :acc acc}
          (or (> current-index (count instructions))
              (in? executed-indices current-index))
          {:corrent false :acc acc}
          (or (= op "nop")
              (and (= op "jmp") (= current-index swap-index)))
          (recur
           acc
           (conj executed-indices current-index)
           (inc current-index))
          (= op "acc")
          (recur
           (+ acc value)
           (conj executed-indices current-index)
           (inc current-index))
          (or (= op "jmp")
              (and (= op "nop") (= current-index swap-index)))
          (recur
           acc
           (conj executed-indices current-index)
           (+ current-index value)))))))

(defn part-1 [input]
  (:acc (interpreter input -1)))

(defn part-2 [input]
  (loop [i 0]
    (let [result (interpreter input i)]
      (cond
        (= i (count input)) :error
        (:correct result) (:acc result)
        :else (recur (inc i))))))
