(ns aoc-2020.04.test
  (:require [clojure.test :refer :all]
            [aoc-2020.04.solution :as s4]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/04/test-input.txt" "\n\n"))
(def input (parser/read-input "src/aoc_2020/04/input.txt" "\n\n"))

test-input

(deftest day-04-tests
   (testing "solves part 1 with test input"
     (is (= 2 (s4/part-1 test-input))))
  
  (testing "solves part 1"
    (is (= 210 (s4/part-1 input))))
  
  (testing "solves part 2 with test input"
    (is (= 2 (s4/part-2 test-input))))
  
  (testing "solves part 2"
    (is (= 131 (s4/part-2 input)))))