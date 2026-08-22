class Solution {
    public boolean checkDivisibility(int n) {
        int t = n;
        int pow = 1;
        int sum = 0;
            while(n!=0) {
                int r = n%10;
                sum += r;
                pow *= r;
                n/=10;
            }
            int div = sum + pow;
            return t % div == 0;
    }
}