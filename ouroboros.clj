(ns ouroboros
  (:require [clojure.test :refer :all]))

(defn ouroboros
  "Return the ouroboros index of a collection of numbers."
  [coll]
  (let [coll (sort #(compare %2 %1) coll)
        s (apply + coll), n (count coll)]
    (if (= n 1) 1N
        (loop [[v & rest-coll] coll, acc 0, i 0]
          (if (< acc (/ s 2))
            (recur rest-coll (+ acc v) (inc i))
            (let [index-max (int (Math/ceil (/ n 2)))
                  proportion-min (/ index-max n)
                  proportion (/ acc s)
                  delta (/ (- proportion proportion-min)
                           (- 1 proportion-min))
                  index (/ (- i delta)
                           index-max)]
              index))))))

(deftest can-compute-ouroboros-index
  (testing "n = 1"
    (is (= (ouroboros [0]) 100/100))
    (is (= (ouroboros [50]) 100/100))
    (is (= (ouroboros [100]) 100/100)))
  (testing "n = 2"
    (is (= (ouroboros [100, 0]) 0/100))
    (is (= (ouroboros [75, 25]) 50/100))
    (is (= (ouroboros [60, 40]) 80/100))
    (is (= (ouroboros [50, 50]) 100/100)))
  (testing "n = 3"
    (is (= (ouroboros [100 0 0]) 0/100))
    (is (= (ouroboros [90 5 5]) 15/100))
    (is (= (ouroboros [66 33 0]) 50/100))
    (is (= (ouroboros [66 23 10]) 50/100))
    (is (= (ouroboros [50 30 20]) 75/100))
    (is (= (ouroboros [33 33 33]) 100/100)))
  (testing "n = 4"
    (is (= (ouroboros [100 0 0 0]) 0/100))
    (is (= (ouroboros [80 20 0 0]) 20/100))
    (is (= (ouroboros [60 20 10 10]) 40/100))
    (is (= (ouroboros [40 40 10 10]) 70/100))
    (is (= (ouroboros [30 30 20 20]) 90/100))
    (is (= (ouroboros [25 25 25 25]) 100/100)))
  (testing "n = 5"
    (is (= (ouroboros [100 0 0 0 0]) 0/100))
    (is (= (ouroboros [90 10 0 0 0]) 2/24))
    (is (= (ouroboros [80 10 10 0 0]) 4/24))
    (is (= (ouroboros [70 10 10 10 0]) 6/24))
    (is (= (ouroboros [60 10 10 10 10]) 8/24))
    (is (= (ouroboros [50 30 20 0 0]) 10/24))
    (is (= (ouroboros [50 20 10 10 10]) 10/24))
    (is (= (ouroboros [40 30 10 10 10]) 14/24))
    (is (= (ouroboros [30 30 30 5 5]) 16/24))
    (is (= (ouroboros [25 20 20 20 15]) 23/24))
    (is (= (ouroboros [20 20 20 20 20]) 100/100))))

(run-tests)
