(ns aoc-2020.15.test
  (:require [clojure.test :refer :all]
            [aoc-2020.15.solution :as s15]))

(def test-input [0 3 6])
(def input [7 14 0 17 11 1 2])

(deftest day-15-tests
   (testing "solves part 1 with test input"
     (is (= 436 (s15/part-1 test-input))))
  
   (testing "solves part 1"
     (is (= 206 (s15/part-1 input))))
  
   (testing "solves part 2"
     (is (= 955 (s15/part-2 input))))
  )