(set-env! :resource-paths #{"src"}
          :dependencies   '[[org.clojure/clojure "1.9.0-alpha14" :scope "provided"]
                            [adzerk/bootlaces "0.1.13" :scope "test"]
                            [boot/core "2.7.1" :scope "test"] ])

(require
 '[adzerk.bootlaces :refer [bootlaces!
                            build-jar
                            push-snapshot
                            push-release]]
 '[codebeige.boot-reset :refer [reset]])

(def +version+ "0.1.3")
(bootlaces! +version+)

(task-options!
 pom {:project     'codebeige/boot-reset
      :version     +version+
      :description "Boot task for resetting component lifecycle in development."
      :url         "https://github.com/codebeige/boot-reset"
      :scm         {:url "https://github.com/codebeige/boot-reset.git"}
      :license     {"MIT License"
                    "https://opensource.org/licenses/MIT"}})

(deftask dev
  "Boot interactive development environment."
  []
  (comp
   (watch)
   (repl :server true)
   (build-jar)))
