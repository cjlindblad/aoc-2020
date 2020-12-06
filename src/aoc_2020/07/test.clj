(ns aoc-2020.07.test
  (:require [clojure.test :refer :all]
            [aoc-2020.07.solution :as s7]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/07/test-input.txt" "\n\n"))
(def input (parser/read-input "src/aoc_2020/07/input.txt" "\n\n"))

(deftest day-07-tests
   (testing "solves part 1 with test input"
     (is (= 0 (s7/part-1 test-input))))
  
;;    (testing "solves part 1"
;;      (is (= 0 (s7/part-1 input))))
  
;;    (testing "solves part 2 with test input"
;;      (is (= 0 (s7/part-2 test-input))))
  
;;    (testing "solves part 2"
;;      (is (= 0 (s7/part-2 input))))
  )