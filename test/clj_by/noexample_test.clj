
(ns clj-by.noexample-test
  (:require [clj-by.example :refer [do-for-example example]]
            [clojure.test :refer [deftest is]]))


;; commented, or false, or nil
;; (def ^:private +examples-enabled+ false)

(do-for-example

 (def example-var 42)

 (defn example-fn [n]
   (loop [n n, acc 1]
     (if (zero? n)
       acc
       (recur (- n 1) (* n acc))))))

(deftest var-test
  (is (= (resolve 'example-var) nil)))

(deftest example-fn-test
  (is (= (resolve 'example-fn) nil)))

(deftest example-example
  (is (= (example (+ 2 2) => 4) nil)))

(deftest exampe-wrong
  (is (= (try (example (+ 2 2) => 3)
              (catch Exception e (:val (ex-data e))))
         nil)))


