package search;

//Pre: given string args[], args.length > 0, with parseable integers, first int is x, the rest of them
//     are sorted by non-strict increasing.
public class BinarySearchSpan {

    //Pre: given int[] arr, arr.length > 0, elements
    //     are sorted by non-strict descending, left == -1 && right == -1

    //Pre: true
    final static int INT_MAX = 2147483647;
    //Post: INT_MAX == 2147483647

    public static int leftSearchIterative(int num, int[] arr) {
        //Pre: true
        int l = 0, r = arr.length - 1;
        // l == 0 && r == len - 1

        // Pre (Invariant): arr.length > 0 &&
        //                  l <= r &&
        //                  (l == 0 || (l > 0) && (arr[l] <= num)) &&
        //                  (r == arr.length || (r < arr.length) && (arr[r] > num))
        //
        while (l <= r) {
            //Pre: Inv && (2 * mid' == l + r || 2 * mid' + 1 == l+r)
            int mid = (l + r) / 2;
            //Post: Inv && mid == mid'

            //Pre: Inv && mid == mid'
            if (arr[mid] >= num) {
                //Pre: Inv && mid == mid' && arr[mid] >= num
                r = mid - 1;
                //Post: Inv && r + 1 == mid' && arr[mid] >= num
            } else {
                //Pre: Inv && mid == mid' && arr[mid] < num
                l = mid + 1;
                //Pre: Inv && l-1 == mid' && arr[mid] < num
            }
            //Post: Inv
        }
        // Post (Invariant): arr.length > 0 &&
        //                   l > r &&
        //                   (l == 0 || (l > 0) && (arr[l] <= num)) &&
        //                   (r == arr.length || (r < arr.length) && (arr[r] > num))
        //
        return l;
    }

    public static int leftSearchRecursive(int num, int[] arr, int l, int r) {
        // Pre (Invariant): arr.length > 0 &&
        //                  l <= r &&
        //                  (l == 0 || (l > 0) && (arr[l] <= num)) &&
        //                  (r == arr.length || (r < arr.length) && (arr[r] > num))
        //
        while (l <= r) {
            //Pre: Inv && (2 * mid' == l + r || 2 * mid' + 1 == l+r)
            int mid = (l + r) / 2;
            //Post: Inv && mid == mid'

            //Pre: Inv && mid == mid'
            if (arr[mid] >= num) {
                leftSearchRecursive(num, arr, l, mid - 1);

                //Pre: Inv && mid == mid' && arr[mid] >= num
                r = mid - 1;
                //Post: Inv && r + 1 == mid' && arr[mid] >= num
            } else {
                leftSearchRecursive(num, arr, mid + 1, r);

                //Pre: Inv && mid == mid' && arr[mid] < num
                l = mid + 1;
                //Pre: Inv && l-1 == mid' && arr[mid] < num
            }
            //Post: Inv
        }
        // Post (Invariant): arr.length > 0 &&
        //                   l > r &&
        //                   (l == 0 || (l > 0) && (arr[l] <= num)) &&
        //                   (r == arr.length || (r < arr.length) && (arr[r] > num))
        //
        return l;
    }

    public static int[] iterativeSearch(int num, int[] arr) {
        //Pre: arr.length > 0
        int left = leftSearchIterative(num, arr);
        int right;

        if (num != INT_MAX) {
            right = leftSearchIterative(num + 1, arr);
        } else {
            right = arr.length;
        }
        //Pre: l >= 0 && right - left <= len
        int[] res = {left, right - left};
        //Post: Created two-element array res, contains left and the number of occurrences of num in the array arr
        return res;
    }
    //Post: res is two-element array, which res[0] is start index of need range, res[1] is length of this range.


    //Pre: given int[] arr, arr.length > 0, elements
    //     are sorted by non-strict increasing. left >= 0 && 0 <= right < arr.length()
    public static int[] recursiveSearch(int num, int[] arr) {
        //Pre: arr.length > 0
        int left = leftSearchRecursive(num, arr, 0, arr.length - 1);

        //Pre: true
        int right;
        //Post: right is undefined integer value.

        //Pre: num is integer value
        if (num != INT_MAX) {
            right = leftSearchRecursive(num + 1, arr, 0, arr.length - 1);
        } else {
            //Pre: arr.length > 0
            right = arr.length;
            //Post: right' == arr.lenghth
        }
        //Post: right is the result of the "right-side" binary search

        //Pre: l >= 0 && right - left <= len
        int[] res = {left, right - left};
        //Post: Created two-element array res, contains left and the number of occurrences of num in the array arr
        return res;
    }
    //Post: res is two-element array, which res[0] is start index of need range, res[1] is length of this range.


    public static void main(String[] args) {
        //Pre: given string args[], args.length > 0, with parseable integers, first int is x, the rest of them
        //     are sorted by non-strict increasing, args[0] = x'
        int x = Integer.parseInt(args[0]);
        //Post: given string args[], args.length > 0, with parseable integers, first int is x, the rest of them
        //      are sorted by non-strict increasing, x = x'

        //Pre: true
        int summ = 0;
        //Post: summ == 0


        //Pre: given string args[], args.length > 1
        int[] arr = new int[args.length - 1];
        //Post: arr.length == args.length - 1

        //Pre: i < args.length && args.length >= 2 && args[] contains parseable integers
        for (int i = 1; i < args.length; i++) {
            //Pre: args[i] is parseable to integer value
            arr[i - 1] = Integer.parseInt(args[i]);
            //Post: arr[i-1] == args[i]
            summ += (arr[i - 1]);
        }
        //Post: i == args.length && args.length >= 2 && args[] contains parseable integers


        //Pre: true
        int[] result;
        //Post: integer array "result"

        //Pre: summ is integer value
        if (summ % 2 == 0) {
            //result = recursiveSearch(x, arr, 0, arr.length - 1);
            result = recursiveSearch(x, arr);
        } else result = iterativeSearch(x, arr);
        //Post: two-element array result which res[0] is start index of need range, res[1] is length of this range.

        System.out.println(result[0] + " " + result[1]);
    }
}
//Post: Array "result" which res[0] is start index of need range, res[1] is length of this range.
