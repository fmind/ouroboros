#!/usr/bin/env python3

from ouroboros import ouroboros

import unittest


class OuroborosTests(unittest.TestCase):
    """Test the output of Ouroboros on arrays of various size"""

    # array sums add to 100 for convenience and intuition
    cases = [
        # n = 0
        ([], 0, 0),
        # n = 1
        ([100], 0, 0),
        # n = 2
        ([100, 0], 0.0, 1),
        ([75, 25], 0.5, 1),
        ([60, 40], 0.8, 1),
        ([50, 50], 1, 1),
        # n = 3
        ([100, 0, 0], 0, 1),
        ([90, 5, 5], 0.15, 1),
        ([67, 23, 10], 0.49, 1),
        ([65, 35, 0], 0.52, 1),
        ([50, 30, 20], 0.75, 1),
        ([34, 33, 33], 0.99, 2),
        # n = 4
        ([100, 0, 0, 0], 0.0, 1),
        ([80, 20, 0, 0], 0.20, 1),
        ([60, 20, 10, 10], 0.4, 1),
        ([40, 40, 10, 10], 0.7, 2),
        ([49, 49, 1, 1], 0.52, 2),
        ([30, 30, 20, 20], 0.9, 2),
        ([25, 25, 25, 25], 1, 2),
        # n = 5
        ([100, 0, 0, 0, 0], 0.0, 1),
        ([80, 20, 0, 0, 0], 0.17, 1),
        ([50, 30, 10, 10, 0], 0.42, 1),
        ([30, 25, 20, 20, 5], 0.71, 2),
        ([25, 25, 25, 15, 10], 0.75, 2),
        ([25, 20, 20, 20, 15], 0.96, 3),
        ([20, 20, 20, 20, 20], 1, 3),
    ]

    def _gini(self, array):
        """Compute the Gini index of a list of frequencies"""
        u = 0
        t = sum(array)

        for x in array:
            u += (x/t)**2

        return 1 - u

    def test_standalone(self):
        for array, index, indice in self.cases:
            idx, idc = ouroboros(array, True)
            self.assertAlmostEqual(idx, index, 2, msg="Array: {0}".format(array))
            self.assertEqual(idc, indice, msg="Array: {0}".format(array))

    def test_against_gini(self):
        print("{0:<15} || {1:<s} | {2:<4s}".format("ARRAY", "OURO", "GINI"))
        for array, _, _ in self.cases:
            orb = ouroboros(array)
            gin = self._gini(array)
            arr = ','.join([str(a) for a in array])
            print("{0:<15} || {1:<4.2f} | {2:<4.2f}".format(arr, orb, gin))


if __name__ == '__main__':
    unittest.main()
