(ns aoc-2020.01.test
  (:require [clojure.test :refer :all]
            [aoc-2020.01.solution :as s1]
            [utils.input-parser :as parser]))

(deftest day-01-test
  (testing "gets correct answer for part 1 test input"
    (is (= 514579 (s1/part-1 [1721 979 366 299 675 1456]))))
  
  (testing "get correct solution for part 1"
    (is (= 955584 (s1/part-1 (parser/read-numbers "src/aoc_2020/01/input.txt")))))
  
  (testing "gets correct answer for part 2 test input"
    (is (= 241861950 (s1/part-2 [1721 979 366 299 675 1456]))))
  
  (testing "get correct solution for part 2"
    (is (= 287503934 (s1/part-2 (parser/read-numbers "src/aoc_2020/01/input.txt"))))))