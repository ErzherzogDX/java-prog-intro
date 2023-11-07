package search;

//Pre: given string args[], args.length > 0, with parseable integers, first int is x, the rest of them
//     are sorted by non-strict descending.
public class BinarySearch {

    //Pre: given int[] arr, arr.length > 0, elements
    //     are sorted by non-strict descending.
    public static int iterativeSearch(int[]arr, int num){
        int l = -1;
        int r = arr.length;

        // Pre (Invariant): arr.length > 0 &&
        //                  r - l > 1 &&
        //                  (l == -1 || (l > -1) && (arr[l] > num)) &&
        //                  (r == arr.length || (r < arr.length) && (arr[r] <= num))
        //
        while(l < r - 1){
            //Pre: Inv && (2 * mid' == l + r || 2 * mid' + 1 == l+r)
            int mid = (l+r) / 2;
            //Post: Inv && mid == mid'

            //Pre: Inv && mid == mid'
            if(arr[mid] > num){
                //Pre: Inv && mid == mid' && arr[mid] > num
                l = mid;
                //Post: Inv && l == mid' && arr[mid] > num
            }
            else {
                //Pre: Inv && mid == mid' && arr[mid] <= num
                r = mid;
                //Pre: Inv && r == mid' && arr[mid] <= num
            }
            //Post: Inv
        }
        // Post (Invariant): arr.length > 0 &&
        //                   r - l <= 1 &&
        //                   (l == -1 || (l > -1) && (arr[l] > num)) &&
        //                   (r == arr.length || (r < arr.length) && (arr[r] <= num))
        return r;
    }
    //Post: r is minimum value of i, which arr[i] <= x.

    //Pre: given int[] arr, arr.length > 0, elements
    //     are sorted by non-strict descending. l >= -1 && 0 <= r <= arr.length()
    public static int recursiveSearch(int[]arr, int num, int l, int r){
            if (r - l <= 1) {
                //Pre: Inv && r-l <= 1
                return r;
            }

            //Pre: 2 * mid' == l + r || 2 * mid' + 1 == l+r
            int mid = (l+r) / 2;
            //Post: mid == mid'

            if(arr[mid] > num){
                //Pre: arr[mid] > num && Inv
                return recursiveSearch(arr, num, mid, r);
            }
            //Pre: arr[mid] <= num && Inv
            else return recursiveSearch(arr, num, l, mid);
    }
    //Post: r is minimum value of i, which arr[i] <= x.

    public static void main(String[] args) {
        //Pre: given string args[], args.length > 0, with parseable integers, first int is x, the rest of them
        //     are sorted by non-strict descending, args[0] = x'
        int x = Integer.parseInt(args[0]);
        //Post: given string args[], args.length > 0, with parseable integers, first int is x, the rest of them
        //      are sorted by non-strict descending, x = x'


        //Pre: given string args[], args.length > 1
        int[] arr = new int[args.length - 1];
        //Post: arr.length == args.length - 1

        //Pre: i < args.length && args.length >= 2 && args[] contains parseable integers
        for(int i = 1; i < args.length; i++){
            arr[i-1] = Integer.parseInt(args[i]);
        }
        //Post: i == args.length && args.length >= 2 && args[] contains parseable integers

      System.out.println(iterativeSearch(arr, x));
        //System.out.println(recursiveSearch(arr, x, -1, arr.length));
    }

}
//Post: r' is minimum value of i from [1..args.length-1] which args[i] <= args[0].
