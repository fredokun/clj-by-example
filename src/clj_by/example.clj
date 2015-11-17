
(ns clj-by.example
  (:import (clojure.lang Var Symbol)))


;;{

;; # The Example macro

;;}

;;{

;; ## Inline examples

;; The `example` macro is the Lisp utility I use to
;; support my litterate-programming style, providing
;; inline examples that may also be run as tests, if
;; the `+examples-enabled+` variable is rebound
;; to a truthy value (most likely `true`).

;;}


;(Var intern *ns* (Symbol intern "*examples-enabled*") Boolean True)

;(def examples-enabled-var (Var intern (Symbol intern (*ns* '*examples-enabled* nil))))

(comment
  (example (+ 2 (* 3 5)) => (+ 4 13))

  ;; should yield:

  (let [res (+ 2 (* 3 5))
        val (+ 4 13)]
    (if (= res val)
      val
      (throw ex-info "Example failed" {:expr '(+ 2 (* 3 5))
                                       :val '17 })))
  )


(defmacro example
  "Show as an example the evaluation of `expr` as `val`.
  Evaluate the example as a test an throw an exception if it fails,
  if a variable `+examples-enabled+` is set and bound to a truthy value."
  [expr sep val & {:keys [equiv?]
                   :or { equiv? =}}]
  (when (not= (name sep) "=>")
    (throw (ex-info "Missing '=>' in example" {:expr `(quote ~expr)
                                               :sep `(quote ~sep)
                                               :val `(quote ~val)})))
  (when-let [ex-var (find-var (symbol (str *ns*) "+examples-enabled+"))]
    (when (var-get ex-var)
      `(let [expr# ~expr
             val# ~val]
         (if (~equiv? expr# val#)
           val#
           (throw (ex-info "Example failed" {:expr ~`(quote ~expr)
                                             :val expr#
                                             :expected  ~`(quote ~val) })))))))

;;  (macroexpand-1 '(example (+ 2 12) => 13))


(defmacro do-for-example
  "A `do`-like construct only compiled if a
variable `+examples-enabled+` is bound in the current namespace.
This is used to prepare examples with some variable or function
  definitions."
  [& body]
  (when-let [ex-var (find-var (symbol (str *ns*) "+examples-enabled+"))]
    (when (var-get ex-var)
      `(do ~@body))))



