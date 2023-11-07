;;  ---------------------------- TASK 10 -----------------------------------

(def constant constantly)
(defn variable [name] (fn [vars] (vars name)))
(defn op-builder [op] (fn [& args] (fn [variables] (apply op (mapv #(% variables) args)))) )


(defn squarefication [& sq]
  (mapv #(Math/pow % 2) sq))
(defn square-root [x]
  (Math/sqrt x))

(defn div-machine ([exp] (/ 1.0 (double exp)))
  ([a & b] (reduce (fn [e1 e2] (/ e1 (double e2))) a b)))
(defn meansq-machine [& args]
  (div-machine (apply + (apply squarefication args)) (count args)))
(defn rms-machine [& args] (square-root (apply meansq-machine args)))


(def multiply (op-builder *))
(def divide (op-builder div-machine))
(def add (op-builder +))
(def subtract (op-builder -))

(def meansq (op-builder meansq-machine))
(def rms (op-builder rms-machine))
(def negate subtract)

(def op-list {"cnst" constant, "var" variable, '+ add, '- subtract, '* multiply, '/ divide, 'negate negate, 'rms rms, 'meansq meansq})

(defn parse-machine [exp, op]
  (cond
    (number? exp) ((op "cnst") exp)
    (seq? exp) (apply (op (first exp)) (map #(parse-machine % op) (rest exp)))
    :else ((op "var") (str exp))))

(defn parseFunction [str] (parse-machine (read-string str) op-list))

;;(println ((parseFunction "(- (* 2 x) 3)") {"x" 2}))


;;  ---------------------------- TASK 11 -----------------------------------

(defn exp-machine [& args]
  (Math/exp (first args)))

(defn sumexp-machine [& args]
  (reduce + (map #(Math/exp %) args)))

(defn lse-machine [& args]
  (Math/log (apply sumexp-machine args)))


(definterface IExpression
  (toString [])
  (evaluate [varlist])
  (diff [var])
  )

(declare Constant)
(declare ZERO ONE NEG-ONE)

(deftype IConstable [val]
  IExpression
  (toString [this] (str val))
  (evaluate [this varlist] val)
  (diff [this var] ZERO)
  )

(defn Constant [val] (IConstable. val))
(def ZERO (Constant 0))
(def ONE (Constant 1))
(def NEG-ONE (Constant -1))

(deftype IVariable [name]
  IExpression
  (toString [this] name)
  (evaluate [this varlist] ((fn [varlist] (varlist name)) varlist))
  (diff [this var]
    (cond
      (= name var) ONE
      :else ZERO))
  )

(defn Variable [name] (IVariable. name))

(defn ng [arg] (- arg))
(declare Add Subtract Multiply Divide Negate Exponent Sumexp LSE)
(defn merge [ar1 ar2] (distinct (concat ar1 ar2)))

(deftype Iop [args op ts-symbol dif]
  IExpression
  (toString [this] (str "(" ts-symbol " " (clojure.string/join " " (map #(.toString %) args)) ")"))
  (evaluate [this varlist] (apply op (map (fn [operand] (.evaluate operand varlist)) args)))
  (diff [this var] (dif var))
  )

(defn Add [& args] (Iop. args + "+" (fn [var] (apply Add (map (fn [operand] (.diff operand var)) args)))))
(defn Subtract [& args] (Iop. args - "-" (fn [var] (apply Subtract (map (fn [operand] (.diff operand var)) args)))))
(defn Multiply [& args] (Iop. args * "*" (fn [var] (apply Add (map (fn [operand] (Divide (Multiply (apply Multiply args) (.diff operand var)) operand) ) args)))))
(defn Divide [& args] (Iop. args div-machine "/" (fn [var] (cond
                                                             (= (count args) 1) (Negate (Divide (.diff (first args) var) (Multiply (first args) (first args))))
                                                             :else                    (Add (Multiply (Divide (apply Divide args) (first args)) (.diff (first args) var))
                                                                                           (Multiply (Multiply (apply Divide (merge [ONE] args)) (first args)) (.diff (first args) var))
                                                                                           (apply Add (map (fn [operand] (Negate (Multiply (Divide (apply Divide args) operand) (.diff operand var)))) args))
                                                                                           ))) ))
(defn Negate [& arg] (Iop. arg ng "negate" (fn [var] (apply Negate (map (fn [operand] (.diff operand var)) arg)))   ))
(defn Exponent [& arg] (Iop. arg exp-machine "exponent" (fn [var] (Multiply ((fn [operand] (.diff operand var)) (first arg)) (apply Exponent arg))) ))
(defn Sumexp [& arg] (Iop. arg sumexp-machine "sumexp" (fn [var] (apply Add(map (fn [operand] (.diff (Exponent operand) var)) arg)))))
(defn LSE [& arg] (Iop. arg lse-machine "lse" (fn [var] (Divide (.diff (apply Sumexp arg) var) (apply Sumexp arg)))))

(defn evaluate [expr varlist] (.evaluate expr varlist))
(defn toString [expr] (.toString expr))
(defn diff [expr var] (.diff expr var))

(def op-list2 {"cnst" Constant, "var" Variable, '+ Add, '- Subtract, '* Multiply, '/ Divide, 'negate Negate, 'sumexp Sumexp, 'lse LSE})
(defn parseObject [str] (parse-machine (read-string str) op-list2))