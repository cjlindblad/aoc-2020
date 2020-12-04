(ns aoc-2020.04.solution)

(defn has-required-fields? [line]
  (and
   (re-find #"byr:" line)
   (re-find #"iyr:" line)
   (re-find #"eyr:" line)
   (re-find #"hgt:" line)
   (re-find #"hcl:" line)
   (re-find #"ecl:" line)
   (re-find #"pid:" line)))

(defn value-for [key input]
  (last (re-find (re-pattern (str key ":([\\S]+)\\s*")) input)))

(defn in-range [value from to]
  (and
   (>= value from)
   (<= value to)))

(defn valid-byr? [byr]
  (in-range (Integer/parseInt byr) 1920 2002))

(defn valid-iyr? [iyr]
  (in-range (Integer/parseInt iyr) 2010 2020))

(defn valid-eyr? [eyr]
  (in-range (Integer/parseInt eyr) 2020 2030))

(defn valid-hgt? [hgt]
  (cond
    (re-find #"cm$" hgt)
    (let [cm (Integer/parseInt (re-find #"\d+" hgt))]
      (in-range cm 150 193))
    (re-find #"in$" hgt)
    (let [in (Integer/parseInt (re-find #"\d+" hgt))]
      (in-range in 59 76))
    :else nil))  

(defn valid-hcl? [hcl]
  (re-matches #"#[0-9a-f]{6}" hcl))

(defn valid-ecl? [ecl]
  (re-matches #"amb|blu|brn|gry|grn|hzl|oth" ecl))

(defn valid-pid? [pid]
  (re-matches #"\d{9}" pid))

(defn has-valid-fields? [line]
  (and
   (valid-byr? (value-for "byr" line))
   (valid-iyr? (value-for "iyr" line))
   (valid-eyr? (value-for "eyr" line))
   (valid-hgt? (value-for "hgt" line))
   (valid-hcl? (value-for "hcl" line))
   (valid-ecl? (value-for "ecl" line))
   (valid-pid? (value-for "pid" line))))

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
