/*
wishful thinking vs recursive thinking
*/

//R5 Q1
//just for the matrix
function flatten_list(lst) {
    return accumulate(append, null, lst);
}

//for all the tree
function flatten_tree(tree) {
    return accumulate((curr, rest) =>
        is_list(curr)
            ? append(flatten_tree(curr), rest)
            : pair(curr, rest),
        null, tree);
}

// const my_matrix = list(list(1, 2, 3), list(4, 5, 6), list(7, 8, 9));
// const my_list = flatten_list(my_matrix);
// display_list(my_list);
// returns: list(1, 2, 3, 4, 5, 6, 7, 8, 9);

//R5 Q2
//two ways to solve the problem
//wishful thinking
function tree_sum(tree) {
    if (is_null(tree)) {
        return 0;
    }
    else {
        if (is_list(head(tree))) {
            return tree_sum(head(tree)) + tree_sum(tail(tree));
        }
        else {
            return head(tree) + tree_sum(tail(tree));
        }
    }
}

//recrusive thinking
function sum(tree) {
    return accumulate((curr, rest) =>
        is_list(curr)
            ? sum(curr) + rest
            : curr + rest,
        0, tree);
}
// const my_tree = list(1, list(2, list(3, 4), 5), list(6, 7));
// tree_sum(my_tree);
// Returns: 28

//R5 Q3
//wishful thinking
function accumulate_tree(f1, f2, initial, tree) {
    if (is_null(tree)) {
        return initial;
    }
    else {
        if (is_list(head(tree))) {
            return f2(accumulate_tree(f1, f2, initial, head(tree)),
                accumulate_tree(f1, f2, initial, tail(tree)));
        }
        else {
            return f2(f1(head(tree)),
                accumulate_tree(f1, f2, initial, tail(tree)));
        }
    }
}

//recursive thinking
function accumulate_tree2(f, op, init, tree) {
    return accumulate((curr, rest) =>
        is_list(curr)
            ? op(accumulate_tree2(f, op, init, curr), rest)
            : op(f(curr), rest),
        init, tree);
}

function accumulate_tree3(f, op, init, tree){
    return accumulate((curr, rest) => 
                    is_list(curr)
                        ? accumulate_tree3(f, op, rest, curr)
                        : op(f(curr), rest),
                    init, tree);

function count_data_items(tree) {
    return accumulate_tree(x => 1, (x, y) => x + y, 0, tree);
}
// example
count_data_items(
    list(list(1, 2, 3),
        4,
        list(list(5, 6)),
        list(7, 8)));