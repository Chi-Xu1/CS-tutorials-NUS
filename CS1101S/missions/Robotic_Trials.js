// Q1
const motorA = ev3_motorA(); // left wheel
const motorD = ev3_motorD(); // right wheel

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
move_forward(ev3_runToRelativePosition, forward_10_cm, speed, pause_time);

// Q2
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


function rotate_90_degree(distance, speed, pause_time){
    ev3_runToRelativePosition(motorD, -distance, speed);
    ev3_runToRelativePosition(motorA, distance, speed);
    ev3_pause(pause_time);
}
rotate_90_degree(turn_90,
                    speed,
                    pause_time * 2);

// Q3
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

function rotate_90_degree(distance, speed, pause_time){
    ev3_runToRelativePosition(motorD, -distance, speed);
    ev3_runToRelativePosition(motorA, distance, speed);
    ev3_pause(pause_time);
}


function run(){
    let count = 0;
    while(count < 4) {
        
        let color = ev3_colorSensorGetColor(colorSensor);
        if (color === 2) {
            move_forward(ev3_runForTime,
                        time,
                        speed,
                        pause_time2);
            display(color);
        } else {
            count = count + 1;
            move_forward(ev3_runToRelativePosition,
                        forward,
                        speed,
                        pause_time);
    
        rotate_90_degree(turn_90,
                    speed,
                    pause_time * 2);
        
        display(color);
        }
    }
}

run();