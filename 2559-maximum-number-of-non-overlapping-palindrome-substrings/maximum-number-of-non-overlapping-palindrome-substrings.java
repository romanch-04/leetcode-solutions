class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();

        //palindrome[i][j] = true if s[i...j] is a palindrome
        boolean[][] palindrome = new boolean[n][n];

        //Building palindrome table
        for(int i=n-1; i>=0; i--) {
            for(int j=i; j<n; j++) {

                if(s.charAt(i) == s.charAt(j)) {
                    if(j-i <= 1) {
                        palindrome[i][j] = true;
                    } else {
                        palindrome[i][j] = palindrome[i+1][j-1];
                    }
                }
            }
        }

        //dp[i] = maximum palindromes using first i characters
        
        int[] dp = new int[n+1];

        for(int i=1; i<=n; i++) {

            //don't select a palindrome endinf at i-1
            dp[i] = dp[i-1];

            //try every posible stating position
            for(int j=0; j<=i-k; j++) {
                if(palindrome[j][i-1]) {
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }
            }
            
        }
        return dp[n];
    }
}