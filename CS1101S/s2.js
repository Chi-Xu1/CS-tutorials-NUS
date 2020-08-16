//Question 1
//input a smaller joint, and return the biggie-sized version
function biggie_size(option){
    return option > 0 && option <= 4 ? option + 4 : "Error: Not a small size";
}

//Question 2
//input a biggie-sized combo, and return the non-biggie-sized version
function unbiggie_size(option){
    return option >= 5 && option <= 8 ? option - 4 : "Error: Not a big size";
}

//Question 3
//return true if the input is biggie-sized, otherwise return false
function is_biggie_size(option){
    return (option >= 5 && option <= 8) ? true : false;
}

//Question 4
//Calculate the combo price
function combo_price(option){
    return is_biggie_size(option) ? 
    1.17 * (option - 4) + 0.5 : 
    1.17 * option;
}

//Question 5
//A function which takes no arguments an return 0 as empty order
function empty_order(){
    return 0;
}

//Question 6
/*A function which takes an order and a combo and returns a
new order which contains the contents of the old order and 
the new combo
*/
function add_to_order(order,combo){
    return (10 * order + combo);
}

//Question 7
//A function which takes an order and return the last combo
function last_combo(order){
    return order % 10;
}

//Question 8
/*A function which takes an order and return a new order
without the last combo
*/
function other_combo(order){
    return (order - order % 10) / 10;
}
