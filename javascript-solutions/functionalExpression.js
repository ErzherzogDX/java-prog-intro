"use strict";

const VAR = {
    "x": 0,
    "y": 1,
    "z": 2
};

const CONSTS = {
    "one": 1,
    "two": 2
}

const operation = op => {
    const fun = (...expr) => (...values) => op(...expr.map(ex => ex(...values)));
    // :NOTE: это уже пошли объекты из следующей ДЗ
    fun.argsCount = op.length;
    return fun;
};


const add = operation( (a, b) => a+b);
const subtract = operation((a, b) => a-b);
const multiply = operation((a, b) => a*b);
const divide = operation((a, b) => a/b);
const negate = operation((a) => -a);

const argMin3 = operation( (a, b, c) => argMiniMax([a, b, c], 0));
const argMax3 = operation( (a, b, c) => argMiniMax([a, b, c], 1));
// :NOTE: если бы в модификации была операция argMin100, то ты бы руками прописывал все 100 аргументов
const argMin5 = operation( (a, b, c, d, e) => argMiniMax([a, b, c, d, e], 0));
const argMax5 = operation( (a, b, c, d, e) => argMiniMax([a, b, c, d, e], 1));


// :NOTE: здесь нужно использовать стандартные функции Math.min и Math.max вместе с indexOf
const argMiniMax = (arr, minimax) => {
    const reducer = (acc, cur, index) => (minimax > 0 ? cur > arr[acc] : cur < arr[acc]) ? index : acc;
    return arr.reduce(reducer, 0);
}


let cnst = (val) => () => val;

let variable = (n) => function () {
    return arguments[VAR[n]];
}

for (let tmp in VAR) {
    // :NOTE: куда ссылается этот this? верхнеуровнево использовать его не рекомендуется
    this[tmp] = variable(tmp);
}
for (let x in CONSTS) {
    this[x] = cnst(CONSTS[x]);
}

const OPERATIONS = {
    "+": add,
    "-": subtract,
    "*": multiply,
    "/": divide,
    "negate": negate,
    "argMin3": argMin3,
    "argMax3": argMax3,
    "argMin5": argMin5,
    "argMax5": argMax5
};

const parse = function (st) {
    let stack = [];
    let tokens = st.split(/(\s+)/).filter(n => n.trim().length > 0);

    tokens.map((elem) => {
        if (elem in OPERATIONS) {
            const op = OPERATIONS[elem];
            stack.push(op(...stack.splice(-op.argsCount)));
        }
        else if (elem in VAR) stack.push(variable(elem));
        else if (elem in CONSTS) stack.push(cnst(CONSTS[elem]));
        // :NOTE: зачем eval? а если там будет какой-то небезопасный код? почитай документацию к нему
        else stack.push(cnst(eval(elem)));
        }
    );
    return stack.pop();
}


//let expr = parse("10");

//console.log(expr(5, 0, 0));
