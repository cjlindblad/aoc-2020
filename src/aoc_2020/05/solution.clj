(ns aoc-2020.05.solution)

(defn lower-half [range]
  (take (/ (count range) 2) range))

(defn upper-half [range]
  (take-last (/ (count range) 2) range))

(def rules {\F lower-half, \B upper-half, \R upper-half, \L lower-half})

(defn search [steps range]
  (first (reduce #((rules %2) %1) range steps)))

(defn seat-id [boarding-pass]
  (+ (* 8 (search (take 7 boarding-pass) (range 128)))
     (search (take-last 3 boarding-pass) (range 8))))

(defn part-1 [input]
  (apply max (map seat-id input)))

(defn part-2 [input]
  (let [ids-with-gap (sort (map seat-id input))
        all-ids (range (first ids-with-gap) (inc (last ids-with-gap)))]
    (first (clojure.set/difference (set all-ids) (set ids-with-gap)))))
