(ns aoc-2020.14.test
  (:require [clojure.test :refer :all]
            [aoc-2020.14.solution :as s14]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/14/test-input.txt"))
(def input (parser/read-input "src/aoc_2020/14/input.txt"))

(deftest day-14-tests
   (testing "solves part 1 with test input"
     (is (= 165 (s14/part-1 test-input))))
  
   (testing "solves part 1"
     (is (= 14862056079561 (s14/part-1 input))))
  
;;    (testing "solves part 2 with test input"
;;      (is (= 0 (s14/part-2 test-input))))
  
;;    (testing "solves part 2"
;;      (is (= 0 (s14/part-2 input))))
  )