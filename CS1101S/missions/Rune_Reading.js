// Q1
function fractal(pic, n) {
    // your answer here
    return n === 1
                 ? pic
                 : beside(pic, fractal(stack(pic, pic), n - 1));
}

// Test
show(fractal(make_cross(rcross), 5));

// Q2
function hook(frac) {
    // your answer here
    /* apply stack_frac to square and blank, then turn right.
    at last, put the square over the picture mentioned above.
    */
    return stack(square, quarter_turn_right
                (stack_frac(frac, square, blank))
                );
}

// Test
show(hook(1 / 5));

// Q3
function spiral(thickness, depth) {
    // your answer here
    return depth === 0
                     ? blank
                     : stack_frac(thickness, hook(thickness / 2), 
                        quarter_turn_right(
                            spiral(thickness, depth - 1)
                            )
                     );
}

// copy your hook function from Question 2 here if required
function hook(frac) {
    return stack(square, quarter_turn_right
                (stack_frac(frac, square, blank))
                );
}

// Test
show(spiral(1 / 5, 20));