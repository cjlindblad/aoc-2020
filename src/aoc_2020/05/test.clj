(ns aoc-2020.05.test
  (:require [clojure.test :refer :all]
            [aoc-2020.05.solution :as s5]
            [utils.input-parser :as parser]))

(def input (parser/read-input "src/aoc_2020/05/input.txt" "\n"))

(deftest day-05-tests
  (testing "solves part 1"
    (is (= 880 (s5/part-1 input))))
  
  (testing "solves part 2"
    (is (= 731 (s5/part-2 input)))))