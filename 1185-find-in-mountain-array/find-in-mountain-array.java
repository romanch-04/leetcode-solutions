/**
 * // This is MountainArray's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface MountainArray {
 *     public int get(int index) {}
 *     public int length() {}
 * }
 */
 
class Solution {
    public int findInMountainArray(int target, MountainArray mountainArr) {
        int peak = peakIndexInMountArr(mountainArr);
        int firstTry = orderAgnosticsBS(mountainArr, target, 0, peak);
        if(firstTry != -1) {
            return firstTry;
        }

        //try to search in second half
        return orderAgnosticsBS(mountainArr, target, peak+1, mountainArr.length()-1);
    }

    // int search(int t, int[]arr) {

    // }

    public int peakIndexInMountArr(MountainArray arr) {
        int start = 0;
        int end = arr.length()-1;

        while(start < end) {
            int mid = start + (end - start) / 2;

            if(arr.get(mid) > arr.get(mid+1)) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }

    static int orderAgnosticsBS(MountainArray arr, int t, int start, int end) {
		
		boolean isAsc = arr.get(start) <= arr.get(end);
		
		while(start <= end) {
			int mid = start + (end - start) / 2;
			
			if(arr.get(mid) == t) {
				return mid;
			} 
			
			if(isAsc) {
				if(t < arr.get(mid)) {
					end = mid - 1;
				} else {
					start = mid + 1;
				} 
			} else {
				if(t > arr.get(mid)) {
					end = mid - 1;
				} else {
					start = mid + 1;
				}
			}
		}
		return -1;
	}
}