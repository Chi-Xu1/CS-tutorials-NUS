// Q1
// Part 1
// your answer here
// unit_line_at:Number -> (Number -> Point)

// Part 2
function vertical_line(pt, length) {
    // your answer here
      return t => make_point(x_of(pt), y_of(pt) + t * length);
  }
  
  // Part 3
  // your answer here
  //(Point, Number) -> Curve
  
  // Part 4
  // your answer here
  draw_connected(200)(vertical_line(make_point(0.5, 0.25), 0.5));

// Q2
function three_quarters(pt) {
    // return a curve
    return t => make_point(x_of(pt) + math_cos(2 * math_PI * 0.75 * t),
                             y_of(pt) + math_sin(2 * math_PI * 0.75 * t));
}

// Test
draw_connected_squeezed_to_window(200)(three_quarters(make_point(0.5, 0.25)));

// Q3
function s_generator(pt) {
    // return function
    return t => t <= 0.5
                ? make_point(x_of(pt) + math_cos(2 * math_PI * 1.5 * t),
                             y_of(pt) + math_sin(2 * math_PI * 1.5 * t))
                : make_point(x_of(pt) + math_cos(2 * math_PI * -1.5 * t),
                             y_of(pt) - 2 + math_sin(2 * math_PI * -1.5 * t));
}

// Test
(draw_connected_squeezed_to_window(200))(s_generator(make_point(0.5, 0.25)));