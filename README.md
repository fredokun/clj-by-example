# clj-by-example

Self-testing examples in your clojure code.

## Motivation

The usual workflow for clojure code is to
write the functional code under `src/` and the unit
tests under `test/`.   While it is an advisable thing
 to separate the two, I think it is useful to keep
  a subset of the tests directly below the function
   and macro definitions, as *examples*.

There are two main benefits:

 - it is easier to mix coding & testing interactively

 - the examples contribute to the code documentation,
  showing examples of uses just below the definitions.

It is also a way to provide a lean support for literate
 programming, although some further processing is needed.

## Usage

The library provides two macros: `example` and `do-for-example`.

-------------------------
clj-by.example/example
([expr sep val & {:keys [equiv?], :or {equiv? =}}])
Macro
  Show as an example the evaluation of `expr` as `val`.
  Evaluate the example as a test an throw an exception if it fails,
  if a variable `+examples-enabled+` is set and bound to a truthy value.

-------------------------
clj-by.example/do-for-example
([& body])
Macro
  A `do`-like construct only compiled if a
variable `+examples-enabled+` is bound in the current namespace.
This is used to prepare examples with some variable or function
  definitions.


##  Example

Suppose you have a file `funtastic.clj` that needs some coding.

```clojure
;;; file funtastic.clj
(ns super-tool.funtastic
  (:require 'clj-by.example :refer [example do-for-example]))
```

```clojure
;; define this to a truthy value for self-testing examples,
;; otherwise ... don't
(def ^:private +examples-enabled+ true)
```

You first want to code the most boring function.

```clojure
(defn fact
  "Return a boring result."
  [n] (if (zero? n)
          1
          (* n (fact (- n 1)))))
```

Then you add some examples:

```clojure
(example (fact 0) => 1) ;; returns 1

(example (fact 5) => 120)  ;; return 120
```

If you want to have some definitions ready
for your examples, use the `do-for-example` macro...

```clojure
(do-for-example
  (def my-example-var 6))
```

Then the variable `my-example-var` is available for your
tests (and also outside the tests, please be careful).

```clojure
(example (fact my-example-var) => 600) ;; return 600
```

Finally, the self-testing examples throw an exception in
 case of failure, this is a *fail-first* strategy and
  thus it is a complement and not a replacement for standard unit testing.

```clojure
(example (fact 3) => 1)
;; throws ExceptionInfo Example failed  clojure.core/ex-info
;;     Example failed
;;     {:val 6, :expr (fact 3), :expected 1}
```

## Disable self-testing

If in your namespace you do not define `+examples-enabled+` or
 set it to a falsy value, then the self-testing mode is disabled.

```clojure
;; define this to a truthy value for self-testing examples,
;; otherwise ... don't
(def ^:private +examples-enabled+ false) ;; remark: false!
```
...

```clojure
(example (fact 0) => 1) ;; return nil

(example (fact 5) => 120)  ;; return nil
```

If you want to have some definitions ready
for your examples, use the `do-for-example` macro...

```clojure
(do-for-example
  (def my-example-var 6)) ;; nothing defined, returns nil
```
...

```clojure
(example (fact my-example-var) => 600) ;; return nil
```
...

```clojure
(example (fact 3) => 1) ;; return nil
```


## License

Copyright © 2015 Frederic Peschanski under the MIT License

