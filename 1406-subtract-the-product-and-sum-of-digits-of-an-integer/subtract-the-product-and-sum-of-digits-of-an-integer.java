class Solution {
    public int subtractProductAndSum(int n) {
        int sum = 0;
        int prod = 1;

        while(n != 0) {
            int r = n%10;
            sum += r;
            prod *= r;
            n/=10;
        }
        int result = prod - sum;
        return result;
    }
}