
class Solution {
    public String[] createGrid(int m, int n) {
        String[] grid = new String[m];

        // Block every cell initially
        char[][] arr = new char[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = '#';
            }
        }

        // Make the top row free
        for (int j = 0; j < n; j++) {
            arr[0][j] = '.';
        }

        // Make the last column free
        for (int i = 0; i < m; i++) {
            arr[i][n - 1] = '.';
        }

        // Convert each row into a String
        for (int i = 0; i < m; i++) {
            grid[i] = new String(arr[i]);
        }

        return grid;
    }
}
