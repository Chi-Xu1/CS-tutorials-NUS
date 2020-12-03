// Q1
function besiden(n, rune) {
    // your solution goes here
    return n === 1
        ? rune
        : beside_frac(1 / n, rune,
            besiden(n - 1, rune));
}

// Test
show(besiden(7, heart));

// Q2
// paste your besiden function from the
// previous question here
function besiden(n, rune) {
    // your solution goes here
    return n === 1
        ? rune
        : beside_frac(1 / n, rune,
            besiden(n - 1, rune));
}

function carpet(n, m, rune) {
    // your solution goes here
    return stackn(n, besiden(m, rune));
}

// test case:
show(carpet(7, 5, heart));

// Q3
/*

enter your answers here
(answer each question in one or two complete sentences)

1. All the Runes in the carpet have the same color.
2. No, it isn't.
3, The Runes in the carpet will have different color as Alyssa's desired effect.
4. Because when using applicative order reduction, the random_color function
wiil be evaluated and its result will become the parameter rune in function 
carpet, but using normal order reduction, the argument random_color become the 
parameter and will be evaluate at last.

*/

// Q4
// you may need helper functions

function randomly_colored_carpet(n, m, rune) {
    // your solution goes here
    function besiden_random_color(n, rune) {
        return n === 1
            ? random_color(rune)
            : beside_frac(1 / n, random_color(rune),
                besiden_random_color(n - 1, rune));
    }
    return m === 1
        ? besiden_random_color(n, rune)
        : stack_frac(1 / m, besiden_random_color(n, rune),
            randomly_colored_carpet(n, m - 1, rune));

}

// test case    
show(randomly_colored_carpet(10, 10, heart));
// should produce a carpet as shown in the title picture of this quest.