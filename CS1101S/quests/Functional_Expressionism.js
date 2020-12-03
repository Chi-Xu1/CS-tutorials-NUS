// Q1

const increment_repeater =
    repeater => g => x => repeater(g)(g(x)); // complete the function definition

const twice = f => x => f(f(x));
const thrice = increment_repeater(twice);
const fourtimes = increment_repeater(thrice);
const warn = thrice(display);
warn("ALERT");          // should display "ALERT" 3 times
const bigwarn = fourtimes(display);
bigwarn("A L E R T");   // should display "A L E R T" 4 times.
// (the REPL will display a fifth
// "A L E R T" as the value returned
// by bigwarn)

// Q2
const pair = (x, y) => f => f(x, y);

const head = p => p((x, y) => x);  // complete the function definition
const tail = p => p((x, y) => y);  // complete the function definition

head(pair(1, 2)) === 1; // should return true
tail(pair(1, 2)) === 2; // should return true

// Q3
/*

enter your answer here; no explanation required
Ω(n)

*/

// Q4
// Incorrect:
const zero_repeater = f => x => x;
const one_repeater = f => x => f(zero_repeater, () => zero_repeater(f)(x));
const two_repeater = f => x => f(one_repeater, () => one_repeater(f)(x));
const three_repeater = f => x => f(two_repeater, () => two_repeater(f)(x));

const to_int = repeater => repeater((iter_count, x) => x() + 1)(0);

const increment_repeater = repeater => f => x =>
    f(repeater, () => repeater(f)(x));

const add_repeaters = (repeater1, repeater2) => f => x =>
    repeater1(f)(repeater2(f)(x));

to_int(add_repeaters(two_repeater,
    three_repeater));  // should return 5

// Correct:
const add_repeaters =
    (repeater1, repeater2) =>
        repeater1((iter_count, x) => increment_repeater(x()))(repeater2);

// Q5
const zero_repeater = f => x => x;
const one_repeater = f => x => f(zero_repeater, () => zero_repeater(f)(x));
const two_repeater = f => x => f(one_repeater, () => one_repeater(f)(x));
const three_repeater = f => x => f(two_repeater, () => two_repeater(f)(x));

const to_int = repeater => repeater((iter_count, x) => x() + 1)(0);

const decrement_repeater = repeater =>
    repeater((iter_count, x) => iter_count)(repeater);

to_int(decrement_repeater(three_repeater));  // should return 2