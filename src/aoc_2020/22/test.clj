(ns aoc-2020.22.test
  (:require [clojure.test :refer :all]
            [aoc-2020.22.solution :as s22]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/22/test-input.txt" "\n\n"))
(def input (parser/read-input "src/aoc_2020/22/input.txt" "\n\n"))

(deftest day-22-tests
  (testing "solves part 1 with test input"
    (is (= 306 (s22/part-1 test-input))))

  (testing "solves part 1"
    (is (= 32033 (s22/part-1 input))))
  
  (testing "solves part 2 with test input"
    (is (= 291 (s22/part-2 test-input))))

  (testing "solves part 2"
    (is (= 34901 (s22/part-2 input))))
)