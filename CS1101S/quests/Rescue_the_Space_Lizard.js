// TASK 1

// return the maximum of the three input parameter
function max(x, y, z) {
    return x > y
        ? y > z ? x : x > z ? x : z
        : x > z ? y : y > z ? y : z;
}

function max_flies_to_eat(tile_flies) {

    // *** Your answer here. ***
    let result = 0;
    const row = array_length(tile_flies);
    const column = array_length(tile_flies[0]);
    function helper(i, j) {
        if (i >= row || j < 0 || j >= column) {
            return 0;
        }
        else {
            let eat1 = helper(i + 1, j);
            let eat2 = helper(i + 1, j - 1);
            let eat3 = helper(i + 1, j + 1);
            return tile_flies[i][j] + max(eat1, eat2, eat3);
        }
    }
    for (let k = 0; k < column; k = k + 1) {
        let temp = helper(0, k);
        result = result < temp ? temp : result;
    }
    return result;
}

// TEST:
const tile_flies = [[3, 1, 7, 4, 2],
[2, 1, 3, 1, 1],
[1, 2, 2, 1, 8],
[2, 2, 1, 5, 3],
[2, 1, 4, 4, 4],
[5, 7, 2, 5, 1]];

max_flies_to_eat(tile_flies); // Expected result: 32
// TASK 2

const mem = [];

function read(n, k) {
    return (mem[n] === undefined) ?
        undefined : mem[n][k];
}

function write(n, k, value) {
    if (mem[n] === undefined) {
        mem[n] = [];
    } else { }
    mem[n][k] = value;
}

// return the maximum of the three input parameter
function max(x, y, z) {
    return x > y
        ? y > z ? x : x > z ? x : z
        : x > z ? y : y > z ? y : z;
}

function memo_max_flies_to_eat(tile_flies) {

    // *** Your answer here. ***
    let result = 0;
    const row = array_length(tile_flies);
    const column = array_length(tile_flies[0]);
    function helper(i, j) {
        if (i >= row || j < 0 || j >= column) {
            return 0;
        }
        else {
            if (read(i, j) !== undefined) {
                return read(i, j);
            }
            else {
                let eat1 = helper(i + 1, j);
                let eat2 = helper(i + 1, j - 1);
                let eat3 = helper(i + 1, j + 1);
                const temp = tile_flies[i][j] + max(eat1, eat2, eat3);
                write(i, j, temp);
                return temp;
            }
        }
    }
    for (let k = 0; k < column; k = k + 1) {
        let temp = helper(0, k);
        result = result < temp ? temp : result;
    }
    return result;
}

// TEST:
const tile_flies = [[3, 1, 7, 4, 2],
[2, 1, 3, 1, 1],
[1, 2, 2, 1, 8],
[2, 2, 1, 5, 3],
[2, 1, 4, 4, 4],
[5, 7, 2, 5, 1]];

memo_max_flies_to_eat(tile_flies); // Expected result: 32