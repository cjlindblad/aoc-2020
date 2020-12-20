(ns aoc-2020.20.solution
  (:require [utils.input-parser :as parser]
            [clojure.string :as str]
            [clojure.set :as set]
            [clojure.math.combinatorics :as combo]))

(def test-input (parser/read-input "src/aoc_2020/20/test-input.txt" "\n\n"))
(def input (parser/read-input "src/aoc_2020/20/input.txt" "\n\n"))

(defn parse-input [input]
  (map
   (fn [tile]
     (let [[header & lines] (str/split tile #"\n")]
     {:id (Integer/parseInt (re-find #"\d+" header))
      :N (apply str (reverse (first lines)))
      :S (last lines)
      :E (apply str (reverse (map last lines)))
      :W (apply str (map first lines))
      :lines lines
      }))
   input))

(defn input->lookup-table [input]
  (reduce
   (fn [result, tile]
     (let [id (:id tile)
           north [(:N tile) {:id id :direction :N :reversed false}]
           east [(:E tile) {:id id :direction :E :reversed false}]
           south [(:S tile) {:id id :direction :S :reversed false}]
           west [(:W tile) {:id id :direction :W :reversed false}]
           north-reversed [(apply str (reverse (:N tile))) {:id id :direction :N :reversed true}]
           east-reversed [(apply str (reverse (:E tile))) {:id id :direction :E :reversed true}]
           south-reversed [(apply str (reverse (:S tile))) {:id id :direction :S :reversed true}]
           west-reversed [(apply str (reverse (:W tile))) {:id id :direction :W :reversed true}]]
       (assoc result
              (first north) (conj (get result (first north) []) (last north))
              (first east) (conj (get result (first east) []) (last east))
              (first south) (conj (get result (first south) []) (last south))
              (first west) (conj (get result (first west) []) (last west))
              (first north-reversed) (conj (get result (first north-reversed) []) (last north-reversed))
              (first east-reversed) (conj (get result (first east-reversed) []) (last east-reversed))
              (first south-reversed) (conj (get result (first south-reversed) []) (last south-reversed))
              (first west-reversed) (conj (get result (first west-reversed) []) (last west-reversed)))))   
   {}
   input))

(defn filter-connections [lookup-table]
  (into
   {}
   (filter
    (fn [[_ v]] (= 2 (count v)))
    lookup-table)))

(defn connection-count-by-id [connections]
  (reduce
   (fn [result [k v]] (assoc result k (count v)))
   {}
   (group-by :id (flatten (vals connections)))))

(defn get-corners [connection-count-by-id]
  (map first
       (filter
        (fn [[_ v]] (= v 4))
        connection-count-by-id)))
  
(defn part-1 [input]
  (->>
   input
   (parse-input)
   (input->lookup-table)
   (filter-connections)
   (connection-count-by-id)
   (get-corners)
   (reduce *)))

(part-1 input)
