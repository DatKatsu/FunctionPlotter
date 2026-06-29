# FunctionPlotter

A desktop app for plotting mathematical functions and save them as images, built with Java and the Java Swing library. A refactor of my old Function Plotter that I made during my time as a Software Engineering student.

The parser and evaluator live in a separate library [ExpressionParser](https://github.com/DatKatsu/ExpressionParser). The plotter just passes an expression string and an x value, gets a double back, and draws it — no parsing logic lives here.

## Screenshot

*(screenshot here)*

## Features

- Plot multiple functions simultaneously
- Built-in constants: `pi`, `e`, `tau`, `phi`
- Built-in functions: `sin`, `cos`, `tan`, `sqrt`, `log`, `abs` and more
- Handles discontinuities like `1/x` and `tan(x)` gracefully
- Save the functions as png

## How to run

This project depends on my other project: [ExpressionParser](https://github.com/DatKatsu/ExpressionParser). Install it locally first:

```bash
# In the ExpressionParser directory
mvn install

# Then in this directory
mvn package
```

Then run the generated jar in the `target/` folder.

