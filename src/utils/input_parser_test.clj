(ns input-parser-test
  (:require [clojure.test :refer :all]
            [utils.input-parser :as parser]))

(deftest input-parser-test
  (testing "can read input file"
    (is (= ["a" "b" "c"] (parser/read-input "src/utils/test.txt"))))

  (testing "can read numbers")
  (is (= [1 2 3] (parser/read-numbers "src/utils/numbers.txt"))))
