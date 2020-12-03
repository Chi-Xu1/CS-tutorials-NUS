// Question 1

// copy generate_list_of_note from Mission "Musical Diversions"
function generate_list_of_note(letter_name, list_of_interval) {
    // from Mission "Musical Diversions"
    const init = letter_name_to_midi_note(letter_name);
    function helper(lst, current_number) {
        return is_null(lst)
            ? pair(current_number, null)
            : pair(current_number,
                helper(tail(lst), current_number + head(lst)));
    }
    return helper(list_of_interval, init);
}

const pentatonic_list_of_interval = list(2, 2, 3, 2, 3);

// repeat_pattern from Lecture L2

function repeat_list(n, operator, lst, result) {
    return n === 0 ? result : repeat_list(n - 1, operator, lst,
        operator(lst, result));
}

function repeated_scale(note, list_of_interval, n, duration, instrument) {
    const repeated_list_of_interval = repeat_list(n, append,
        list_of_interval, null);
    const list_of_notes =
        generate_list_of_note(note, repeated_list_of_interval);
    const list_of_sounds = map(x => sine_sound(midi_note_to_frequency(x),
        duration), list_of_notes);
    return list_of_sounds;
}

play(consecutively(repeated_scale("C4", pentatonic_list_of_interval,
    2, 1, cello)));

// Q2
// Question 2

function play_matrix(distance, list_of_sounds) {
    /* your answer here */
    function combine_row(row, column) {
        if (row > 15) {
            return null;
        }
        else {
            if (list_ref(list_ref(get_matrix(), row), column)) {
                return pair(list_ref(sounds, row),
                    combine_row(row + 1, column));
            }
            else {
                return pair(silence_sound(0),
                    combine_row(row + 1, column));
            }
        }
    }

    function helper(row, column) {
        set_timeout(() => play_concurrently(simultaneously
            (combine_row(row, column % 15))),
            column * distance * 1000);
        helper(0, column + 1);
    }
    helper(0, 0);
}

function stop_matrix() {
    /* your answer here */
    return clear_all_timeout();
}

// copy your solution of Question 1 here
function generate_list_of_note(letter_name, list_of_interval) {
    const init = letter_name_to_midi_note(letter_name);
    function helper(lst, current_number) {
        return is_null(lst)
            ? pair(current_number, null)
            : pair(current_number,
                helper(tail(lst), current_number + head(lst)));
    }
    return helper(list_of_interval, init);
}

const pentatonic_list_of_interval = list(2, 2, 3, 2, 3);

function repeat_list(n, operator, lst, result) {
    return n === 0 ? result : repeat_list(n - 1, operator, lst,
        operator(lst, result));
}

function repeated_scale(note, list_of_interval, n, duration, instrument) {
    const repeated_list_of_interval = repeat_list(n, append,
        list_of_interval, null);
    const list_of_notes =
        generate_list_of_note(note, repeated_list_of_interval);
    const list_of_sounds = map(x => sine_sound(midi_note_to_frequency(x),
        duration), list_of_notes);
    return list_of_sounds;
}

const sounds = repeated_scale("C4", pentatonic_list_of_interval, 3, 0.2, cello);

play_matrix(0.3, sounds);
