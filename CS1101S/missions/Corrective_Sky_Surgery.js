// Question 1

function my_first_filter(ignore, dest) {
    const WIDTH = video_width();
    const HEIGHT = video_height();
    
    for (let i = 0; i < HEIGHT; i = i + 1) {
        for (let j = 0; j < WIDTH; j = j + 1) {
            if(i <= HEIGHT / 2) {
                if(j <= WIDTH / 2) {
                    // display blue
                    dest[i][j][0] = 0;
                    dest[i][j][1] = 0;
                    dest[i][j][2] = 255;
                }
                else {
                    // display green
                    dest[i][j][0] = 0;
                    dest[i][j][1] = 255;
                    dest[i][j][2] = 0;
                }
            }
            else {
                if(j <= WIDTH / 2) {
                    // display red
                    dest[i][j][0] = 255;
                    dest[i][j][1] = 0;
                    dest[i][j][2] = 0;
                }
                else {
                    // display yellow
                    dest[i][j][0] = 255;
                    dest[i][j][1] = 255;
                    dest[i][j][2] = 0;
                }
            }
            dest[i][j][3] = 255;
        }
    }
}

// install_filter(my_first_filter);

// filter gradually change the colors
function my_second_filter(ignore, dest) {
    const WIDTH = video_width();
    const HEIGHT = video_height();
    const k1 = 255 / HEIGHT;
    const k2 = 255 / WIDTH;
    function blue(i, j) {
        let r = 255 - k1 * i - k2 * j;
        return r > 0 ? r : 0;
    }
    for (let i = 0; i < HEIGHT; i = i + 1) {
        for (let j = 0; j < WIDTH; j = j + 1) {
            dest[i][j][0] = k1 * i;
            dest[i][j][1] = k2 * j;
            dest[i][j][2] = blue(i, j); 
            dest[i][j][3] = 255;
        }
    }
}

install_filter(my_second_filter);

// Question 2

function copy(src, dest) {
    const WIDTH = video_width();
    const HEIGHT = video_height();
    
    for (let i = 0; i < HEIGHT; i = i + 1) {
        for (let j = 0; j < WIDTH; j = j + 1) {
           dest[i][j][0] = src[i][j][0];
           dest[i][j][1] = src[i][j][1];
           dest[i][j][2] = src[i][j][2];
           dest[i][j][3] = src[i][j][3];
        }
    }
}

function crosshair(src, dest) {
    // your program goes here
    const WIDTH = video_width();
    const HEIGHT = video_height();
    copy(src, dest);
    function red_cross(i, j) {
        if((i >= HEIGHT / 2 - 1 && i <= HEIGHT / 2 + 1) || 
        (j >= WIDTH / 2 - 1 && j <= WIDTH / 2 + 1)) {
            dest[i][j][0] = 255;
      }
      else {}
    }
    function circle(i, j) {
        let x = i - HEIGHT / 2;
        let y = j - WIDTH / 2;
        let r = math_sqrt(x*x + y*y);
        if(math_floor(r / (HEIGHT / 12)) % 2 === 1) {
            dest[i][j][2] = 255;
        }
        else{}
    }
    
    for (let i = 0; i < HEIGHT; i = i + 1) {
        for (let j = 0; j < WIDTH; j = j + 1) {
          red_cross(i, j);
          circle(i, j);
        }
    }
}

install_filter(crosshair);

// Question 3

function zoom(factor) {
    // your solution here
    const WIDTH = video_width();
    const HEIGHT = video_height();
    const transi = i => math_floor(
        i / factor + (HEIGHT / 2) * (1 - 1 / factor));
    const transj = j => math_floor(
        j / factor + (WIDTH / 2) * (1 - 1 / factor));
    
    function helper(src, dest) {
        for (let i = 0; i < HEIGHT; i = i + 1) {
            for (let j = 0; j < WIDTH; j = j + 1) {
               let ti = transi(i);
               let tj = transj(j);
               dest[i][j][0] = src[ti][tj][0];
               dest[i][j][1] = src[ti][tj][1];
               dest[i][j][2] = src[ti][tj][2];
               dest[i][j][3] = src[ti][tj][3];
            }
        }
    }
    return helper;
}

install_filter(zoom(2));

// function zoom_gradually(factor) {
//     const increase = 0.01;
//     let i = 1;
//     while (i >= factor) {
//         install_filter(zoom(i));
//         i = i + increase;
//     }
//     install_filter(zoom(factor));
// }

// zoom_gradually(2);

// Question 4

function flip_vertically(src, dest) {
    const HEIGHT = video_height();
    const WIDTH = video_width();
    
    for (let i = 0; i < HEIGHT; i = i + 1) {
        for (let j = 0; j < WIDTH; j = j + 1) {
            for (let k = 0; k < 4; k = k + 1) {
                dest[i][j][k] = src[HEIGHT - 1 - i][j][k];
            }
        }
    }
}

function color_invert(src, dest) {
    const WIDTH = video_width();
    const HEIGHT = video_height();
    
    for (let i = 0; i < HEIGHT; i = i + 1){
        for (let j = 0; j < WIDTH; j = j + 1){
            for (let c = 0; c < 4; c = c + 1) {
                dest[i][j][c] = c < 3 ? 255 - src[i][j][c] : src[i][j][c];
            }          
        }
    }
}

// copy your solution for Question 3 (zoom) here   
function zoom(factor) {
    const WIDTH = video_width();
    const HEIGHT = video_height();
    const transi = i => math_floor(
        i / factor + (HEIGHT / 2) * (1 - 1 / factor));
    const transj = j => math_floor(
        j / factor + (WIDTH / 2) * (1 - 1 / factor));
    
    function helper(src, dest) {
        for (let i = 0; i < HEIGHT; i = i + 1) {
            for (let j = 0; j < WIDTH; j = j + 1) {
               let ti = transi(i);
               let tj = transj(j);
               dest[i][j][0] = src[ti][tj][0];
               dest[i][j][1] = src[ti][tj][1];
               dest[i][j][2] = src[ti][tj][2];
               dest[i][j][3] = src[ti][tj][3];
            }
        }
    }
    return helper;
}

function make_image() {
    const WIDTH = video_width();
    const HEIGHT = video_height();
    
    const img = [];
    for (let i = 0; i < HEIGHT; i = i + 1) {
        const row = [];
        img[i] = row;
        for (let j = 0; j < WIDTH; j = j + 1) {
            const pixel = [];
            row[j] = pixel;
            for (let z = 0; z < 4; z = z + 1) {
                pixel[z] = 255;
            }
        }             
    }
    return img;
}

function stack(filter1, filter2) {
    const temp1 = make_image();
    const temp2 = make_image();

    const HEIGHT = video_height();
    const half_height = math_floor(HEIGHT / 2);

    return (src, dest) => {
        filter1(src, temp1);
        filter2(src, temp2);
        for (let i = 0; i < half_height; i = i + 1) {
            dest[i] = temp1[i * 2];
            dest[i + half_height] = temp2[i * 2];
        }
        // take last row from temp2, if HEIGHT is odd
        for (let i = half_height * 2; i < HEIGHT; i = i + 1) {
            dest[i] = temp2[i];
        }
    };
}

function beside(filter1, filter2) {    
    // your program goes here
    const temp1 = make_image();
    const temp2 = make_image();

    const WIDTH = video_width();
    const HEIGHT = video_height();
    const half_width = math_floor(WIDTH / 2);

    return (src, dest) => {
        filter1(src, temp1);
        filter2(src, temp2);
        for(let i = 0; i < HEIGHT; i = i + 1) {
            for (let j = 0; j < half_width; j = j + 1) {
                dest[i][j] = temp1[i][j * 2];
                dest[i][j + half_width] = temp2[i][j * 2];
            }
        }
        // take last row from temp2, if HEIGHT is odd
        for(let i = 0; i < HEIGHT; i = i + 1) {
            for (let j = half_width * 2; j < WIDTH; j = j + 1) {
                dest[i][j] = temp2[i][j];
            }
        }
    };
}

install_filter(stack(beside(flip_vertically, color_invert),
                     beside(copy_image, zoom(2))));
                     function flip_vertically(src, dest) {
                        const HEIGHT = video_height();
                        const WIDTH = video_width();
                        
                        for (let i = 0; i < HEIGHT; i = i + 1) {
                            for (let j = 0; j < WIDTH; j = j + 1) {
                                for (let k = 0; k < 4; k = k + 1) {
                                    dest[i][j][k] = src[HEIGHT - 1 - i][j][k];
                                }
                            }
                        }
                    }
                    
                    function color_invert(src, dest) {
                        const WIDTH = video_width();
                        const HEIGHT = video_height();
                        
                        for (let i = 0; i < HEIGHT; i = i + 1){
                            for (let j = 0; j < WIDTH; j = j + 1){
                                for (let c = 0; c < 4; c = c + 1) {
                                    dest[i][j][c] = c < 3 ? 255 - src[i][j][c] : src[i][j][c];
                                }          
                            }
                        }
                    }
                    
                    function make_image() {
                        const WIDTH = video_width();
                        const HEIGHT = video_height();
                        
                        const img = [];
                        for (let i = 0; i < HEIGHT; i = i + 1) {
                            const row = [];
                            img[i] = row;
                            for (let j = 0; j < WIDTH; j = j + 1) {
                                const pixel = [];
                                row[j] = pixel;
                                for (let z = 0; z < 4; z = z + 1) {
                                    pixel[z] = 255;
                                }
                            }             
                        }
                        return img;
                    }
                    
                    function compose(filter1, filter2) {
                        // your program goes here
                        const temp = make_image();
                        return (src, dest) => {
                            filter1(src, temp);
                            filter2(temp, dest);
                        };
                    }
                    
                    install_filter(compose( flip_vertically, color_invert));