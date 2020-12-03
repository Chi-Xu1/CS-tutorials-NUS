// Q1
function backward(sound) {
    // your solution goes here
    const wave = get_wave(sound);
    const duration = get_duration(sound);
    return make_sound(t => wave(duration - t), duration);
}

init_record();                       // step 1

const my_voice = record_for(2, 0.2); // step 2

play(backward(my_voice()));          // step 3 in REPL

// Q2
function repeat(n, sound) {
    // your solution goes here
    const wave = get_wave(sound);
    const duration = get_duration(sound);
    function repeat_helper(counter, result) {
        return counter > n
            ? result
            : repeat_helper(counter + 1,
                t => t <= counter * duration
                    ? result(t)
                    : wave(t - counter * duration));
    }
    return make_sound(repeat_helper(1, wave), n * duration);
}

const my_sound = consecutively(
    list(sine_sound(400, 1), sine_sound(800, 1)));
const my_repeated = repeat(3, my_sound);
play(my_repeated);

// Q3
function fast_forward(n, sound) {
    // your solution goes here
    const wave = get_wave(sound);
    const duration = get_duration(sound);
    return make_sound(t => wave(n * t), duration / n);
}

init_record();                       // step 1

const my_voice = record_for(2, 0.2); // step 2

// play(fast_forward(2, my_voice()));   // step 3 in REPL

// Q4
function echo(n, d, sound) {
    // your solution goes here
    const wave = get_wave(sound);
    const duration = get_duration(sound);
    function echo_helper(counter, result, prev_d) {
        return counter > n
            ? result
            : echo_helper(
                counter + 1,
                t => t <= prev_d
                    ? result(t)
                    : t <= prev_d + d - duration
                        ? 0
                        : math_pow(0.5, counter) * wave(
                            t - prev_d - d + duration),
                prev_d + d);
    }
    return make_sound(echo_helper(1, wave, duration),
        n * d + duration);
}

const test_sound = sine_sound(800, 0.2);
play(echo(2, 0.3, test_sound));

// Q5
// Copy your functions backward, repeat,
// fast_forward and echo here
function backward(sound) {
    // your solution goes here
    const wave = get_wave(sound);
    const duration = get_duration(sound);
    return make_sound(t => wave(duration - t), duration);
}

function repeat(n, sound) {
    // your solution goes here
    const wave = get_wave(sound);
    const duration = get_duration(sound);
    function repeat_helper(counter, result) {
        return counter > n
            ? result
            : repeat_helper(counter + 1,
                t => t <= counter * duration
                    ? result(t)
                    : wave(t - counter * duration));
    }
    return make_sound(repeat_helper(1, wave), n * duration);
}

function fast_forward(n, sound) {
    // your solution goes here
    const wave = get_wave(sound);
    const duration = get_duration(sound);
    return make_sound(t => wave(n * t), duration / n);
}

function echo(n, d, sound) {
    // your solution goes here
    const wave = get_wave(sound);
    const duration = get_duration(sound);
    function echo_helper(counter, result, prev_d) {
        return counter > n
            ? result
            : echo_helper(
                counter + 1,
                t => t <= prev_d
                    ? result(t)
                    : t <= prev_d + d - duration
                        ? 0
                        : math_pow(0.5, counter) * wave(
                            t - prev_d - d + duration),
                prev_d + d);
    }
    return make_sound(echo_helper(1, wave, duration),
        n * d + duration);
}

function make_alien_jukebox(sound) {
    // your solution goes here
    return list(
        play(sound),
        play(backward(sound)),
        play(fast_forward(0.5, sound)),
        play(repeat(3, fast_forward(2, sound))),
        play(echo(3, 0.3, backward(sound))));
}

// Test in REPL:

init_record();

const erksh_voice = record_for(1, 0.2);

// const j = make_alien_jukebox(erksh_voice());

// j(0);  // plays original recording

// j(1);  // plays it backward

// j(2);  // plays it at half speed

// j(3);  // plays it at double speed, three times in a row

// j(4);  // plays it backward with 4-times echo,
//        //     with 0.3 seconds echo delay