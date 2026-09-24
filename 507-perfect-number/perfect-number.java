class Solution {
    public boolean checkPerfectNumber(int num) {
        int div = 0;
        int sum = 0;
        int t = num;

        for(int i=1; i<t; i++) {
            if(t%i == 0) {
                sum +=i;
            }
        }

        if(sum == t) {
            return true;
        }
        return false;
    }
}