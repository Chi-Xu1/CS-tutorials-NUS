// Q1
function fractal(level, transformation, curve){
    // your solution goes here
    return level === 0
           ? curve
           : fractal(level - 1, 
                     transformation, 
                     transformation(curve));
}

function levycize(curve){
    const scaled_curve = (scale(math_sqrt(2) / 2))(curve);
    return connect_rigidly(
        (rotate_around_origin(math_PI / 4))(scaled_curve),
        (translate(0.5, 0.5))
            ((rotate_around_origin(-math_PI / 4))(scaled_curve)));
}


// test
draw_connected_full_view_proportional(10000)
(fractal(11, levycize, unit_line));

// Q2
function fractal(level, transformation, curve){
    // your solution goes here
    return level === 0
           ? curve
           : fractal(level - 1, 
                     transformation, 
                     transformation(curve));
}

function dragonize(curve) {
    // your answer here
    const scaled_curve = (scale(math_sqrt(2) / 2))(curve);
    return invert(connect_ends(
        rotate_around_origin(-0.25 * math_PI)(invert(scaled_curve)),
        rotate_around_origin(-0.75 * math_PI)(scaled_curve)
        ));
}


// Test
draw_connected_full_view_proportional(10000)
(fractal(11, dragonize, unit_line));

// Q3
function kochize(curve){
    const up_60 = rotate_around_origin(math_PI / 3);
    const down_60 = rotate_around_origin(- math_PI / 3);
    return put_in_standard_position(
               connect_ends(curve,
                            connect_ends(up_60(curve),
                                         connect_ends(down_60(curve),
                                                      curve))));
}

// copy your fractal function here
function fractal(level, transformation, curve){
    // your solution goes here
    return level === 0
           ? curve
           : fractal(level - 1, 
                     transformation, 
                     transformation(curve));
}
		  
function snowflake(n) {
    // your solution goes here
    const basic_ele = fractal(n, kochize, unit_line);
    
    return snowflake_iter(basic_ele, basic_ele, 1, n,
                            2 * math_PI / 3);
}

function snowflake_iter(base, curve, count, n, angle){
    if(count === 3){
        return curve;
    }
    else{
        const r_base = rotate_around_origin(-2 * angle * count)(base);
        return snowflake_iter(
                base,
                    connect_ends(r_base, curve),
                        count + 1, n, angle);
    }
}

draw_connected_full_view_proportional(10000)
(snowflake(5));
