class Solution {
    public static int firstMissingPositive(int[] arr) {
    	int i=0;
    	while(i < arr.length) {
    		int correct = arr[i]-1;
    		if(arr[i] > 0 && arr[i] < arr.length && arr[i] != arr[correct]) {
    			swap(arr, i, correct);
    		} else {
    			i++;
    		}
    	}
    		
    	// just find missing number
        for(int  index = 0; index < arr.length; index++) {
            if(arr[index] != index+1) {
                 return index+1;
            }
        }
        return arr.length+1;
    }
    
    static void swap(int[] arr, int f, int s) {
        int temp = arr[f];
        arr[f] = arr[s];
        arr[s] = temp;
    }
}