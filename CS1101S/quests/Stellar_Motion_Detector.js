// Q1
// any helper functions and constants go here
const WIDTH = video_width();
const HEIGHT = video_height();
const rbound = 252; // the lower bound of Red
const gbound = 220; // the upper bound of Green
const bbound = 220; // the upper bound of Blue

function is_motion(A) {
    return (A[0] >= rbound && A[1] <= gbound && A[2] <= bbound);
}

// detect all the moving red pixels and draw the blue square
function detect(src, dest) {
    // bluish square
    function draw_square(mini, maxi, minj, maxj) {
        for (let i = 0; i < HEIGHT; i = i + 1) {
            for (let j = 0; j < WIDTH; j = j + 1) {
                if (i >= mini && i <= maxi && j >= minj && j <= maxj) {
                    dest[i][j][2] = 255;
                } else { }
            }
        }
    }
    let mini = HEIGHT;
    let minj = WIDTH;
    let maxi = 0;
    let maxj = 0;
    for (let i = 0; i < HEIGHT; i = i + 1) {
        for (let j = 0; j < WIDTH; j = j + 1) {
            if (is_motion(src[i][j])) {
                mini = mini >= i ? i : mini;
                maxi = maxi <= i ? i : maxi;
                minj = minj >= j ? j : minj;
                maxj = maxj <= j ? j : maxj;
            } else { }
        }
    }
    draw_square(mini, maxi, minj, maxj);
}

function stellar_motion_detector(src, dest) {
    // your program
    const WIDTH = video_width();
    const HEIGHT = video_height();
    copy_image(src, dest);
    detect(src, dest);

}
// reset_filter();
install_filter(stellar_motion_detector);

// Q2
// any helper functions and constants go here
const WIDTH = video_width();
const HEIGHT = video_height();
const rbound = 252; // the lower bound of Red
const gbound = 220; // the upper bound of Green
const bbound = 220; // the upper bound of Blue

let time_counter = 0;

function is_motion(A) {
    return (A[0] >= rbound && A[1] <= gbound && A[2] <= bbound);
}

// play five sine wave sounds as alarm
function alarm() {
    for (let i = 0; i < 5; i = i + 1) {
        time_counter = time_counter + 1000 * i;
        set_timeout(() => play(sine_sound(440, 0.1)), time_counter);
    }
}

// detect all the moving red pixels and draw the blue square
function detect(src, dest) {
    // bluish square
    function draw_square(mini, maxi, minj, maxj) {
        for (let i = 0; i < HEIGHT; i = i + 1) {
            for (let j = 0; j < WIDTH; j = j + 1) {
                if (i >= mini && i <= maxi && j >= minj && j <= maxj) {
                    dest[i][j][2] = 255;
                } else { }
            }
        }
    }
    let mini = HEIGHT;
    let minj = WIDTH;
    let maxi = 0;
    let maxj = 0;
    for (let i = 0; i < HEIGHT; i = i + 1) {
        for (let j = 0; j < WIDTH; j = j + 1) {
            if (is_motion(src[i][j])) {
                // when the motion is detected
                mini = mini >= i ? i : mini;
                maxi = maxi <= i ? i : maxi;
                minj = minj >= j ? j : minj;
                maxj = maxj <= j ? j : maxj;
                alarm();
            } else { }
        }
    }
    draw_square(mini, maxi, minj, maxj);
}

function noisy_stellar_motion_detector(src, dest) {
    // your program
    const WIDTH = video_width();
    const HEIGHT = video_height();
    copy_image(src, dest);
    detect(src, dest);
}
// reset_filter();
install_filter(noisy_stellar_motion_detector);