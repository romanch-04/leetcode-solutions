import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int sr = 0, sc = 0;
        int litterCount = 0;

        // Assign an ID to every litter cell
        int[][] id = new int[m][n];

        for (int[] row : id) {
            Arrays.fill(row, -1);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (classroom[i].charAt(j) == 'S') {
                    sr = i;
                    sc = j;
                }

                if (classroom[i].charAt(j) == 'L') {
                    id[i][j] = litterCount++;
                }
            }
        }

        int all = (1 << litterCount) - 1;

        // visited[row][col][energy][mask]
        boolean[][][][] visited =
            new boolean[m][n][energy + 1][1 << litterCount];

        Queue<int[]> q = new LinkedList<>();

        // {row, col, remainingEnergy, mask, moves}
        q.offer(new int[]{sr, sc, energy, 0, 0});
        visited[sr][sc][energy][0] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!q.isEmpty()) {

            int[] cur = q.poll();

            int r = cur[0];
            int c = cur[1];
            int e = cur[2];
            int mask = cur[3];
            int moves = cur[4];

            // All litter collected
            if (mask == all) {
                return moves;
            }

            // Cannot move without energy
            if (e == 0) {
                continue;
            }

            for (int d = 0; d < 4; d++) {

                int nr = r + dr[d];
                int nc = c + dc[d];

                // Outside grid
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                // Obstacle
                if (classroom[nr].charAt(nc) == 'X') {
                    continue;
                }

                // Moving costs 1 energy
                int ne = e - 1;

                // Reset energy at R
                if (classroom[nr].charAt(nc) == 'R') {
                    ne = energy;
                }

                // Update litter mask
                int nmask = mask;

                if (classroom[nr].charAt(nc) == 'L') {
                    nmask |= (1 << id[nr][nc]);
                }

                // Visit this state only once
                if (!visited[nr][nc][ne][nmask]) {

                    visited[nr][nc][ne][nmask] = true;

                    q.offer(new int[]{
                        nr, nc, ne, nmask, moves + 1
                    });
                }
            }
        }

        return -1;
    }
}