class Solution {
    public int projectionArea(int[][] grid) {
        int n = grid.length;
        int area = 0;

        for(int i=0; i<n; i++) {

            int rowMax = 0;
            int colMax = 0;

            for(int j=0; j<n; j++) {

                if (grid[i][j] > 0) {
                    area++;
                }

                rowMax = Math.max(rowMax, grid[i][j]);

                colMax = Math.max(colMax, grid[j][i]);

            }

            area += rowMax;

            area += colMax;
        }

        return area;
    }
}