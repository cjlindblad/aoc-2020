(ns aoc-2020.04.solution)

(defn key-value-for [key input]
  (rest (re-find (re-pattern (str "(" key "):([\\S]+)\\s*")) input)))

(defn in-range [value from to]
  (and (>= value from) (<= value to)))

(def validators
  {"byr" #(in-range (Integer/parseInt %) 1920 2002)
   "iyr" #(in-range (Integer/parseInt %) 2010 2020)
   "eyr" #(in-range (Integer/parseInt %) 2020 2030)
   "hcl" #(re-matches #"#[0-9a-f]{6}" %)
   "ecl" #(re-matches #"amb|blu|brn|gry|grn|hzl|oth" %)
   "pid" #(re-matches #"\d{9}" %)
   "hgt" #(cond
            (re-find #"cm$" %)
            (let [cm (Integer/parseInt (re-find #"\d+" %))]
              (in-range cm 150 193))
            (re-find #"in$" %)
            (let [in (Integer/parseInt (re-find #"\d+" %))]
              (in-range in 59 76))
            :else nil)})

(defn has-required-fields? [line]
  (every? not-empty (map #(key-value-for % line) (keys validators))))

(defn has-valid-fields? [line]
  (->>
   (keys validators)
   (map #(key-value-for % line))
   (map (fn [[key value]] ((validators key) value)))
   (every? identity)))

(defn part-1 [input]
  (->>
   input
   (filter has-required-fields?)
   (count)))

(defn part-2 [input]
  (->>
   input
   (filter has-required-fields?)
   (filter has-valid-fields?)
   (count)))
