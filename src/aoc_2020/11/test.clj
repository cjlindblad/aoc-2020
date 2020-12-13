(ns aoc-2020.11.test
  (:require [clojure.test :refer :all]
            [aoc-2020.11.solution :as s11]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/11/test-input.txt"))
(def input (parser/read-input "src/aoc_2020/11/input.txt"))

(deftest day-11-tests
   (testing "solves part 1 with test input"
     (is (= 37 (s11/part-1 test-input))))
  
   (testing "solves part 1"
     (is (= 2441 (s11/part-1 input))))
  
;;    (testing "solves part 2 with test input"
;;      (is (= 0 (s11/part-2 test-input))))
  
;;    (testing "solves part 2"
;;      (is (= 0 (s11/part-2 input))))
  )