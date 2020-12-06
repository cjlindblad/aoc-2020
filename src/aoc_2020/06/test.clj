(ns aoc-2020.06.test
  (:require [clojure.test :refer :all]
            [aoc-2020.06.solution :as s6]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/06/test-input.txt" "\n\n"))
(def input (parser/read-input "src/aoc_2020/06/input.txt" "\n\n"))

(deftest day-06-tests
   (testing "solves part 1 with test input"
     (is (= 11 (s6/part-1 test-input))))
  
   (testing "solves part 1"
     (is (= 6778 (s6/part-1 input))))
  
   (testing "solves part 2 with test input"
     (is (= 6 (s6/part-2 test-input))))
  
   (testing "solves part 2"
     (is (= 3406 (s6/part-2 input)))))