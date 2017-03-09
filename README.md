# boot-reset

[](dependency)
```clojure
[codebeige/boot-reset "0.1.2"] ;; latest release
```
[](/dependency)

[Boot][1] task to start [Component][2] lifecycle on first run and subsequently
do a reset on file changes.

* Provides the `reset` task
* Performs arbitrary side effects before and after wrapped handlers
* Triggers on file changes optionally filtered by pattern


## Usage

Add dependency to `build.boot` and `require` the task:

```clj
(set-env! :dependencies '[[codebeige/boot-reset "RELEASE" :scope "test"]])

(require '[codebeige.boot-reset :refer [reset]])
```

Provide symbols referring to the functions that should be run as options:

```clj
(task-options!
 reset {:start 'dev/start
        :stop  'dev/stop
        :files #{#"\.cljc?$"}})
```

Add the task to your development pipeline after `watch` and before any
*tasks that should be wrapped* [(e.g. `refresh`)][3]:

```clj
(deftask dev []
  (comp
   (watch)
   (reset)
   (refresh)))
```


## Development

Use the provided `dev` task:

```sh
boot dev
```

This will start up an interactive development environment that will reinstall
the `boot-reset` jar locally on every file change.


## License

Copyright (c) 2017 Tibor Claassen. Distributed under the MIT License.

[1]: https://github.com/boot-clj/boot
[2]: https://github.com/stuartsierra/component
[3]: https://github.com/samestep/boot-refresh
