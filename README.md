# boot-reset

```clojure
[codebeige/boot-reset "0.1.0-SNAPSHOT"] ;; latest release
```

[Boot][1] task to start [Component][2] lifecycle on first run and subsequently
do a reset on file changes.

* Provides the `reset` task
* Performs arbitrary side effects before and after wrapped handlers


## Usage

Add dependency to `build.boot` and `require` the task:

```clj
(set-env! :dependencies '[[codebeige/boot-reset "X.Y.Z" :scope "test"]])

(require '[codebeige.boot-reset :refer [reset]])
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
