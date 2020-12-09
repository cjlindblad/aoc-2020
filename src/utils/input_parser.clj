(ns utils.input-parser
  (:require [clojure.string :as str]))

(defn read-input
  ([path]
   (read-input path "\n"))
  ([path delimiter]
  (->
   path
   (slurp)
   (str/split (re-pattern delimiter)))))

(defn read-numbers [path]
  (map #(Long/valueOf %) (read-input path)))