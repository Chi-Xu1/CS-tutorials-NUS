// Q1
const test_curve =
    t => make_point(t, 0.5 + (math_sin(4 * (math_PI * t)) / 2));

function stack(c1, c2) {
    // your program here
    return t => t <= 0.5
        ? make_point(x_of(c1(2 * t)), 0.5 + 0.5 * y_of(c1(2 * t)))
        : make_point(x_of(c2(2 * t - 1)), 0.5 * y_of(c2(2 * t - 1)));
}

// Test
draw_points_on(10000)(stack(test_curve, test_curve));

// Q2
const test_curve =
    t => make_point(t, 0.5 + (math_sin(4 * (math_PI * t)) / 2));

function stack_frac(frac, c1, c2) {
    // your program here
    return t => t <= 0.5
        ? make_point(x_of(c1(2 * t)), 1 - frac + frac * y_of(c1(2 * t)))
        : make_point(x_of(c2(2 * t - 1)),
            (1 - frac) * y_of(c2(2 * t - 1)));
}

// Test
draw_points_on(10000)
    (stack_frac(1 / 5,
        test_curve,
        stack_frac(1 / 2, test_curve, test_curve)));