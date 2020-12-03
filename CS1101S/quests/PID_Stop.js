const motorA = ev3_motorA(); // left wheel
const motorD = ev3_motorD(); // right wheel
const colorSensor = ev3_colorSensor();
const kp = 5; // proportional parameter
const ki = 0.24; // integral parameter
const kd = 7.5; // derivative parameter 
const standard = 15;

function run_spiral(pre_error, i) {
    const error = standard - ev3_reflectedLightIntensity(colorSensor);
    const error_i = i + error;
    const error_d = error - pre_error;

    const output = math_floor(error * kp + error_i * ki + error_d * kd);

    if (500 + output > 0) {
        ev3_runToRelativePosition(motorD, 13, 500 + output);
    }
    else {
        ev3_runToRelativePosition(motorD, -13, -(350 + output));
    }
    if (350 - output > 0) {
        ev3_runToRelativePosition(motorA, 13, 350 - output);
    }
    else {
        ev3_runToRelativePosition(motorA, -13, output - 350);
    }

    ev3_pause(10);
    return run_spiral(error, error_i);
}

run_spiral(standard - ev3_reflectedLightIntensity(colorSensor), 0);