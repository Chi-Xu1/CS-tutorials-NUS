//S6 Q1
function my_map(f, xs) {
    return accumulate((curr, rest) => pair(f(curr), rest), null, xs);
}

// display_list(my_map(x => x + 1, list(1, 2, 3)));

//S6 Q2
/*
Write a function called remove_duplicates that takes in a list 
as its only argument and returns a list with duplicate elements removed. 
The order of the elements in the returned list does not matter.
*/
function remove_duplicates(lst) {
    function helper(curr, xs) {
        return filter(x => x !== curr, xs);
    }
    return is_null(lst)
        ? null
        : pair(head(lst), remove_duplicates(helper(head(lst), tail(lst))));
}

remove_duplicates(list(1, 2, 3, 4, 4, 3, 2, 1, 2));
//Result: list(1, 2, 3, 4)
remove_duplicates(list("a", "x", "b", "c", "c", "b", "d"));
//Result: list("a", "x", "b", "c", "d")

//S6 Q3
/*
a function which takes as parameters the
amount x and a list of all the coins Louis has in his pocket, 
and returns a list of lists, such that each sub-list of the result 
contains a valid combination to make up x. A combination
may appear more than once, since it may be using different coins of 
the same denomination.
*/
function makeup_amount(x, coins) {
    if (x === 0) {
        return list(null);
    } else if (x < 0 || is_null(coins)) {
        return null;
    } else {
        // Combinations that do not use the head coin.
        const combi_A = makeup_amount(x, tail(coins));
        // Combinations that do not use the head coin
        // for the remaining amount.
        const combi_B = makeup_amount(x - head(coins), tail(coins));
        // Combinations that use the head coin.
        const combi_C = is_null(combi_B)
            ? null
            : map(x => pair(head(coins), x), combi_B);
        return append(combi_A, combi_C);
    }
}

display_list(makeup_amount(22, list(1, 10, 5, 20, 1, 5, 1, 50)));
// Result: list(list(20, 1, 1), list(10, 5, 1, 5, 1), list(1, 20, 1),
// list(1, 20, 1), list(1, 10, 5, 5, 1),
// list(1, 10, 5, 1, 5))