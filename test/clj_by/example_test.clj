
(ns clj-by.example-test
  (:require [clj-by.example :refer [do-for-example example]]
            [clojure.test :refer [deftest is]]))


(def ^:private +examples-enabled+ true)

(do-for-example

 (def example-var 42)

 (defn example-fn [n]
   (loop [n n, acc 1]
     (if (zero? n)
       acc
       (recur (- n 1) (* n acc))))))

(deftest var-test
  (is (= example-var 42)))

(deftest example-fn-test
  (is (= (example-fn 5) 120)))

(deftest example-example
  (is (= (example (+ 2 2) => 4) 4)))

(deftest exampe-wrong
  (is (= (try (example (+ 2 2) => 3)
              (catch Exception e (:val (ex-data e))))
         4)))


