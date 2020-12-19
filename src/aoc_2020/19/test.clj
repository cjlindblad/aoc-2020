(ns aoc-2020.19.test
  (:require [clojure.test :refer :all]
            [aoc-2020.19.solution :as s19]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/19/test-input.txt" "\n\n"))
(def test-input-2 (parser/read-input "src/aoc_2020/19/test-input-2.txt" "\n\n"))
(def input (parser/read-input "src/aoc_2020/19/input.txt" "\n\n"))

(deftest day-19-tests
  (testing "solves part 1 with test input"
    (is (= 2 (s19/part-1 test-input))))

  (testing "solves part 1"
    (is (= 178 (s19/part-1 input))))
  
  (testing "solves part 2 with test input"
    (is (= 12 (s19/part-2 test-input-2))))

  (testing "solves part 2"
    (is (= 346 (s19/part-2 input))))
)