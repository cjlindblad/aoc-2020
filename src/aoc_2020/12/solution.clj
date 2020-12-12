(ns aoc-2020.12.solution)

(defn input->instructions [input]
  (map
   #(hash-map :dir (first %) :distance (Integer/parseInt (subs % 1)))
   input))

(defn add-distance [direction distance [y x]]
  (cond
    (= direction \N) [(- y distance) x]
    (= direction \S) [(+ y distance) x]
    (= direction \E) [y (+ x distance)]
    (= direction \W) [y (- x distance)]))

(defn next-coord [instruction [y x] direction]
  (let [dir (:dir instruction)
        distance (:distance instruction)]
    (cond
      (= dir \R) [y x]
      (= dir \L) [y x]
      (= dir \F) (add-distance direction distance [y x]) 
      :else (add-distance dir distance [y x]))))

(def idx-by-direction
  {\N 0
   \E 1
   \S 2
   \W 3})

(def direction-by-idx
  {0 \N
   1 \E
   2 \S
   3 \W})

(defn next-direction [instruction direction]
  (let [dir (:dir instruction)
        distance (:distance instruction)
        steps (/ distance 90)]
    (cond
      (= dir \R) (direction-by-idx (mod (+ (idx-by-direction direction) steps) 4))
      (= dir \L) (direction-by-idx (mod (- (idx-by-direction direction) steps) 4))
      :else direction)))

(defn part-1 [input]
  (loop [instructions (input->instructions input)
         current-coord [0 0]
         current-direction \E]
    (let [current-instruction (first instructions)]
      (cond (empty? instructions) (+ (Math/abs (first current-coord)) (Math/abs (last current-coord)))
            :else (recur
                   (rest instructions)
                   (next-coord current-instruction current-coord current-direction)
                   (next-direction current-instruction current-direction))))))

(defn next-ship-coord [instruction [ship-y ship-x] [waypoint-y waypoint-x]]
  (let [dir (:dir instruction)
        distance (:distance instruction)]
    (cond (= \F dir) [(+ ship-y (* distance waypoint-y)) (+ ship-x (* distance waypoint-x))]
          :else [ship-y ship-x])))

(defn translate-offset [direction amount [y x]]
  (let [steps (/ amount 90)]
      (loop [[y x] [y x]
             steps steps]
        (cond (= steps 0)
              [y x]
              :else
              (recur
               (if (= direction \R) [x (- y)] [(- x) y])
               (dec steps))))))

(defn next-waypoint-offset [instruction [y x]]
  (let [dir (:dir instruction)
        distance (:distance instruction)]
    (cond
      (= \R dir) (translate-offset dir distance [y x])
      (= \L dir) (translate-offset dir distance [y x])
      (= \F dir) [y x]
      :else (add-distance dir distance [y x]))))

(defn part-2 [input]
  (loop [instructions (input->instructions input)
         ship-coord [0 0]
         waypoint-offset [-1 10]]
    (let [current-instruction (first instructions)]
      (cond
        (empty? instructions) (+ (Math/abs (first ship-coord)) (Math/abs (last ship-coord)))
        :else (recur
               (rest instructions)
               (next-ship-coord current-instruction ship-coord waypoint-offset)
               (next-waypoint-offset current-instruction waypoint-offset))))))
