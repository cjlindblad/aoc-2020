(ns aoc-2020.09.test
  (:require [clojure.test :refer :all]
            [aoc-2020.09.solution :as s9]
            [utils.input-parser :as parser]))

(def test-input (parser/read-numbers "src/aoc_2020/09/test-input.txt"))
(def input (parser/read-numbers "src/aoc_2020/09/input.txt"))

(deftest day-09-tests
   (testing "solves part 1 with test input"
     (is (= 127 (s9/part-1 test-input 5))))
  
   (testing "solves part 1"
     (is (= 1930745883 (s9/part-1 input 25))))
  
   (testing "solves part 2 with test input"
     (is (= 62 (s9/part-2 test-input 5))))
  
   (testing "solves part 2"
     (is (= 268878261 (s9/part-2 input 25))))
  )