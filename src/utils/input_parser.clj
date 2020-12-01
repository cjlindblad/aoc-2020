(ns utils.input-parser
  (:require [clojure.string :as str]))

(defn read-input [path]
  (->
   path
   (slurp)
   (str/split #"\n")))

(defn read-numbers [path]
  (map #(Integer/parseInt %) (read-input path)))