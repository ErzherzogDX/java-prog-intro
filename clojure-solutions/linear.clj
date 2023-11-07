(defn check-vectors? [s op] (every? (fn [v] (and (vector? v) (every? op v))) s) )
(defn check-sizes? [s] (every? (partial = (count (first s))) (map count s)))


(defn v-op [op & s]
  {:pre [(check-vectors? s number?)
         (check-sizes? s)]}
  (apply mapv op s))

(def v+ (partial v-op +))
(def v- (partial v-op -))
(def v* (partial v-op *))
(def vd (partial v-op /))

(defn m-op [op & ms]
  {:pre [(check-vectors? ms vector?)
         (check-sizes? ms)]}
  (apply mapv (partial v-op op) ms))

(def m+ (partial m-op +))
(def m- (partial m-op -))
(def m* (partial m-op *))
(def md (partial m-op /))

(defn transpose [s]
  (apply mapv vector s))

(defn scalar [& s] (apply + (apply v* s)))

(defn m*v [m v] (mapv (fn [v1] (scalar v1 v)) m))

(defn v*s [v & s]
  (mapv (partial * (apply * s)) v))

(defn m*s [m & s]
  {:pre [(every? number? s)]}
  (mapv (fn [v] (v*s v (apply * s))) m))


(defn m*m
  ([] 0)
  ([x] x)
  ([m1 m2] {:pre [(check-vectors? [m1 m2] vector?)]}
   (transpose (mapv (fn [v2] (m*v m1 v2)) (transpose m2))))
  ([m1 m2 & more] (reduce m*m (m*m m1 m2) more)))

(defn vect
  ([] 1)
  ([first] first)
  ([first second]
   {:pre [(and (vector? first) (vector? second)
               (check-vectors? [first second] number?)
               (check-sizes? [first second]))]}

   (vector (- (* (nth first 1) (nth second 2)) (* (nth second 1) (nth first 2)))
           (- (* (nth first 2) (nth second 0)) (* (nth second 2) (nth first 0)))
           (- (* (nth first 0) (nth second 1)) (* (nth second 0) (nth first 1)))))
  ([first second & more] (reduce vect (vect first second) more)))

(defn tBroadcast [op a b] {:pre [(or (number? a) (number? b) (== (count a) (count b)))]}
  (if (number? a)
    (if (number? b)
      (op a b)
      (mapv (fn [n1] (tBroadcast op a n1))  b))
    (if (number? b)
      (mapv (fn [n1] (tBroadcast op n1 b)) a)
      (mapv (fn [n1 n2] (tBroadcast op n1 n2)) a b))))

(defn t-op [op first] (fn ([ar] (tBroadcast op first ar))
                        ([ar & args] (reduce (fn [a1 a2] (tBroadcast op a1 a2)) ar args))))

(def tb* (t-op * 1))
(def tbd (t-op / 1))
(def tb+ (t-op + 0))
(def tb- (t-op - 0))

;;test-part

(defn m*test [& s]
  (every? #(and (vector? %) (every? number? %)) s))

(defn m*test2 [& s]
  (every? (partial = (count (first s))) (map count s)))

(defn n3 [s] (number? s))

;;(println (n3 [21]))

;;(println (m*test2 [1 2 3] [1 2 3]))
;;(println (m*test (vector 0.0 0.0 0.0) (vector 0.0 4.0 2.0)))
;;(println (m*test (vector 0.0 0.0 0.0) (vector (vector 0.0 0.0 0.0) (vector 0.0 0.0 0.0) (vector 0.0 0.0 0.0))))
;;(println (scalar [1 2 3] [4 5 6]))
;;(println (m*m [[2 1 2] [3 4 5] [3 7 8]]  [[2 1 2] [3 4 5] [3 7 8]] [[2 1 2] [3 4 5] [3 7 8]]))
