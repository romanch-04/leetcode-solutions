class Solution {
    public boolean divisorGame(int n) {
        
        if(n > 0 && n <= 1000) {
            if(n % 2 == 0) {
                return true;
            }
        }
        return false;
    }
}