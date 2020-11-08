// Q1
let count_frames = -1;

function count_frames_created(program_string) {
    // Your solution here
    // another change in line 365
    count_frames = -1;
    parse_and_evaluate(program_string);
    return count_frames;
}

function extend_environment(symbols, vals, base_env) {
    // check if symbols is an empty list, which means an empty frame
    if(length(symbols) !== 0) {
        count_frames = count_frames + 1;
    }else {}
    return length(symbols) === length(vals)
           ? pair(make_frame(symbols, vals), base_env)
           : length(symbols) < length(vals)
             ? error("Too many arguments supplied: " + 
                     stringify(symbols) + ", " + 
                     stringify(vals))
             : error("Too few arguments supplied: " + 
                     stringify(symbols) + ", " + 
                     stringify(vals));
}

// Q2
let count_function_object = 0;

function count_function_objects_created(program_string) {
    // Your solution here
    // another change in line 317
    count_function_object = 0;
    parse_and_evaluate(program_string);
    return count_function_object;
}

function make_function(parameters, body, env) {
    // create a function objection, count++
    count_function_object = count_function_object + 1;
    return list("compound_function",
                parameters, body, env);
}

// Q3
const primitive_functions = list(
    list("head",    head             ),
    list("tail",    tail             ),
    list("pair",    pair             ),
    list("list",    list             ),
    list("is_null", is_null          ),
    list("display", display          ),
    list("error",   error            ),
    list("math_abs",math_abs         ),
    list("+",       (x, y) => overload_plus(x, y)  ), // overload + operator
    list("-",       (x, y) => x - y  ),
    list("-unary",   x     =>   - x  ),
    list("*",       (x, y) => x * y  ),
    list("/",       (x, y) => x / y  ),
    list("%",       (x, y) => x % y  ),
    list("===",     (x, y) => x === y),
    list("!==",     (x, y) => x !== y),
    list("<",       (x, y) => x <   y),
    list("<=",      (x, y) => x <=  y),
    list(">",       (x, y) => x >   y),
    list(">=",      (x, y) => x >=  y),
    list("!",        x     =>   !   x)
    );

function overload_plus(x, y) {
    // the + operator overload function 
    if(is_list(x) && is_list(y)) {
        return append(x, y);
    }
    else {
        return x + y;
    }
}

// Q4
const primitive_functions = list(
    list("head",    head             ),
    list("tail",    tail             ),
    list("pair",    pair             ),
    list("list",    list             ),
    list("is_null", is_null          ),
    list("display", display          ),
    list("error",   error            ),
    list("math_abs",math_abs         ),
    list("+",       (x, y) => x + y  ),
    list("-",       (x, y) => x - y  ),
    list("-unary",   x     =>   - x  ),
    list("*",       (x, y) => x * y  ),
    list("/",       (x, y) => x / y  ),
    list("%",       (x, y) => x % y  ),
    list("===",     (x, y) => x === y),
    list("!==",     (x, y) => x !== y),
    list("<",       (x, y) => x <   y),
    list("<=",      (x, y) => x <=  y),
    list(">",       (x, y) => overload_greater(x, y)),
    list(">=",      (x, y) => x >=  y),
    list("!",        x     =>   !   x)
    );

function overload_greater(x, y) {
    if(is_compound_function(x) || is_primitive_function(x) && is_list(y)) {
        return map(curr => apply(x, list(curr)), y);
    }
    else {
        return x > y;
    }
}