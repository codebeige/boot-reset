(ns codebeige.boot-reset
  {:boot/export-tasks true}
  (:require [boot.core :as boot]
            [boot.util :as util]
            [clojure.set :as set]))

(def map-ns
  (comp (map #(some-> % namespace symbol)) (remove nil?)))

(defn require-ns [& syms]
  (doseq [n (into #{} map-ns syms)] (require n)))

(defn maybe-filter [filters files]
  (if (seq filters) (boot/by-re filters files) files))

(defn user-files-diff [prev-fs fs]
  (set/union (->> fs (boot/fileset-diff prev-fs) boot/user-files)
             (->> fs (boot/fileset-removed prev-fs) boot/user-files)))

(defn changed? [prev-fs fs filters]
  (->> fs
       (user-files-diff prev-fs)
       (maybe-filter filters)
       count
       (< 0)))

(defn maybe-run [sym]
  (when-let [run (some-> sym resolve)]
    (util/info "Running %s...\n" sym)
    (run)))

(boot/deftask reset
  "Execute side effects (e.g. reset your development system) on file changes.

  Middleware for wrapping subsequent handlers into calls of :stop before and
  :start after execution. Provided callback functions should be idempotent.

  Example:
  boot watch reload --start dev/start --stop dev/stop refresh"
  [x stop  SYMBOL sym "The function to call before wrapped tasks are run."
   s start SYMBOL sym "The function to call after wrapped tasks did complete."
   f files PATTERNS #{regex} "File patterns that trigger a reset."]
  (require-ns stop start)
  (let [prev-fs (atom nil)]
    (fn [handler]
      (fn [fs]
        (let [run? (changed? @prev-fs fs files)]
          (when run? (maybe-run stop))
          (let [fs* (handler fs)]
            (when run? (maybe-run start))
            (reset! prev-fs fs*)
            fs*))))))
