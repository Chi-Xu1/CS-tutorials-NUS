// TASK 1

function d_split_list(xs) {

    // *** Your answer here. ***
    function find_pos(xs, n) {
        // return a pointer that points to the nth pair in the list
        let i = 0;
        let tmp = xs;
        while(i < n) {
            tmp = tail(tmp);
            i = i + 1;
        }
        return tmp;
    }
    const len = length(xs);
    const mid = math_ceil(len / 2);
    const tmp = find_pos(xs, mid - 1);
    const right = tail(tmp); // point to the right part
    set_tail(tmp, null); // cut off the left part
    return pair(xs, right);
    
}

// TEST:
const my_list1 = list(1, 2, 3, 4, 5, 6);
const my_list2 = list(5, 4, 3, 2, 1);
d_split_list(my_list1);
d_split_list(my_list2);

// TASK 2

function d_merge(xs, ys) {

    // *** Your answer here. ***
    if(is_null(xs) || is_null(ys)) {
        if(is_null(xs)) {
            return ys;
        }
        else {
            return xs;
        }
    }
    else {
        if(head(xs) < head(ys)) {
            set_tail(xs, d_merge(tail(xs), ys));
            return xs;
        }
        else {
            set_tail(ys, d_merge(xs, tail(ys)));
            return ys;
        }
    }
}

// TEST:
const my_list1 = list(2, 4, 5, 9);
const my_list2 = list(3, 5, 8);
d_merge(my_list1, my_list2);

// TASK 3

// Copy-and-paste your d_split_list function for Task 1 here.
function d_split_list(xs) {
    function find_pos(xs, n) {
        let i = 0;
        let tmp = xs;
        while(i < n) {
            tmp = tail(tmp);
            i = i + 1;
        }
        return tmp;
    }
    const len = length(xs);
    const mid = math_ceil(len / 2);
    const tmp = find_pos(xs, mid - 1);
    const right = tail(tmp);
    set_tail(tmp, null);
    return pair(xs, right);
}

// Copy-and-paste your d_merge function for Task 2 here.
function d_merge(xs, ys) {
    if(is_null(xs) || is_null(ys)) {
        if(is_null(xs)) {
            return ys;
        }
        else {
            return xs;
        }
    }
    else {
        if(head(xs) < head(ys)) {
            set_tail(xs, d_merge(tail(xs), ys));
            return xs;
        }
        else {
            set_tail(ys, d_merge(xs, tail(ys)));
            return ys;
        }
    }
}


function d_merge_sort(xs) {

    // *** Your answer here. ***
    if(is_null(xs) || is_null(tail(xs))) {
        return xs;
    }
    else {
        let tmp = d_split_list(xs);
        return d_merge(d_merge_sort(head(tmp)), d_merge_sort(tail(tmp)));
    }

}

// TEST:
const my_list = list(7, 2, 4, 6, 9, 1, 5, 8, 3, 6);
d_merge_sort(my_list);