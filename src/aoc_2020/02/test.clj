(ns aoc-2020.02.test
  (:require [clojure.test :refer :all]
            [aoc-2020.02.solution :as s2]
            [utils.input-parser :as parser]))

(def test-input (parser/read-input "src/aoc_2020/02/test-input.txt"))
(def input (parser/read-input "src/aoc_2020/02/input.txt"))

(deftest day-02-test
  (testing "parses letter limit"
    (is (= '(1 3) (s2/parse-letter-limit "1-3 b: cdefg"))))
  
  (testing "parses letter"
    (is (= "b" (s2/parse-letter "1-3 b: cdefg"))))
  
  (testing "parses password"
    (is (= "cdefg" (s2/parse-password "1-3 b: cdefg"))))
  
  (testing "counts letter occurences"
    (is (= 3 (s2/letter-occurences "a" "aabca"))))
  
  (testing "finds valid password"
    (is (= true (s2/is-valid-password "1-3 a: abcde"))))
  
  (testing "finds invalid password"
    (is (= false (s2/is-valid-password "1-3 b: cdefg"))))
  
  (testing "gets correct answer for part 1 test input"
    (is (= 2 (s2/part-1 test-input))))
  
  (testing "gets correct answer for part 1"
    (is (= 396 (s2/part-1 input)))))
