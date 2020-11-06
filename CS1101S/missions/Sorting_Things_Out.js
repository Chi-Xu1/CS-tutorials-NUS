// Task 1
function partition(xs, p) {
    // Your answer here
    function helper(smaller, larger, xs){
        return is_null(xs) 
               ? pair(smaller, larger)
               : head(xs) <= p
                    ? helper(pair(head(xs), smaller), larger, tail(xs))
                    : helper(smaller, pair(head(xs), larger), tail(xs));
    }
    return helper(null, null, xs);
}

// Test
const my_list = list(1, 2, 3, 4, 5, 6);
partition(my_list, 4);

// Task 2
function partition(xs, p) {
    function helper(smaller, larger, xs){
        return is_null(xs) 
               ? pair(smaller, larger)
               : head(xs) <= p
                    ? helper(pair(head(xs), smaller), larger, tail(xs))
                    : helper(smaller, pair(head(xs), larger), tail(xs));
    }
    return helper(null, null, xs);
}

function quicksort(xs) {
    // Your answer here
    if(is_null(xs) || is_null(tail(xs))) {
        return xs;
    }
    else {
        const pivot = head(xs);
        const smaller = quicksort(head((partition(tail(xs), pivot))));
        const larger = quicksort(tail(partition(tail(xs), pivot)));
        return append(smaller, append(list(pivot), larger));
    }
}

// Test
const my_list = list(23, 12, 56, 92, -2, 0);
quicksort(my_list);

