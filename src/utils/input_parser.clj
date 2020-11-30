(ns utils.input-parser
  (:require [clojure.string :as str]))

(defn read-input [path]
  (->
   path
   (slurp)
   (str/split #"\n")))
