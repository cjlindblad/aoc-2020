(ns aoc-2020.12.test
  (:require [clojure.test :refer :all]
            [aoc-2020.12.solution :as s12]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/12/test-input.txt"))
(def input (parser/read-input "src/aoc_2020/12/input.txt"))

(deftest day-12-tests
   (testing "solves part 1 with test input"
     (is (= 25 (s12/part-1 test-input))))
  
   (testing "solves part 1"
     (is (= 882 (s12/part-1 input))))
  
   (testing "solves part 2 with test input"
     (is (= 286 (s12/part-2 test-input))))
  
   (testing "solves part 2"
     (is (= 28885 (s12/part-2 input)))))
