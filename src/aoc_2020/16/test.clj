(ns aoc-2020.16.test
  (:require [clojure.test :refer :all]
            [aoc-2020.16.solution :as s16]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/16/test-input.txt" "\n\n"))
(def input (parser/read-input "src/aoc_2020/16/input.txt" "\n\n"))

(deftest day-16-tests
  (testing "solves part 1 with test input"
    (is (= 71 (s16/part-1 test-input))))

  (testing "solves part 1"
    (is (= 25984 (s16/part-1 input))))

  (testing "solves part 2"
    (is (= 1265347500049 (s16/part-2 input)))))
