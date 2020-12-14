(ns aoc-2020.14.solution
  (:require [clojure.string :as str]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/14/test-input.txt"))
(def input (parser/read-input "src/aoc_2020/14/input.txt"))

(defn parse-instruction [line]
  (cond (re-matches #"mask.*" line) {:op :mask
                                     :mask (last (re-matches #".*= (.+)" line))}
        (re-matches #"mem.*" line) {:op :mem
                                    :address (Integer/parseInt (last (re-matches #".*\[(\d+)\].*" line)))
                                    :value (Integer/parseInt (last (re-matches #".*= (\d+)" line)))}))

(defn apply-mask [mask value]
  (let [binary-value (Integer/toBinaryString value)
        reversed-value (reverse binary-value)
        reversed-mask (reverse mask)]
    (Long/parseLong
     (apply str
            (reverse
             (map-indexed
              (fn [idx el]
                (let [value (nth reversed-value idx 0)]
                  (cond (or (= el \0) (= el \1)) el
                        :else value)))
              reversed-mask))) 2)))

(defn part-1 [input]
  (apply + (vals (loop [remaining-input input
         mask ""
         memory {}]
    (if (empty? remaining-input)
      memory
      (let [instruction (parse-instruction (first remaining-input))
            op (:op instruction)]
        (case op
          :mask
          (recur (rest remaining-input)
                 (:mask instruction)
                 memory)
          :mem
          (recur (rest remaining-input)
                 mask
                 (assoc memory (:address instruction) (apply-mask mask (:value instruction)))))))))))
