(ns codebeige.boot-reset
  {:boot/export-tasks true}
  (:require [boot.core :as boot]
            [boot.util :as util]))

(def map-ns
  (comp (map #(some-> % namespace symbol)) (remove nil?)))

(defn require-ns [& syms]
  (doseq [n (into #{} map-ns syms)] (require n)))

(boot/deftask reset
  "Execute side effects (e.g. reset your development system) on file changes.

  Middleware for wrapping subsequent handlers into calls of :stop before and
  :start after execution.

  Example:
  boot watch reload --start dev/start --stop dev/stop refresh"
  [x stop  SYMBOL sym "The function to call before wrapped tasks are run."
   s start SYMBOL sym "The function to call after wrapped tasks did complete."]
  (require-ns stop start)
  (comp
   (boot/with-pass-thru _
     (util/info "Stop: executing %s...\n" stop)
     ((resolve stop)))
   (boot/with-post-wrap _
     (util/info "Start: executing %s...\n" start)
     ((resolve start)))))
