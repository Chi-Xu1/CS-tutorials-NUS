// Q1
const motorA = ev3_motorA(); // left wheel
const motorD = ev3_motorD(); // right wheel
const colorSensor = ev3_colorSensor();

const forward_10_cm = 64;
const forward = 280;
const turn_90 = 148;
const speed = 500;
const time = 100;
const pause_time = 1000;
const pause_time2 = 1;

function move_forward(f, distance, speed, pause_time){
    f(motorD, distance, speed);
    f(motorA, distance, speed);
    ev3_pause(pause_time);
}

function rotate(distance, speed, pause_time){
    ev3_runToRelativePosition(motorD, -distance, speed);
    ev3_runToRelativePosition(motorA, distance, speed);
    ev3_pause(pause_time);
}

function run_black_line(){
    let color = ev3_colorSensorGetColor(colorSensor);
    // Whenever color detected is black it will move forward
    while(color === 1 || color === 0){
        color = ev3_colorSensorGetColor(colorSensor);
        move_forward(ev3_runForTime, time, speed/2, pause_time2);
        display(color);
    }
}

// Q2
//Your program here.
const motorA = ev3_motorA(); // left wheel
const motorD = ev3_motorD(); // right wheel
const colorSensor = ev3_colorSensor();

const forward_10_cm = 64;
const forward = 280;
const turn_90 = 148;
const speed = 500;
const time = 100;
const pause_time = 1000;
const pause_time2 = 1;
const lower_bond = 10;
const upper_bond = 20;

function move_forward(f, distance, speed, pause_time){
    f(motorD, distance, speed);
    f(motorA, distance, speed);
    ev3_pause(pause_time);
}

function rotate(motor, distance, speed, pause_time){
    ev3_runToRelativePosition(motor, -distance, speed);
    ev3_pause(pause_time);
}

function run_maze() {
    let color = ev3_reflectedLightIntensity(colorSensor);
    while(true) {
        // update colorSensor
        color = ev3_reflectedLightIntensity(colorSensor);
        display(color);
        if (color >= lower_bond && color <= upper_bond) {
            // when the black part and white part are equal in the sensor 
            move_forward(ev3_runForTime, 100, 200, 100);
        }
        else {
            if (color < lower_bond) {
                // when the black part is more than the white part
                // rotate -15 degree anticlockwiseA
                rotate(motorA, -turn_90/6, speed/2, pause_time2);
            }
            else {
                // when the black part is less than the white part
                // rotate 15 degree anticlockwise 
                rotate(motorA, turn_90/6, speed/2, pause_time2);
            }
        }
    }
}

run_maze();