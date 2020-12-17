(ns aoc-2020.17.test
  (:require [clojure.test :refer :all]
            [aoc-2020.17.solution :as s17]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/17/test-input.txt" "\n"))
(def input (parser/read-input "src/aoc_2020/17/input.txt" "\n"))

(deftest day-17-tests
  (testing "solves part 1 with test input"
    (is (= 112 (s17/part-1 test-input))))

  (testing "solves part 1"
    (is (= 384 (s17/part-1 input))))
  
  (testing "solves part 2 with test input"
    (is (= 848 (s17/part-2 test-input))))

  (testing "solves part 2"
    (is (= 2012 (s17/part-2 input))))
)
