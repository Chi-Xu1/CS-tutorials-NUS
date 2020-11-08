// Question 1
		
function array_to_stream(a) {
    // your solution goes here
    const len = array_length(a);
    function helper(n) {
        return n >= len
            ? null
            : pair(a[n], () => helper(n + 1));
    }
    return helper(0);
}


display(array_length(anomaly_data) ===
        stream_length(array_to_stream(anomaly_data)));
display(anomaly_data[7] === 
        stream_ref(array_to_stream(anomaly_data), 7));

// Question 2

// your array_to_stream function from Question 1 goes here		  
function array_to_stream(a) {
    const len = array_length(a);
    function helper(n) {
        return n >= len
            ? null
            : pair(a[n], () => helper(n + 1));
    }
    return helper(0);
}

function stream_to_filter(s) {
    // your solution goes here
    function result_filter(src, dest) {
        if(!is_null(stream_tail(s))) {
            copy_image(head(s), dest);
            s = stream_tail(s);
        }
        else {
            copy_image(head(s), dest);
        }
    }
    return result_filter;
}

install_filter(stream_to_filter(array_to_stream(anomaly_data)));

// Question 3

// your stream_to_filter and array_to_stream
// functions go here
function array_to_stream(a) {
    const len = array_length(a);
    function helper(n) {
        return n >= len
            ? null
            : pair(a[n], () => helper(n + 1));
    }
    return helper(0);
}

function stream_to_filter(s) {
    function result_filter(src, dest) {
        if(!is_null(stream_tail(s))) {
            copy_image(head(s), dest);
            s = stream_tail(s);
        }
        else {
            copy_image(head(s), dest);
        }
    }
    return result_filter;
}

function is_infinite(s) {
    let p = s;
    let flag = false;
    while(!is_null(stream_tail(p))) {
        if(stream_tail(p) === s) {
            // the stream is infinite
            flag = true;
            break;
        }else {}
        p = stream_tail(p);
    }
    return flag;
}

function loop(s) {
    // your solution goes here
    function helper(q) {
        return is_null(q) 
            ? helper(s)
            : pair(head(q), () => helper(stream_tail(q)));
    }
    if(is_infinite(s)) {
        return s;
    }
    else {
        return helper(s);
    }
}
install_filter(
    stream_to_filter(
        loop(array_to_stream(anomaly_data))));

// Question 4

// your stream_to_filter, array_to_stream and loop
// functions go here
function array_to_stream(a) {
    const len = array_length(a);
    function helper(n) {
        return n >= len
            ? null
            : pair(a[n], () => helper(n + 1));
    }
    return helper(0);
}

function stream_to_filter(s) {
    function result_filter(src, dest) {
        if(!is_null(stream_tail(s))) {
            copy_image(head(s), dest);
            s = stream_tail(s);
        }
        else {
            copy_image(head(s), dest);
        }
    }
    return result_filter;
}

function is_infinite(s) {
    let p = s;
    let flag = false;
    while(!is_null(stream_tail(p))) {
        if(stream_tail(p) === s) {
            // the stream is infinite
            flag = true;
            break;
        }else {}
        p = stream_tail(p);
    }
    return flag;
}

function loop(s) {
    function helper(q) {
        return is_null(q) 
            ? helper(s)
            : pair(head(q), () => helper(stream_tail(q)));
    }
    if(is_infinite(s)) {
        return s;
    }
    else {
        return helper(s);
    }
}

function time_lapse(s, n) {
    // your solution goes here
    function helper(s, counter) {
        if(is_null(s)) {
            return null;
        }
        else {
            if(counter < n) {
                return helper(stream_tail(s), counter + 1);
            }
            else {
                return pair(head(s), () => helper(stream_tail(s), 1));
            }
        }
    }
    return pair(head(s), () => helper(stream_tail(s), 1));
}


install_filter(
    stream_to_filter(
        time_lapse(loop(array_to_stream(anomaly_data)),
                   3)));
