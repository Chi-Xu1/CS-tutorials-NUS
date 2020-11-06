// Task 1

// Function type: Number -> pair_of_numbers
// where input is between 0 - 15 inclusive
function get_dtmf_frequencies(number) {
    // your solution goes here
    const freq_list = list(pair(941, 1336), pair(697, 1209),
                           pair(697, 1336), pair(697, 1477),
                           pair(770, 1209), pair(770, 1336),
                           pair(770, 1477), pair(852, 1209),
                           pair(852, 1336), pair(852, 1477),
                           pair(941, 1209), pair(941, 1477),
                           pair(697, 1633), pair(770, 1633),
                           pair(852, 1633), pair(941, 1633));
    return list_ref(freq_list, number);
}
get_dtmf_frequencies(12);

// Task 2

// copy your function get_dtmf_frequencies here
function get_dtmf_frequencies(number) {
    // your solution goes here
    const freq_list = list(pair(941, 1336), pair(697, 1209),
                           pair(697, 1336), pair(697, 1477),
                           pair(770, 1209), pair(770, 1336),
                           pair(770, 1477), pair(852, 1209),
                           pair(852, 1336), pair(852, 1477),
                           pair(941, 1209), pair(941, 1477),
                           pair(697, 1633), pair(770, 1633),
                           pair(852, 1633), pair(941, 1633));
    return list_ref(freq_list, number);
}
		  
function make_dtmf_tone(frequency_pair) {
    // your solution goes here
    const sound_list = list(sine_sound(head(frequency_pair), 0.5),
                            sine_sound(tail(frequency_pair), 0.5));
    return simultaneously(sound_list);
}

play(make_dtmf_tone(get_dtmf_frequencies(7)));

// Task 3

// copy your functions get_dtmf_frequencies
// and make_dtmf_tone here
function get_dtmf_frequencies(number) {
    // your solution goes here
    const freq_list = list(pair(941, 1336), pair(697, 1209),
                           pair(697, 1336), pair(697, 1477),
                           pair(770, 1209), pair(770, 1336),
                           pair(770, 1477), pair(852, 1209),
                           pair(852, 1336), pair(852, 1477),
                           pair(941, 1209), pair(941, 1477),
                           pair(697, 1633), pair(770, 1633),
                           pair(852, 1633), pair(941, 1633));
    return list_ref(freq_list, number);
}
		  
function make_dtmf_tone(frequency_pair) {
    // your solution goes here
    const sound_list = list(sine_sound(head(frequency_pair), 0.2),
                            sine_sound(tail(frequency_pair), 0.2));
    return simultaneously(sound_list);
}
		  
function dial(list_of_digits) {
    // your solution goes here
    const helper = x => list(make_dtmf_tone(
                                get_dtmf_frequencies(x)),
                             silence_sound(0.1));
                    
    return consecutively(accumulate(
                            (curr, rest) => 
                                append(helper(curr), rest), 
                            null, list_of_digits));
}

// Test
play(dial(list(6,2,3,5,8,5,7,7)));

// Task 4

// copy your functions get_dtmf_frequencies,
// make_dtmf_tone and dial here
function get_dtmf_frequencies(number) {
    const freq_list = list(pair(941, 1336), pair(697, 1209),
                           pair(697, 1336), pair(697, 1477),
                           pair(770, 1209), pair(770, 1336),
                           pair(770, 1477), pair(852, 1209),
                           pair(852, 1336), pair(852, 1477),
                           pair(941, 1209), pair(941, 1477),
                           pair(697, 1633), pair(770, 1633),
                           pair(852, 1633), pair(941, 1633));
    return list_ref(freq_list, number);
}
		  
function make_dtmf_tone(frequency_pair) {
    const sound_list = list(sine_sound(head(frequency_pair), 0.2),
                            sine_sound(tail(frequency_pair), 0.2));
    return simultaneously(sound_list);
}
		  
function dial(list_of_digits) {
    const helper = x => list(make_dtmf_tone(
                                get_dtmf_frequencies(x)),
                             silence_sound(0.1));
                    
    return consecutively(accumulate(
                            (curr, rest) => 
                                append(helper(curr), rest), 
                            null, list_of_digits));
}

function dial_all(list_of_numbers) {
    // your solution goes here
    const evil = list(1,8,0,0,5,2,1,1,9,8,0);
    function is_evil_number(lst){
        // check the if the list is the evil number
        function helper(lst, counter){
            return counter === 11
                        ? true
                        : list_ref(lst, counter) === list_ref(evil, counter)
                            ? helper(lst, counter + 1)
                            : false;
        }
        return length(lst) !== 11 ? false : helper(lst, 0);
    }
    //filter the evil number
    const filter_evil = filter(lst => !is_evil_number(lst), 
                                list_of_numbers);
    //add a # to the end of phone number
    const add_11 = map(lst => append(lst, list(11)), filter_evil);
    //add them together
    const sound_list = 
        accumulate((curr, rest) => append(curr, rest),
                      null, add_11);
    return dial(sound_list);
}

// Test
play(dial_all(
 list(
     list(1,8,0,0,5,2,1,1,9,8,0),  // not played!!!
     list(6,2,3,5,8,5,7,7),
     list(0,0,8,6,1,3,7,7,0,9,5,0,0,6,1))
 ));