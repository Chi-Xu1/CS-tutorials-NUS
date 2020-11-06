// Q1
//Your program here.
const motorA = ev3_motorA(); // left wheel
const motorD = ev3_motorD(); // right wheel
const colorSensor = ev3_colorSensor();
const slow_speed = 200;
const full_speed = 600;
const time = 200;

function move_forward(f, distance, speed, pause_time){
    f(motorD, distance, speed);
    f(motorA, distance, speed);
    ev3_pause(pause_time);
}

function run_dangerous_zone() {
    let color = ev3_colorSensorGetColor(colorSensor);
    let green_count = 0;
    let red_count = 0;

    while (red_count < 2) {
        color = ev3_colorSensorGetColor(colorSensor);
        display(color);

        if (color === 3) {
            green_count = green_count + 1;
        } else if (color === 5) {
            red_count = red_count + 1;
        } else { }

        if (green_count <= 1) {
            move_forward(ev3_runForTime, time, slow_speed, time);
        } else if (green_count === 2 && red_count === 0) {
            move_forward(ev3_runForTime, time, full_speed, time);
        } else if (red_count === 1) {
            move_forward(ev3_runForTime, time, slow_speed, time);
        } else { }
    }
}

run_dangerous_zone();

// Q2
//Your program here.
const motorA = ev3_motorA(); // left wheel
const motorD = ev3_motorD(); // right wheel
const colorSensor = ev3_colorSensor();
const slow_speed = 200;
const full_speed = 600;
const time = 200;
const rotate_90 = 155;

const forward_10_cm = 64;
const forward = 380;
const pause_time = 3000;
const speed = 200;

function move_forward(f, distance, speed, pause_time) {
    f(motorD, distance, speed);
    f(motorA, distance, speed);
    ev3_pause(pause_time);
}

function rotate(distance, speed, pause_time) {
    ev3_runToRelativePosition(motorD, -distance, speed);
    ev3_runToRelativePosition(motorA, distance, speed);
    ev3_pause(pause_time);
}

function run() {
    display("Run starts.");
    let color = ev3_colorSensorGetColor(colorSensor);
    let count = 0;
    for (let i = 0; i <= 3; i = i + 1) {
        rotate(rotate_90 + 4, slow_speed, time * 4.5);
    }
    while (count < 3) {
        color = ev3_colorSensorGetColor(colorSensor);
        if (color === 2 || color === 6) {
            move_forward(ev3_runForTime,
                time,
                speed,
                time);
            display(color);
        } else {
            count = count + 1;
            // rotate 360 degree

            // move forward to cross the line
            move_forward(ev3_runToRelativePosition,
                forward,
                speed,
                pause_time);

            rotate(rotate_90,
                speed,
                pause_time / 2);

            for (let i = 0; i <= 3; i = i + 1) {
                rotate(rotate_90 + 4, slow_speed, time * 4.5);
            }
            display(color);
        }
    }
    display("Run ended.");
}

function to_clockwise() {
    let color = ev3_colorSensorGetColor(colorSensor);
    rotate(rotate_90, slow_speed, time * 5);// rotate 90 degree and detect color

    while (true) {
        color = ev3_colorSensorGetColor(colorSensor);
        display(color);
        if (color === 1 || color === 0 || color === 7) {
            // color sensor may not detect black consistently
            /* if color is black, which means the 
              original direction is anticlockwise,
              rotate 180 degree to become clockwise*/
            rotate(rotate_90, slow_speed, time * 5);
            rotate(rotate_90, slow_speed, time * 5);
            break;
        } else if (color !== 2 && color !== 6) {
            /* if color is not black, which means the original 
              direction is clockwise,
              then rotate -90 degree to become the original direction.*/

            move_forward(ev3_runToRelativePosition,
                -64,
                slow_speed,
                time * 5);
            rotate(-rotate_90, slow_speed, time * 5);

            break;
        } else { }
        move_forward(ev3_runForTime, time, slow_speed, time);
    }
    display("Clockwise turned");
}

function find_a() {
    let color = ev3_colorSensorGetColor(colorSensor);
    while (color !== 3) {
        if (color === 2 || color === 6) {
            // the car is running on the white block
            move_forward(ev3_runForTime,
                time,
                speed,
                time);
        } else {
            // the car encounters the non-green line
            move_forward(ev3_runToRelativePosition,
                forward,
                speed,
                pause_time);

            rotate(rotate_90,
                speed,
                pause_time / 2);
        }
        color = ev3_colorSensorGetColor(colorSensor);
        display(color);
    }
    move_forward(ev3_runToRelativePosition,
        -80,
        speed,
        pause_time / 2);
    display("A found");
}

function task() {
    to_clockwise();
    find_a();
    run();
}
task();