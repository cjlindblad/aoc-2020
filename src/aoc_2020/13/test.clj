(ns aoc-2020.13.test
  (:require [clojure.test :refer :all]
            [aoc-2020.13.solution :as s13]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/13/test-input.txt"))
(def input (parser/read-input "src/aoc_2020/13/input.txt"))

(deftest day-13-tests
   (testing "solves part 1 with test input"
     (is (= 295 (s13/part-1 test-input))))
  
   (testing "solves part 1"
     (is (= 2092 (s13/part-1 input))))
  
;;    (testing "solves part 2 with test input"
;;      (is (= 0 (s13/part-2 test-input))))
  
;;    (testing "solves part 2"
;;      (is (= 0 (s13/part-2 input))))
  )
