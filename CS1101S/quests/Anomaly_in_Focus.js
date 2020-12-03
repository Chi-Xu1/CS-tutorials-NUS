// Question 1
function is_red(pixel) {
    return pixel[0] === 255 && pixel[1] === 0 && pixel[2] === 0;
}

function detect_anomaly(image) {
    const HEIGHT = array_length(image);
    const WIDTH = array_length(image[0]);
    let mini = HEIGHT;
    let minj = WIDTH;
    let maxi = 0;
    let maxj = 0;
    for (let i = 0; i < HEIGHT; i = i + 1) {
        for (let j = 0; j < WIDTH; j = j + 1) {
            if (is_red(image[i][j])) {
                mini = mini >= i ? i : mini;
                maxi = maxi <= i ? i : maxi;
                minj = minj >= j ? j : minj;
                maxj = maxj <= j ? j : maxj;
            } else { }
        }
    }
    return pair(pair(mini, minj), pair(maxi, maxj));
}

function find_red_rectangles(s) {
    // your solution goes here
    return stream_map(x => detect_anomaly(x), s);
}

head(find_red_rectangles(anomaly_stream));
// should evaluate to: [[141, 191], [159, 209]]

// Q2
// Question 2

// copy your function find_red_rectangles from Question 1 here
function is_red(pixel) {
    return pixel[0] === 255 && pixel[1] === 0 && pixel[2] === 0;
}

function detect_anomaly(image) {
    const HEIGHT = array_length(image);
    const WIDTH = array_length(image[0]);
    let mini = HEIGHT;
    let minj = WIDTH;
    let maxi = 0;
    let maxj = 0;
    for (let i = 0; i < HEIGHT; i = i + 1) {
        for (let j = 0; j < WIDTH; j = j + 1) {
            if (is_red(image[i][j])) {
                mini = mini >= i ? i : mini;
                maxi = maxi <= i ? i : maxi;
                minj = minj >= j ? j : minj;
                maxj = maxj <= j ? j : maxj;
            } else { }
        }
    }
    return pair(pair(mini, minj), pair(maxi, maxj));
}

function find_red_rectangles(s) {
    return stream_map(x => detect_anomaly(x), s);
}

function zip_stream(f, s1, s2) {
    // your solution goes here
    return pair(f(head(s1), head(s2)),
        () => zip_stream(f, stream_tail(s1), stream_tail(s2)));
}

// trim the given image using the given rectangle
// returns an image that includes all purely red
// pixels of the given image

function trim(image, rectangle) {
    const trimmed = [];
    const i_min = head(head(rectangle));
    const j_min = tail(head(rectangle));
    const i_max = head(tail(rectangle));
    const j_max = tail(tail(rectangle));

    for (let i = i_min; i <= i_max; i = i + 1) {
        const new_i = i - i_min;
        trimmed[new_i] = [];
        for (let j = j_min; j <= j_max; j = j + 1) {
            const new_j = j - j_min;
            trimmed[new_i][new_j] = image[i][j];
        }
    }
    return trimmed;
}

// Example:

const focused_stream = zip_stream(
    trim,
    anomaly_stream,
    find_red_rectangles(anomaly_stream));

head(focused_stream);

// should return a close-up of the anomaly, a 19x19 image of black,
// red and white pixels