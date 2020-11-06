// Q1
const drum_envelope = adsr(0.05, 0.95, 0, 0);

function snare_drum(note, duration) {
    /* your answer here */
    return drum_envelope(noise_sound(duration));
}

function bass_drum(note, duration) {
    /* your answer here */
    return drum_envelope(sine_sound(midi_note_to_frequency(note), duration));
}

function mute(note, duration) {
    /* your answer here */
    return drum_envelope(silence_sound(duration));
}


//play(snare_drum(50, 0.2));
//play(bass_drum(50, 0.2));
 

 play(consecutively(list(snare_drum(50, 0.2), mute(0, 0.2), bass_drum(53, 0.2),
                         mute(0, 0.2), 
                         snare_drum(50, 0.2), mute(0, 0.2), bass_drum(53, 0.2))));

// Q2
function generate_list_of_note(letter_name, list_of_interval) {
    /* your answer here */
    const init = letter_name_to_midi_note(letter_name);
    function helper(lst, current_number){
        return is_null(lst)
            ? pair(current_number, null)
            : pair(current_number, 
                helper(tail(lst), current_number + head(lst)));
    }
    return helper(list_of_interval, init);
}

const major_scale_interval = list(2, 2, 1, 2, 2, 2, 1, -1, -2, -2, -2, -1, -2, -2);
const c_major_scale = generate_list_of_note("C4", major_scale_interval);

// display(c_major_scale);

function list_to_sound(list_of_midi_note, duration, instrument) {
    /* your answer here */
    return consecutively(
        map(xs => instrument(xs, duration), list_of_midi_note));
}

const c_major_scale_sound = list_to_sound(c_major_scale, 0.4, cello);
// play(c_major_scale_sound);

const harmonic_minor_scale_interval = list(2, 1, 2, 2, 1, 3, 1, -1, -3, -1, -2, -2, -1, -2);

const melodic_minor_scale_interval = list(2, 1, 2, 2, 2, 2, 1, -2, -2, -1, -2, -2, -1, -2);


const c_harmonic_minor_scale = generate_list_of_note("C4", 
                                harmonic_minor_scale_interval);
const c_harmonic_minor_scale_sound = list_to_sound(c_harmonic_minor_scale, 0.4, cello);
play(c_harmonic_minor_scale_sound);

const c_melodic_minor_scale = generate_list_of_note("C4",
                                melodic_minor_scale_interval);
// const c_melodic_minor_scale_sound = list_to_sound(c_melodic_minor_scale, 0.4, cello);
// play(c_melodic_minor_scale_sound);

// Q3
// copy your functions generate_list_of_note and list_to_sound
// from the previous Question here
function generate_list_of_note(letter_name, list_of_interval) {
    const init = letter_name_to_midi_note(letter_name);
    function helper(lst, current_number){
        return is_null(lst)
            ? pair(current_number, null)
            : pair(current_number, 
                helper(tail(lst), current_number + head(lst)));
    }
    return helper(list_of_interval, init);
}

function list_to_sound(list_of_midi_note, duration, instrument) {
    return consecutively(
        map(xs => instrument(xs, duration), list_of_midi_note));
}

const major_arpeggio_interval = list(4, 3, 5, 4, 3, 5);
const minor_arpeggio_interval = list(3, 4, 5, 3, 4, 5);

function generate_arpeggio(letter_name, list_of_interval) {
    return generate_list_of_note(letter_name, list_of_interval);
}

function arpeggiator_up(arpeggio, duration_each, instrument) {
    /* your answer here */
    // list of numbers -> list of sounds
    const sound_list = map(xs => instrument(xs, duration_each), arpeggio);
    
    //list of sounds -> list of sounds' lists
    function generate_up(lst){
        return helper(lst, 0);
    }
    function helper(lst, counter){
        return counter > 3
            ? null
            : is_null(lst)
                ? pair(silence_sound(0), null)
                : pair(head(lst), helper(tail(lst), counter + 1));
    }
    const my_map = (f, xs) => is_null(xs)
        ? null : pair(f(xs), my_map(f, tail(xs)));
        
    // list of sounds' lists -> sound
    return consecutively(map(consecutively, 
                            my_map(x => generate_up(x), sound_list)));
}

// test
play(arpeggiator_up(generate_arpeggio("C4", major_arpeggio_interval), 0.1, cello));

// Q4
function simplify_rhythm(rhythm) {
    /* your answer here */
    if(is_null(rhythm)) {
        return null;
    }
    else {
        if(is_list(rhythm)){
            return append(simplify_rhythm(head(rhythm)), 
                            simplify_rhythm(tail(rhythm)));
        }
        else {
            if(is_pair(rhythm)){
                return repeat(simplify_rhythm(head(rhythm)), tail(rhythm));
            }
            else{
                return list(rhythm);
            }
        }
    }
}

/* 
list -> list
repeat a list n times
*/
function repeat(lst, n){
    function helper(lst, counter, n){
        return !(counter < n)
                ? lst
                : append(lst, helper(lst, counter + 1, n));
    }
    return helper(lst, 1, n);
}

// test

const my_rhythm = pair(list(pair(list(1,2,0,1), 2), list(1,3,0,1,3,1,0,3)), 3);
display_list(simplify_rhythm(my_rhythm));

// Q5
// paste your snare_drum, mute and simplify_rhythm
// from Questions 3 and 4 here
const drum_envelope = adsr(0.05, 0.95, 0, 0);

function snare_drum(note, duration) {
    return drum_envelope(noise_sound(duration));
}

function mute(note, duration) {
    return drum_envelope(silence_sound(duration));
}

function simplify_rhythm(rhythm) {
    if(is_null(rhythm)) {
        return null;
    }
    else {
        if(is_list(rhythm)){
            return append(simplify_rhythm(head(rhythm)), 
                            simplify_rhythm(tail(rhythm)));
        }
        else {
            if(is_pair(rhythm)){
                return repeat(simplify_rhythm(head(rhythm)), tail(rhythm));
            }
            else{
                return list(rhythm);
            }
        }
    }
}

function repeat(lst, n){
    function helper(lst, counter, n){
        return !(counter < n)
                ? lst
                : append(lst, helper(lst, counter + 1, n));
    }
    return helper(lst, 1, n);
}

function percussions(distance, list_of_sounds, rhythm) {
    /* your answer here */
    const r_list = simplify_rhythm(rhythm);
    const r_length = length(r_list);
    function p_helper(lst, counter) {
        return counter >= r_length
               ? null
               : pair(consecutively(
                            list(silence_sound(counter * distance),
                                list_ref(list_of_sounds, 
                                    list_ref(lst, counter)))),
                        p_helper(lst, counter + 1));
    }
    return simultaneously(p_helper(r_list, 0));
}

// test
const my_mute_sound = mute(50, 0.7);
const my_snare_drum = snare_drum(50, 0.7);
const my_cello = cello(50, 0.7);
const my_bell = bell(72, 1);
play(percussions(0.5,
         list(my_mute_sound,
              my_snare_drum, 
              my_cello, 
              my_bell), 
         list(1,2,1,0,3,1,0)));