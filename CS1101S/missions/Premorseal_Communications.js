// Task 1
		  
function noise_sound(duration) {
    const wave = t => math_random() * 2 - 1;
    return make_sound(wave, duration);
}

function cut_sound(sound, duration) {
    /* your answer here */
    return make_sound(get_wave(sound), duration);
}

// testing
play(cut_sound(noise_sound(2), 1));

// Task 2

function sine_sound(freq, duration) {
    /* your answer here */
    const sinewave = t => math_sin(2 * math_PI * freq * t);
    return make_sound(sinewave, duration);
}

play(sine_sound(500, 1));

// Task 3

// copy your own sine_sound function from the previous question here 
function sine_sound(freq, duration) {
    const sinewave = t => math_sin(2 * math_PI * freq * t);
    return make_sound(sinewave, duration);
}

function consecutively(list_of_sounds) {
    /* your answer here */
    return helper(list_of_sounds, t => 0, 0);
}

function helper(list_of_sounds, wave, duration){
    function wave_manipulator(original, add){
        return t => t <= duration
               ? original(t)
               : add(t - duration);
    }
    
    if(is_null(list_of_sounds)){
        return make_sound(wave, duration);
    }
    else{
        const cur_sound = head(list_of_sounds);
        const cur_wave = get_wave(cur_sound);
        const cur_duration = get_duration(cur_sound);
        
        return helper(tail(list_of_sounds),
                      wave_manipulator(wave, cur_wave),
                      duration + cur_duration);
    }
}

const my_sine_1 = sine_sound(500, 1);
const my_sine_2 = sine_sound(700, 1);

// Play test sound
play(consecutively(list(my_sine_1, my_sine_2)));

// Task 4

// copy your own sine_sound and consecutively functions
// from the previous questions here 
function sine_sound(freq, duration) {
    const sinewave = t => math_sin(2 * math_PI * freq * t);
    return make_sound(sinewave, duration);
}

function consecutively(list_of_sounds) {
    return helper(list_of_sounds, t => 0, 0);
}

function helper(list_of_sounds, wave, duration){
    function wave_manipulator(original, add){
        return t => t <= duration
               ? original(t)
               : add(t - duration);
    }
    
    if(is_null(list_of_sounds)){
        return make_sound(wave, duration);
    }
    else{
        const cur_sound = head(list_of_sounds);
        const cur_wave = get_wave(cur_sound);
        const cur_duration = get_duration(cur_sound);
        return helper(tail(list_of_sounds),
                      wave_manipulator(wave, cur_wave),
                      duration + cur_duration);
    }
}

// Create dot, dash and pause sounds first
const dot_sound = sine_sound(800, 0.125);
const dash_sound = sine_sound(800, 0.375);
const dot_pause = make_sound(t => 0, 0.125);
const dash_pause = make_sound(t => 0, 0.375);

// Create sounds for each letter
const S_sound = consecutively(
                list(dot_sound, dot_pause, 
                     dot_sound, dot_pause,
                     dot_sound));
const O_sound = consecutively(
                list(dash_sound, dot_pause,
                     dash_sound, dot_pause,
                     dash_sound));

// Build the signal out of letter sounds and pauses
const distress_signal = consecutively(list(S_sound, dash_pause,
                                           O_sound, dash_pause,
                                           S_sound));

// Play distress signal
play(distress_signal);