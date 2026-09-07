class Solution {
    public int distinctSubseqII(String s) {
        int n = s.length();
        int MOD = 1000000007;

        // dp[i] = number of distinct subsequences
        // including empty subsequence using first i characters
        int[] dp = new int[n + 1];

        dp[0] = 1;

        // last[c] = dp value before the previous occurrence of character c
        int[] last = new int[26];

        for (int i = 1; i <= n; i++) {

            int c = s.charAt(i - 1) - 'a';

            // Double the previous subsequences:
            // 1. Don't use current character
            // 2. Use current character
            dp[i] = (2 * dp[i - 1]) % MOD;

            // Remove duplicates caused by previous occurrence
            dp[i] = (dp[i] - last[c] + MOD) % MOD;

            // Save current dp value for future duplicate removal
            last[c] = dp[i - 1];
        }

        // Remove empty subsequence
        return (dp[n] - 1 + MOD) % MOD;
    }
}