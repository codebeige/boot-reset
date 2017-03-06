# boot-reset

A Boot task to do many wonderful things.

## Usage

FIXME: explanation

Run the `boot-reset-pre` task:

    $ boot boot-reset-pre

To use this in your project, add `[boot-reset "0.1.0-SNAPSHOT"]` to your `:dependencies`
and then require the task:

    (require '[boot-reset.core :refer [boot-reset-pre]])

Other tasks include: `boot-reset-simple`, `boot-reset-post`, `boot-reset-pass-thru`.

## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
