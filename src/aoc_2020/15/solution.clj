(ns aoc-2020.15.solution)

(defn setup-notes [input]
  (loop [remaining input
         turn 0
         notes {}]
  (if (empty? remaining) notes
      (recur (rest remaining) (inc turn) (assoc notes (first remaining) (list turn))))))

(defn calculate-number [notes last-spoken]
  (let [spoken-indices (notes last-spoken)]
    (if (= 1 (count spoken-indices)) 0
        (- (first spoken-indices) (last spoken-indices)))))

(defn solver [input limit]
  (let [notes (setup-notes input)]
    (loop [turn (count input)
           notes notes
           last-spoken (last input)]
      (if (= turn limit) last-spoken
          (let [next-number (calculate-number notes last-spoken)]
            (recur (inc turn)
                   (assoc notes next-number (conj (take 1 (get notes next-number [])) turn))
                   next-number))))))

(defn part-1 [input]
  (solver input 2020))

(defn part-2 [input]
  (solver input 30000000))
