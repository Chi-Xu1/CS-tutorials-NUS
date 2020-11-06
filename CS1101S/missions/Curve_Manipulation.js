// Q1
function reflect_through_y_axis(curve) {
    // your program here
    return t => make_point(-x_of(curve(t)), y_of(curve(t)));
}

function s_generator(pt) {
    // return function
    return t => t <= 0.5
                ? make_point(x_of(pt) + math_cos(2 * math_PI * 1.5 * t),
                             y_of(pt) + math_sin(2 * math_PI * 1.5 * t))
                : make_point(x_of(pt) + math_cos(2 * math_PI * -1.5 * t),
                             y_of(pt) - 2 + math_sin(2 * math_PI * -1.5 * t));
}

//Test
const my_s = s_generator(make_point(0,0));
(draw_connected_squeezed_to_window(200))(reflect_through_y_axis(my_s));

// Q2
// you can define your own helper
// functions here
function reverse(curve){
    //input a curve c, output a curve c'' such that c(t) = c''(1 - t)
    return t => 
        make_point(x_of(curve(1 - t)), y_of(curve(1 - t)));
}

function close(curve) {
    // your program here
    return connect_rigidly(curve, reverse(curve));
}

function reflect_through_y_axis(curve) {
    // your program here
    return t => make_point(-x_of(curve(t)), y_of(curve(t)));
}

function s_generator(pt) {
    // return function
    return t => t <= 0.5
                ? make_point(x_of(pt) + math_cos(2 * math_PI * 1.5 * t),
                             y_of(pt) + math_sin(2 * math_PI * 1.5 * t))
                : make_point(x_of(pt) + math_cos(2 * math_PI * -1.5 * t),
                             y_of(pt) - 2 + math_sin(2 * math_PI * -1.5 * t));
}

const my_s_curve = s_generator(make_point(1, 0));
draw_connected_squeezed_to_window(200) 
(connect_ends(close(my_s_curve), reflect_through_y_axis(my_s_curve)));
