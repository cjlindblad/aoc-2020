(ns aoc-2020.10.test
  (:require [clojure.test :refer :all]
            [aoc-2020.10.solution :as s10]
            [utils.input-parser :as parser]))

(def test-input (parser/read-numbers "src/aoc_2020/10/test-input.txt"))
(def input (parser/read-numbers "src/aoc_2020/10/input.txt"))

(deftest day-10-tests
   (testing "solves part 1 with test input"
     (is (= 220 (s10/part-1 test-input))))
  
   (testing "solves part 1"
     (is (= 2346 (s10/part-1 input))))
  
   (testing "solves part 2 with test input"
     (is (= 19208 (s10/part-2 test-input))))
  
   (testing "solves part 2"
     (is (= 6044831973376 (s10/part-2 input))))
  )