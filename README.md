![Cats Friendly Badge](https://typelevel.org/cats/img/cats-badge-tiny.png)

Turing State Machine
=

Pure Deterministic Turing State Machine, running in Terminal, done with Scala using Cats (having fun with some cool types)

### Cool features around functional programming
 - `IO` Monad (Cats), to give a homogeneous structure (safe, easy to compose)
 - `Validated` Applicative Functor (Cats), to parse the Machine description (JSON file)
 - `Cursor` Comonad ([handmade](src/main/scala/com/rbobillo/turing/tape), for fun)
   - built around `Zipper`: handmade wrapper to deal with `Stream` or `LazyList` (simulating the Machine's `head`)
   - handles (updates, displays) the Machine's `tape`

### What does it do ?
 - parses a JSON description of the Machine (alphabet, states, transitions...)
 - parses an input, first content of an infinite `tape` that will be re-written
 - updates (re-writes) the `tape`, according to the machine description
 - displays every update on stdout

### Available Machines
 - `unary_add`:  a TM made to add 2 unary strings
 - `unary_sub`:  a TM made to subtract 2 unary strings
 - `palindrome`: a TM made to check if a binary string is a palindrome
 - `02n`:        a TM made to check if a unary string is a word of the language 0^2n

### How to run
 ```bash
 sbt "run </path/to/description.json> <input>"
 ```
or
 ```bash
 ./compile
 ./turing </path/to/description.json> <input> [-p]
 ```
![example](https://camo.githubusercontent.com/4bb5ba4cd8abea9c94f0cca5f54fa008153818fcfdcd55972490f6c28a700822/68747470733a2f2f692e6962622e636f2f564a6e367351682f436170747572652d642d652d6372616e2d323031392d30382d31332d612d31392d30312d34352e706e67)

### TODO
 - add tests !
 - using State Monad ?
 - adding some safety (if needed)
 - refactoring
