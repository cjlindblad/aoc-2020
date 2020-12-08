(ns aoc-2020.08.test
  (:require [clojure.test :refer :all]
            [aoc-2020.08.solution :as s8]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/08/test-input.txt" "\n"))
(def input (parser/read-input "src/aoc_2020/08/input.txt" "\n"))

(deftest day-08-tests
   (testing "solves part 1 with test input"
     (is (= 5 (s8/part-1 test-input))))
  
   (testing "solves part 1"
     (is (= 1671 (s8/part-1 input))))
  
   (testing "solves part 2 with test input"
     (is (= 8 (s8/part-2 test-input))))
  
   (testing "solves part 2"
     (is (= 892 (s8/part-2 input))))
  )
