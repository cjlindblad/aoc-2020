(ns aoc-2020.20.solution
  (:require [clojure.string :as str]))

(defn edges-for-lines [lines]
  {:N (apply str (first lines))
   :S (last lines)
   :E (apply str (map last lines))
   :W (apply str (map first lines))})

(defn parse-input [input]
  (map
   (fn [tile]
     (let [[header & lines] (str/split tile #"\n")]
       (into
        {:id (Integer/parseInt (re-find #"\d+" header))
         :lines lines}
        (edges-for-lines lines))))
   input))

(defn input->lookup-table [input]
  (reduce
   (fn [result tile]
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

(defn input->tiles-by-id [raw-input]
  (let [input (parse-input raw-input)]
    (reduce
     (fn [result tile]
       (assoc result
              (:id tile) tile))
     {}
     input)))

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

(defn get-connections [input]
  (->
   input
   (parse-input)
   (input->lookup-table)
   (filter-connections)))

(defn rotate-lines [lines]
  (for [x (range (dec (count (first lines))) -1 -1)]
    (apply str (map #(nth % x) lines))))

(defn flip-lines [lines]
  (map (fn [line] (apply str (reverse line))) lines))

(defn image-variations [image]
  [image
   (flip-lines image)
   (rotate-lines image)
   (flip-lines (rotate-lines image))
   (rotate-lines (rotate-lines image))
   (flip-lines (rotate-lines (rotate-lines image)))
   (rotate-lines (rotate-lines (rotate-lines image)))
   (flip-lines (rotate-lines (rotate-lines (rotate-lines image))))])

(defn tile-variations [tile]
  (let [lines (:lines tile)]
    (map
     #(into (edges-for-lines %) {:lines % :id (:id tile)})
     (image-variations lines))))

(defn upper-left-corner-id [connections]
  (first
   (first
    (filter
     (fn [[_ v]] (= v #{:S :E}))
     (reduce
      (fn [result [k v]]
        (assoc result k (set (map :direction v))))
      {}
      (group-by :id (flatten (vals connections))))))))

(defn edge-to-match [y x]
  (cond
    ; first of rows
    (= x 0) {:y (dec y) :x x :direction :S :next-direction :N}
    ; rest of rows
    (> x 0) {:y y :x (dec x) :direction :E :next-direction :W}))

(defn ordered-tiles [input]
  (let [tiles-by-id (input->tiles-by-id input)
        edge-length (int (Math/sqrt (count tiles-by-id)))
        lookup-table (input->lookup-table (parse-input input))
        connections (get-connections input)
        upper-left-id (upper-left-corner-id connections)
        tiles [[(get tiles-by-id upper-left-id)]]
        max-y (dec edge-length)
        max-x (dec edge-length)]
    (loop [result tiles y 0 x 1 n 0]
      (cond
        (or (and (= x 0) (= y (inc max-y))))
        result
        :else
        (recur
         (let [edge-to-match (edge-to-match y x)
               tile-to-match (get-in result [(:y edge-to-match) (:x edge-to-match)])
               edge ((:direction edge-to-match) tile-to-match)
               next-tile-id (:id (first (filter #(not (= (:id %) (:id tile-to-match))) (get lookup-table edge))))
               next-tile (get tiles-by-id next-tile-id)
               rotated-next-tile (first (filter #(= edge ((:next-direction edge-to-match) %)) (tile-variations next-tile)))]
           (assoc-in result [y] (vec (conj (get-in result [y]) rotated-next-tile))))
         (if (= x max-x) (inc y) y)
         (if (< x max-x) (inc x) 0)
         (inc n))))))

(defn strip-border [lines]
  (->>
   lines
   (drop 1)
   (drop-last 1)
   (map (fn [line] (apply str (drop-last 1 (drop 1 line)))))))

(defn build-image [nested-tiles]
  (flatten
   (map
    (fn [tile-row]
      (flatten
       (map
        (fn [idx]
          (apply
           str
           (map
            (fn [tile]
              (nth (strip-border (:lines tile)) idx))
            tile-row)))
        (range 0 8))))
    nested-tiles)))

(defn monster-regex [edge-length]
  (let [monster-length 20 padding (- edge-length monster-length)]
    (re-pattern (str "(?=(.{18}#.{" (+ padding 1) "}#.{4}##.{4}##.{4}###.{" (+ padding 1) "}#.{2}#.{2}#.{2}#.{2}#.{2}#))"))))

(defn monster-substring-length [edge-length]
  (let [monster-length 20 padding (- edge-length monster-length)]
    (+ 20 20 17 padding padding)))

(defn part-2 [input]
  (let [image (build-image (ordered-tiles input))
        edge-length (count (first image))
        monster-regex (monster-regex edge-length)
        image-varations (map str/join (image-variations image))
        nessies (apply max (map #(count (re-seq monster-regex %)) image-varations))]
    (- (count (filter #(= % \#) (str/join image))) (* nessies 15))))
