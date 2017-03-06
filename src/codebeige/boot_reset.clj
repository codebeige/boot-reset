(ns codebeige.boot-reset
  {:boot/export-tasks true}
  (:require [boot.core :as boot]))

(boot/deftask reset
  "I'm a pass-thru task."
  []
  ;; merge environment etc
  (println "Pass-thru task setup.")
  (boot/with-pass-thru fs
    (println "Pass-thru: Run functions on filesystem (fs). Next task will run with the same fs.")))
