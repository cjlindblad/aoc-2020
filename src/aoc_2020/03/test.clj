(ns aoc-2020.03.test
  (:require [clojure.test :refer :all]
            [aoc-2020.03.solution :as s3]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/03/test-input.txt"))
(def input (parser/read-input "src/aoc_2020/03/input.txt"))

(deftest day-03-test
  (testing "solves part 1 with test input"
    (is (= 7 (s3/part-1 test-input))))

  (testing "solves part 1"
    (is (= 153 (s3/part-1 input))))
  
  (testing "solves part 2 with test input"
    (is (= 336 (s3/part-2 test-input))))
  
  (testing "solves part 2"
    (is (= 2421944712 (s3/part-2 input)))))
