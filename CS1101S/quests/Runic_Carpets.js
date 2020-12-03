function persian(rune, count) {
    // your answer here
    const down = quarter_turn_left(stackn(count - 2,
        quarter_turn_right(rune)));
    const up = quarter_turn_left(stackn(count - 2,
        quarter_turn_right(rune)));
    const broadside = stackn(count, rune);
    const center = make_cross(rune);
    const middle = stack_frac(1 / count,
        up,
        stack_frac((count - 2) / (count - 1),
            center,
            down));
    return beside_frac(1 / count,
        broadside,
        beside_frac((count - 2) / (count - 1),
            middle,
            broadside));
}

// Test
show(persian(nova, 9));