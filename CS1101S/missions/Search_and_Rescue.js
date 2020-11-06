// Q1

function binary_search_tree_to_string(bst) {
    // Your answer here
    if(is_tree(bst)) {
        if(is_empty_tree(bst)) {
            return "";
        }
        else {
            return binary_search_tree_to_string(left_branch(bst)) 
                    + entry(bst) + "; " + 
                   binary_search_tree_to_string(right_branch(bst));
        }
    }
    else {
        return "";
    }
}

const h = make_tree("h", make_empty_tree(),  make_empty_tree());
const a = make_tree("a", make_empty_tree(), make_empty_tree());
const n = make_tree("n", h, make_empty_tree());
const c = make_tree("c", a, make_empty_tree());
const test_bst = make_tree("e", c, n);

// Test

binary_search_tree_to_string(test_bst);

binary_search_tree_to_string(cadet_names);

// Q2

function insert(bst, item) {
    // your solution goes here
    if(is_tree(bst)){
        if(is_empty_tree(bst)) {
            return make_tree(item, make_empty_tree(), make_empty_tree());
        }
        else {
            if(item < entry(bst)){
                return make_tree(entry(bst), 
                          insert(left_branch(bst), item),
                          right_branch(bst));
            }
            else {
                return make_tree(entry(bst),
                                 left_branch(bst),
                                 insert(right_branch(bst), item));
            }
        }    
    }
    else {
        return make_tree(item, make_empty_tree(), make_empty_tree());
    }
}


// Copy your binary_search_tree_to_string function here from the previous task
function binary_search_tree_to_string(bst) {
    if(is_tree(bst)) {
        if(is_empty_tree(bst)) {
            return "";
        }
        else {
            return binary_search_tree_to_string(left_branch(bst)) 
                    + entry(bst) + "; " + 
                   binary_search_tree_to_string(right_branch(bst));
        }
    }
    else {
        return "";
    }
}
// Test

binary_search_tree_to_string(insert(make_empty_tree(), "x")); 
// Should produce "x; "

const bst = accumulate((item, bst) =>
                          insert(bst, item),
                      make_empty_tree(),
                      list("g", "a", "r", "x", "p"));
binary_search_tree_to_string(bst); 
// Should produce "a; g; p; r; x; "

const cadet_names_with_aaaron =  insert(cadet_names, "A AARON");
binary_search_tree_to_string(cadet_names_with_aaaron); 
// Should produce "A AARON; ..."

// Q3

function find(bst, name) {
    // Your answer here
    if(is_tree(bst)){
        if(is_empty_tree(bst)) {
            return false;
        }
        else {
            if(name === entry(bst)) {
                return true;
            }
            else {
                if(name < entry(bst)) {
                    return find(left_branch(bst), name);
                }
                else {
                    return find(right_branch(bst), name);
                }
            }
        }    
    }
    else {
        return false;
    }
}

// Test
find(cadet_names, "CHI XU");