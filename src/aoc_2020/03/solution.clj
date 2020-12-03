(ns aoc-2020.03.solution)

(defn toboggan-indices [input right down]
  (let [row-count (count input)
        col-count (count (first input))]
    (map
     (fn [y x] [y (mod (* x right) col-count)])
     (take-nth down (range row-count))
     (range))))

(def slope-rules [[1 1] [3 1] [5 1] [7 1] [1 2]])

(defn slope-indices [input slope-rules]
  (map
   (fn [[right down]]
     (toboggan-indices input (int right) (int down)))
   slope-rules))

(defn trees-for-slope [input slope-rule]
  (let [[right down] slope-rule
        indices (toboggan-indices input right down)]
    (count
     (filter
      #(= % \#)
      (map
       #(nth (nth input (first %)) (last %))
       indices)))))

(defn part-1 [input]
  (trees-for-slope input (second slope-rules)))

(defn part-2 [input]
  (apply * (map
   #(trees-for-slope input %)
   slope-rules)))
