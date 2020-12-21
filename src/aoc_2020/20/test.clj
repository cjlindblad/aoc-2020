(ns aoc-2020.20.test
  (:require [clojure.test :refer :all]
            [aoc-2020.20.solution :as s20]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/20/test-input.txt" "\n\n"))
(def input (parser/read-input "src/aoc_2020/20/input.txt" "\n\n"))

(deftest day-20-tests
  (testing "solves part 1 with test input"
    (is (= 20899048083289 (s20/part-1 test-input))))

  (testing "solves part 1"
    (is (= 23497974998093 (s20/part-1 input))))
  
  (testing "solves part 2 with test input"
    (is (= 273 (s20/part-2 test-input))))

  (testing "solves part 2"
    (is (= 2256 (s20/part-2 input))))
)