class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        
        int n = img1.length;
        int max = 0;

        // Try every possible vertical movement
        for (int dx = -(n - 1); dx <= n - 1; dx++) {

            // Try every possible horizontal movement
            for (int dy = -(n - 1); dy <= n - 1; dy++) {

                int count = 0;

                // Check every cell of img1
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {

                        // We only care about 1s in img1
                        if (img1[i][j] == 1) {

                            // New position after translation
                            int newRow = i + dx;
                            int newCol = j + dy;

                            // Check whether the new position
                            // is inside the image
                            if (newRow >= 0 && newRow < n &&
                                newCol >= 0 && newCol < n) {

                                // If img2 also has 1,
                                // we have an overlap
                                if (img2[newRow][newCol] == 1) {
                                    count++;
                                }
                            }
                        }
                    }
                }

                // Store the best overlap
                max = Math.max(max, count);
            }
        }

        return max;
    }
}