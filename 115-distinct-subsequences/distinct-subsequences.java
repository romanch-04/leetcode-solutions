class Solution {
    public int numDistinct(String s, String t) {
        int sl = s.length();
        int tl = t.length();

        int[][] dp = new int[sl + 1][tl + 1];

        // Empty t can always be formed in 1 way:
        // choose nothing from s
        for (int i = 0; i <= sl; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= sl; i++) {

            for (int j = 1; j <= tl; j++) {

                if (s.charAt(i - 1) == t.charAt(j - 1)) {

                    // 1. Use this character
                    // 2. Skip this character
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];

                } else {

                    // Characters don't match, so skip s character
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[sl][tl];
     }
}