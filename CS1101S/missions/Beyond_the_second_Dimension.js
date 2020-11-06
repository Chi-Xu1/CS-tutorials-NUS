// Q1
function steps(r1, r2, r3, r4){
    function mosaic(rune1, rune2, rune3, rune4) {
    return beside(stack(rune4, rune3),stack(rune1, rune2));
    }
    return overlay_frac(1 / 4, mosaic(blank, blank, blank, r4),
                   overlay_frac(1 / 3, mosaic(blank, blank, r3, blank),
                   overlay(mosaic(blank, r2, blank, blank),
                   mosaic(r1, blank, blank, blank))
                   )
                   );
}

// Test
show(steps(rcross, sail, corner, nova));

// Q2
 // steps(r1, r2, r3, r4) -> {Rune}
 /* makes a new 3-D Rune from four given Rune by arranging them clockwise,
 starting from the top-right corner, where the r1 is placed, and the depth of 
 the image increases evenly and clockwise from the top-right corner.*/
 /* Parameters:
    Name        Type        Description
    r1          Rune        given Rune
    r2          Rune        given Rune
    r3          Rune        given Rune
    r4          Rune        given Rune
 */ 
 /*
    Return: resulting Rune
    Type: Rune
 */
 
function steps(r1, r2, r3, r4){
    // your answer from Q1 goes here
    function mosaic(rune1, rune2, rune3, rune4) {
    return beside(stack(rune4, rune3),stack(rune1, rune2));
    }
    return overlay_frac(1 / 4, mosaic(blank, blank, blank, r4),
                   overlay_frac(1 / 3, mosaic(blank, blank, r3, blank),
                   overlay(mosaic(blank, r2, blank, blank),
                   mosaic(r1, blank, blank, blank))
                   )
                   );
}

show(steps(rcross, sail, corner, nova));

// Q3
function cone(n, rune){
    // your answer here
    return cone_helper(n, n - 1 , rune, rune);
}

//helper function with iterative process
function cone_helper(n, counter, pic, rune){
    return counter === 0
         ? pic
         : cone_helper(n, counter - 1, overlay_frac(1 / (n - counter + 1),
             scale(counter / n, rune), pic), rune);
}

// Test
show(cone(4, circle));