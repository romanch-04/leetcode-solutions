class Solution {
    public boolean hasValidPath(char[][] grid) {

        int  m = grid.length;
        int n = grid[0].length;

        // valid parenthesis string must have even length
        if((m+n-1) % 2 != 0) {
            return false;
        }

        // first character cannot be ')'
        if(grid[0][0] == ')') {
            return false;
        }

        // last character cannot be '('
        if(grid[m-1][n-1] == '(') {
            return false;
        }

        // dp[i][j][balance]
        boolean[][][] dp = new boolean[m][n][m+n];

        // starting cell '(' gives balance = 1
        dp[0][0][1] = true;

        for(int i=0; i<m; i++) {
            for(int j=0; j<n; j++) {

                //skip starting cell
                if(i == 0 && j == 0) {
                    continue;
                }

                for(int balance=0; balance<m+n; balance++) {

                    // previous cell can be from top or left
                    boolean possible = false;

                    if(i>0 && dp[i-1][j][balance]) {
                        possible = true;
                    }

                    if(j>0 && dp[i][j-1][balance]) {
                        possible = true;
                    }

                    if(!possible) {
                        continue;
                    }

                    int newBalance;

                    if(grid[i][j] == '(') {
                        newBalance = balance + 1;
                    } else {
                        newBalance = balance - 1;
                    }

                    // Balance can never become negative 
                    if(newBalance >= 0 && newBalance < m+n) {
                        dp[i][j][newBalance] =  true;
                    }
                }
            }
        }

        // valid string must finish with balance 0
        return dp[m-1][n-1][0];
    }
}