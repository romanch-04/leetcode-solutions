class Solution {
    public int reverseDegree(String s) {
        int sum = 0;

        for(int i=0; i<s.length(); i++) {
            int revPos = 'z' - s.charAt(i) + 1;

            int index = i+1;

            sum += revPos * index;
        }
        return sum;
    }
}