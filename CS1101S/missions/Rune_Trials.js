// Q2
function mosaic(rune1, rune2, rune3, rune4) {
    // your answer here
    /*Firstly, I should put the rune1 over the rune2, rune4 over rune3.
    Then, put the former on the right side, the latter on the left side.
    */
    return beside(stack(rune4, rune3),stack(rune1, rune2));
}

// Test
show(mosaic(rcross, sail, corner, nova));

// Q3
function mosaic(rune1, rune2, rune3, rune4) {
    // your answer from the previous question here
    /* Firstly, I should put the rune1 over the rune2, rune4 over rune3.
    Then, put the former on the right side, the latter on the left side.
    */
    return beside(stack(rune4, rune3),stack(rune1, rune2));
}

function upside_down_mosaic(rune1, rune2, rune3, rune4) {
    // your answer here
    // apply the mosaic function and turn it upside down
    return turn_upside_down(mosaic(rune1, rune2, rune3, rune4));
}

// Test
show(upside_down_mosaic(rcross, sail, corner, nova));

// Q4
function mosaic(rune1, rune2, rune3, rune4) {
    // your answer from the previous question here
    /* Firstly, I should put the rune1 over the rune2, rune4 over rune3.
    Then, put the former on the right side, the latter on the left side.
    */
    return beside(stack(rune4, rune3),stack(rune1, rune2));
}

function transform_mosaic(rune1, rune2, rune3, rune4, transformation) {
    // your answer here
    /* apply the function argument, the parameter of the function 
        will be mosaic.
    */
    return transformation(mosaic(rune1, rune2, rune3, rune4));
}

// Test
show(transform_mosaic(rcross, sail, corner, nova, make_cross));