(ns aoc-2020.21.test
  (:require [clojure.test :refer :all]
            [aoc-2020.21.solution :as s21]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/21/test-input.txt" "\n"))
(def input (parser/read-input "src/aoc_2020/21/input.txt" "\n"))

(deftest day-21-tests
  (testing "solves part 1 with test input"
    (is (= 5 (s21/part-1 test-input))))

  (testing "solves part 1"
    (is (= 2125 (s21/part-1 input))))
  
  (testing "solves part 2 with test input"
    (is (= "mxmxvkd,sqjhc,fvjkl" (s21/part-2 test-input))))

  (testing "solves part 2"
    (is (= "phc,spnd,zmsdzh,pdt,fqqcnm,lsgqf,rjc,lzvh" (s21/part-2 input))))
)