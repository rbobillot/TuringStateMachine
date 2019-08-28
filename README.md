![Cats Friendly Badge](https://typelevel.org/cats/img/cats-badge-tiny.png)

Turing State Machine
=

Pure Deterministic Turing State Machine, running in Terminal, done with Scala using Cats (having fun with some cool types)

### Cool features around functional programming
 - `IO` Monad (Cats), to give a homogeneous structure (safe, easy to compose)
 - `Validated` Applicative Functor (Cats), to parse the Machine description (JSON file)
 - `LazyListZipper` Comonad (handmade, for fun)
   - built around `Cursor`: handmade wrapper to deal with `Stream` or `LazyList` (simulating the Machine's `head`)
   - handles (updates, displays) the Machine's `tape`

### What does it do ?
 - parses a JSON description of the Machine (alphabet, states, transitions...)
 - parses an input, first content of an infinite `tape` that will be re-written
 - updates (re-writes) the `tape`, according to the machine description
 
### How to run
 ```bash
 sbt "run </path/to/description.json> <input>"
 ```
![example](https://i.ibb.co/VJn6sQh/Capture-d-e-cran-2019-08-13-a-19-01-45.png)

### TODO
 - using State Monad ?
 - adding some safety (if needed)
 - refactoring
