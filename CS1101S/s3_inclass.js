//Question 1
function moony_1(bottom_right)
{
    const left = stack(circle, square);
    const right = stack(blank, bottom_right);
    return beside(left, right);
}

//Question 2
function moony_2(n)
{
    return moony_2_helper(n, circle);
}

function moony_2_helper(n, pic)
{
    return n === 0
        ? pic
        : moony_2_helper(n - 1, moony_1(pic));
}
//show(moony_2(5));
/*function moony_2(n)
{
    return n === 0
        ? monny_2(circle)
        : monny_1(moony_2(n - 1));
}
*/

//Question 3 Part 1
function moony_even_row(n)
{
    return moony_iterative(n, 1, circle);
}

function moony_1_even_row(n, b)
{
    return beside(stack_frac(1 / n, circle, square),
                  stack_frac(1 / n, blank, b));
}

function moony_iterative(n, counter, pic)
{
    return counter === n
        ? pic
        : moony_iterative(n, 
                          counter + 1, 
                          moony_1_even_row(counter + 1, pic));
}
show(moony_even_row(2));

function moony_1_even_row(n, b)
{
    return beside(stack_frac(1 / n, circle, square),
                  stack_frac(1 / n, blank, b));
}

//Question 3 Part 2
function moony_even_rows(n)
{
    return n === 1
          ? circle
          : moony_1_even_row(n, moony_even_rows(n - 1));
}

show(moony_even_rows(5));

function moony_helper(n, b)
{
    return beside_frac(1 / n,
                       stack_frac(1 / n, circle, square),
                       stack_frac(1 / n, blank, b));
}

function moony(n)
{
    return n === 1
          ? circle
          : moony_helper(n, moony(n - 1));
}
