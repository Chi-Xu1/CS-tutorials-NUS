/*

Describe your solution here, including
its order of growth.

My solution is quite similar to merge sort
firstly, divide the list into two parts: left and right.
Then, count the number of un-order pairs of each part, 
order each part by recursion, and merge them together to form a new list.
And then, counter the un-order pair in the new list.
Finally, add the number of un-ordered pair together.
The order of growth is Θ(nlogn)

You will get full marks marks only for
a solution that has an order of growth
O(n²) and that does not have an order
of growth Θ(n²).

*/

// half, rounded downwards
function middle(n) {
    return n % 2 === 0
        ? n / 2
        : (n - 1) / 2;
}
// put the first n elements of xs into a list
function take(xs, n) {
    return n <= 0
        ? null
        : pair(head(xs), take(tail(xs), n - 1));
}
// drop the first n elements from the list and return the rest
function drop(xs, n) {
    return n <= 0
        ? xs
        : drop(tail(xs), n - 1);
}
// merge sort
function merge_sort(xs) {
    function merge(xs, ys) {
        if (is_null(xs)) {
            return ys;
        }
        else {
            if (is_null(ys)) {
                return xs;
            }
            else {
                const smaller = head(xs);
                const larger = head(ys);
                return (smaller <= larger)
                    ? pair(smaller, merge(tail(xs), ys))
                    : pair(larger, merge(xs, tail(ys)));
            }
        }
    }

    if (is_null(xs) || is_null(tail(xs))) {
        return xs;
    }
    else {
        const mid = middle(length(xs));
        const left = merge_sort(take(xs, mid));
        const right = merge_sort(drop(xs, mid));
        return merge(left, right);
    }
}

function graderVer1(arr) {
    // your solution here
    // count the number of un-order pairs in two sorted list
    function counting(xs, ys, lx, i) {
        if (is_null(xs) || is_null(ys)) {
            return 0;
        }
        else {
            if (head(xs) <= head(ys)) {
                return counting(tail(xs), ys, lx, i + 1);
            }
            else {
                return lx - i + counting(xs, tail(ys), lx, i);
            }
        }
    }
    if (is_null(arr) || is_null(tail(arr))) {
        return 0;
    }
    else {
        const mid = middle(length(arr));
        const left = take(arr, mid);
        const right = drop(arr, mid);
        return graderVer1(left)
            + graderVer1(right)
            + counting(merge_sort(left), merge_sort(right),
                length(left), 0);
    }
}

/* Describe your solution here
My solution is derived from the previous task.
Firstly, find all the un-order pairs from the tail of the list, and record them
in a list.
Then, compare the head of the original list with every elements in the un-order 
pairs list, if the head of the original list is larger than the head element
of the un-order pair, it can form an inverted triple.
So traverse the original list by using this method, we can get the number of the
inverted triples.
The order of growth will be Θ((n^2)*logn)
*/

// half, rounded downwards
function middle(n) {
    return n % 2 === 0
        ? n / 2
        : (n - 1) / 2;
}
// put the first n elements of xs into a list
function take(xs, n) {
    return n <= 0
        ? null
        : pair(head(xs), take(tail(xs), n - 1));
}
// drop the first n elements from the list and return the rest
function drop(xs, n) {
    return n <= 0
        ? xs
        : drop(tail(xs), n - 1);
}
// merge sort
function merge_sort(xs) {
    function merge(xs, ys) {
        if (is_null(xs)) {
            return ys;
        }
        else {
            if (is_null(ys)) {
                return xs;
            }
            else {
                const smaller = head(xs);
                const larger = head(ys);
                return (smaller <= larger)
                    ? pair(smaller, merge(tail(xs), ys))
                    : pair(larger, merge(xs, tail(ys)));
            }
        }
    }

    if (is_null(xs) || is_null(tail(xs))) {
        return xs;
    }
    else {
        const mid = middle(length(xs));
        const left = merge_sort(take(xs, mid));
        const right = merge_sort(drop(xs, mid));
        return merge(left, right);
    }
}

function graderVer2_helper(arr) {
    // similar to graderVer1
    // record the un-order pairs in two sorted list, and return a list of pairs
    function counting(xs, ys) {
        if (is_null(xs) || is_null(ys)) {
            return null;
        }
        else {
            if (head(xs) <= head(ys)) {
                return counting(tail(xs), ys);
            }
            else {
                // pair each rest of the elements with head(ys)
                return append(map(x => pair(x, head(ys)), xs),
                    counting(xs, tail(ys)));
            }
        }
    }
    if (is_null(arr)) {
        return null;
    }
    else {
        if (is_null(tail(arr))) {
            return null;
        }
        else {
            const mid = middle(length(arr));
            const left = take(arr, mid);
            const right = drop(arr, mid);
            return append(graderVer2_helper(left),
                append(graderVer2_helper(right),
                    counting(merge_sort(left), merge_sort(right))));
        }
    }
}
function graderVer2(arr) {
    // your solution here
    /* 
       find the un-order pairs from the tail of the list, and then check whether 
       the head of the list is larger than the head of the pair, if true, it can
       form an inverted triple.
    */
    if (is_null(arr)) {
        return 0;
    }
    else {
        const curr = head(arr);
        const rest = graderVer2_helper(tail(arr));
        function f(x) {
            return is_pair(x)
                ? curr > head(x)
                : false;
        }
        return length(filter(f, rest))
            + graderVer2(tail(arr));
    }
}

// test your program!
graderVer2(list(5, 4, 3, 2, 1)); // should return 2