"use strict";

const vars = "xyz";

const ParseException = function (name, msg) {
    const result = function (...args) {
        this.name = name;
        this.msg = msg(...args);
    };
    result.prototype = Object.create(Error.prototype);
    return result;
};

const MissingOperation = ParseException("MissingOperation",
    (index) => "Missing operation at position " + index)

const UnknownOperation = ParseException("UnknownOperation",
    (operation, index) => "Operation " + operation + " is unknown at position " + index)

const MissingArgument = ParseException("MissingArgument",
    (op, realArg, givenArg) => "You have entered " + (realArg - (givenArg)) + " arguments - not equal " + realArg + " required for the operation " + op);

const BracketOperation = ParseException("UnknownOperation",
    (index) => "Missed closing bracket at position " + index);

const UnexpectedOperation = ParseException("UnexpectedOperation",
    (operation, index, current) => "Unexpected operation " + operation + " at position " + index + " while argument entering for operation + " + current);

function Variable(name) {
    this.name = name;
    this.evaluate = function () {
        return arguments[vars.indexOf(this.name)];
    }
    this.toString = () => this.name;
    this.diff = (name) => name === this.name ? new Const(1) : new Const(0);
    this.prefix = () => this.toString();
    this.postfix = () => this.toString();
}

function Const(value) {
    this.value = value;
    this.toString = () => this.value.toString();
    this.evaluate = () => this.value;
    this.diff = () => new Const(0);
    this.prefix = () => this.toString();
    this.postfix = () => this.toString();
}


const Operation = function (...arg) {
    this.evaluate = function (...args) {
        const result = arg.map(function (operand) {
            return operand.evaluate(...args);
        })
        return this.f(...result);
    }

    this.toString = function () {
        return arg.join(" ") + " " + this.operation;
    }
    this.diff = function (name) {
        switch (this.operation) {
            case "+":
                return new Add(arg[0].diff(name), arg[1].diff(name));
            case "-":
                return new Subtract(arg[0].diff(name), arg[1].diff(name));
            case "*":
                return new Add(
                    new Multiply(arg[0].diff(name), arg[1]),
                    new Multiply(arg[0], arg[1].diff(name))
                );
            case "/":
                return new Divide(
                    new Subtract(
                        new Multiply(arg[0].diff(name), arg[1]),
                        new Multiply(arg[0], arg[1].diff(name))
                    ),
                    new Multiply(arg[1], arg[1])
                );
            case "negate":
                return new Negate(arg[0].diff(name));
            case "sumrec2":
            case "sumrec3":
            case "sumrec4":
            case "sumrec5":
                return arg.reduce((ac, val) => {
                    const newVal = new Multiply(new Const(-1), new Divide(val.diff(name), new Multiply(val, val)));
                    return new Add(ac, newVal);
                }, new Const(0));

            case "hmean2":
            case "hmean3":
            case "hmean4":
            case "hmean5":
                return new Divide(new Const(arg.length), arg.reduce((ac, val) => {
                    const newVal = new Divide(new Const(1), val);
                    return new Add(ac, newVal);
                }, new Const(0))).diff(name);
            case "sqrt":
                return new Divide(new Const(1), new Multiply(new Const(2), new Sqrt(arg[0])))
            case "meansq":
                return getMeansq(arg).diff(name)
            case "rms":
                return new Divide(getMeansq(arg).diff(name), new Multiply(new Sqrt(getMeansq(arg)), new Const(2)));
        }
    }

    this.prefix = () => "(" + this.operation + " " + arg.map(op => op.prefix()).join(" ") + ")";
    this.postfix = () => "(" + arg.map(op => op.postfix()).join(" ") + " " + this.operation + ")";
}

const getMeansq = (arg) => {
    return new Multiply(new Const(1 / arg.length), arg.reduce((ac, val) => {
        return new Add(ac, new Multiply(val, val));
    }, new Const(0)));
}

const createOp = (fun, op) => {
    const result = function (...arg) {
        result.prototype = new Operation;
        this.operation = op;
        this.f = fun;
        Operation.apply(this, arg);
    }
    return result;
}

const Add = createOp((l, r) => l + r, "+");
const Subtract = createOp((l, r) => l - r, "-");
const Multiply = createOp((l, r) => l * r, "*");
const Divide = createOp((l, r) => l / r, "/");
const Negate = createOp((v) => -v, "negate");
const Sqrt = createOp(v => Math.sqrt(v), "sqrt");

const SumRecN = function (...operands) {
    return operands.reduce((acc, val) => acc + (1 / val), 0);
};
const HmeanN = function (...operands) {
    return operands.length / SumRecN(...operands);
};
const MeansqN = function (...operands) {
    return (1 / operands.length) * operands.reduce((acc, val) => acc + Math.pow(val, 2), 0);
};
const RMSN = function (...operands) {
    return Math.sqrt(MeansqN(...operands));
};

const Sumrec2 = createOp(SumRecN, "sumrec2");
const Sumrec3 = createOp(SumRecN, "sumrec3");
const Sumrec4 = createOp(SumRecN, "sumrec4");
const Sumrec5 = createOp(SumRecN, "sumrec5");

const HMean2 = createOp(HmeanN, "hmean2");
const HMean3 = createOp(HmeanN, "hmean3");
const HMean4 = createOp(HmeanN, "hmean4");
const HMean5 = createOp(HmeanN, "hmean5");

const Meansq = createOp(MeansqN, "meansq");
const RMS = createOp(RMSN, "rms");


const OPERATIONS = {
    "+": [2, (...ops) => new Add(ops[0], ops[1])],
    "-": [2, (...ops) => new Subtract(ops[0], ops[1])],
    "*": [2, (...ops) => new Multiply(ops[0], ops[1])],
    "/": [2, (...ops) => new Divide(ops[0], ops[1])],

    "sumrec2": [2, (...ops) => new Sumrec2(...ops)],
    "sumrec3": [3, (...ops) => new Sumrec3(...ops)],
    "sumrec4": [4, (...ops) => new Sumrec4(...ops)],
    "sumrec5": [5, (...ops) => new Sumrec5(...ops)],
    "hmean2": [2, (...ops) => new HMean2(...ops)],
    "hmean3": [3, (...ops) => new HMean3(...ops)],
    "hmean4": [4, (...ops) => new HMean4(...ops)],
    "hmean5": [5, (...ops) => new HMean5(...ops)],
    "negate": [1, (...ops) => new Negate(ops[0])],

    "meansq": [1, (...ops) => new Meansq(...ops)],
    "rms": [1, (...ops) => new RMS(...ops)]
}

let ARBITRARY_OPERATIONS = ["meansq", "rms"]

const parse = function (st) {
    let tokens = st.trim().split(/\s+/);
    let result = [];

    tokens.map((elem) => {
        if (vars.indexOf(elem) !== -1) {
            result.push(new Variable(elem));
        } else if (elem in OPERATIONS) {
            const nOperands = result.splice(result.length - OPERATIONS[elem][0]);
            result.push(OPERATIONS[elem][1](...nOperands));
        } else {
            result.push(new Const(eval(elem)));
        }
    });
    return result.pop();
}

const parsePrefix = (st, postfix = false) => {
    //console.log("PARSE " + st);
    let tokens = st.split(/(\(|\s+|\))/).filter(e => e.trim().length > 0);
    let ix = 0;
    let skobockaBalance = 0;
    let parseMachine = (tokens) => {
        let result = [];
        let numOfOp = 1;
        let op = "";
        for (ix; ix < tokens.length; ix++) {
            if (tokens[ix] === "(") {
                ix++;
                skobockaBalance++;
                result.push(parseMachine(tokens));
                numOfOp--;
            } else if (tokens[ix] === ")") {
                if (op in OPERATIONS && (numOfOp === 0 || ARBITRARY_OPERATIONS.includes(op))) {
                    result.push(OPERATIONS[op][1](...result));
                    skobockaBalance--;
                    break;
                } else {
                    (op === "") ? (() => {
                        throw new MissingOperation(ix)
                    })() : (() => {
                        throw new MissingArgument(op, OPERATIONS[op][0], numOfOp)
                    })();
                }
            } else {
                if (tokens[ix] in OPERATIONS) {
                    postfix ? numOfOp = numOfOp += (OPERATIONS[tokens[ix]][0]) : numOfOp = OPERATIONS[tokens[ix]][0] + 1;
                    op === "" ? op = tokens[ix] : (() => {
                        throw new UnexpectedOperation(tokens[ix], ix, op)
                    })();
                } else if (tokens[ix] === "x" || tokens[ix] === "y" || tokens[ix] === "z") {
                    result.push(new Variable(tokens[ix]));
                } else if (!isNaN(parseInt(tokens[ix]))) {
                    result.push(new Const(eval(tokens[ix])));
                } else {
                    throw new UnknownOperation(tokens[ix], ix);
                }
                numOfOp--;
            }
        }
        if (!ARBITRARY_OPERATIONS.includes(op)) {
            if (!numOfOp) {
                return result.pop();
            } else {
                (op !== "") ? (() => {
                    throw new MissingArgument(op, OPERATIONS[op][0], numOfOp)
                })() : (() => {
                    throw new MissingOperation(ix)
                })();
            }
        } else return result.pop();
    }

    let res = parseMachine(tokens);
    return (!skobockaBalance) ? res : (() => {
        throw new BracketOperation(ix);
    })();
}

const parsePostfix = function (st) {
    return parsePrefix(st, true);
}


//const xw3 = new RMS(new Const(2), new Const(10), new Variable("x"));
//console.log(xw3.evaluate(22, 0, 0));
//const xw0 = new Meansq(new Variable('x'), new Variable('y'), new Variable('z')).postfix();
//const xw1 = new Divide(new Negate(new Variable('x')), new Const(2)).postfix();
//const xw = parsePrefix("(x x)");

//console.log(xw0);
//console.log(xw1);
