class Solution {
    public boolean isSameAfterReversals(int num) {

        int rev1 = 0;
        int t = num;

        while(t != 0) {
            int r = t%10;
            rev1 = rev1*10+r;
            t/=10;
        }

        int rev2 = 0;
        t = rev1;

        while(t != 0) {
            int r = t%10;
            rev2 = rev2*10+r;
            t/=10;
        }

        if(num == rev2) {
            return true;
        }

        return false;
    }
}