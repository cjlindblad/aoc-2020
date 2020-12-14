(ns aoc-2020.14.solution
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]
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

(defn decode-memory-address [mask address]
  (let [binary-address (Integer/toBinaryString address)
        reversed-address (reverse binary-address)
        reversed-mask (reverse mask)]
    (vec (reverse (map-indexed
     (fn [idx bitmask-bit]
       (let [address-bit (nth reversed-address idx 0)]
         (cond
           (= bitmask-bit \0) address-bit
           (= bitmask-bit \1) bitmask-bit
           (= bitmask-bit \X) bitmask-bit)))
     reversed-mask)))))

(defn indices-of [coll target]
  (loop [remaining coll
         idx 0
         result []]
    (if (empty? remaining) result
        (recur (rest remaining)
               (inc idx)
               (if (= (first remaining) target) (conj result idx)
                   result)))))

(defn get-decoded-addresses [input]
  (map
   (fn [binary-seq]
     (Long/parseLong (apply str binary-seq) 2))
   (let [floating-indexes (indices-of input \X)
        index-count (count floating-indexes)
        binary-permutations (combo/selections [\0 \1] index-count)]
    (map
     (fn [permutation]
       (reduce #(assoc %1 (first %2) (last %2)) input (zipmap floating-indexes permutation)))
     binary-permutations))))

(defn part-2 [input]
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
                 (reduce
                  (fn [acc cur]
                    (assoc acc cur (:value instruction)))
                  memory
                  (get-decoded-addresses (decode-memory-address mask (:address instruction))))))))))))
