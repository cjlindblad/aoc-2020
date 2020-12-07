(ns aoc-2020.07.test
  (:require [clojure.test :refer :all]
            [aoc-2020.07.solution :as s7]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/07/test-input.txt" "\n"))
(def input (parser/read-input "src/aoc_2020/07/input.txt" "\n"))

(deftest day-07-tests
   (testing "solves part 1 with test input"
     (is (= 4 (s7/part-1 test-input))))
  
   (testing "solves part 1"
     (is (= 274 (s7/part-1 input)))) ; 584 too high
  
   (testing "solves part 2 with test input"
     (is (= 32 (s7/part-2 test-input))))
  
   (testing "solves part 2"
     (is (= 158730 (s7/part-2 input))))
  )