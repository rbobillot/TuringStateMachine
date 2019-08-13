![Cats Friendly Badge](https://typelevel.org/cats/img/cats-badge-tiny.png)

Turing State Machine
=

Pure Turing State Machine running in Terminal, done with Scala using Cats (having fun with the IO Monad, Validated Applicative)

### What does it do ?
 - parses a JSON description of the Machine (alphabet, states, transitions...)
 - parses an input, first content of an infinite tape that will be re-written
 - updates the input, according to the machine description
 
### How to run
 ```bash
 sbt "run </path/to/description.json> <input>"
 ```
