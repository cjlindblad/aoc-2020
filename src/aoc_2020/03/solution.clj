(ns aoc-2020.03.solution)

(def slope-rules [[1 1] [3 1] [5 1] [7 1] [1 2]])

(defn slope-indices [input right down]
  (let [row-count (count input)
        col-count (count (first input))]
    (map
     (fn [y x] [y (mod (* x right) col-count)])
     (range 0 row-count down)
     (range))))

(defn trees-for-slope [input slope-rule]
  (let [[right down] slope-rule
        indices (slope-indices input right down)]
    (->>
     indices
     (map (fn [[y x]] (nth (nth input y) x)))
     (filter #(= % \#))
     (count))))

(defn part-1 [input]
  (trees-for-slope input (second slope-rules)))

(defn part-2 [input]
  (apply * (map
   #(trees-for-slope input %)
   slope-rules)))
