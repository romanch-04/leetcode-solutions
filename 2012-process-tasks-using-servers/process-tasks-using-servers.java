class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {

        int n = servers.length;
        int m = tasks.length;

        // Available servers: weight, index
        PriorityQueue<int[]> free = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0])
                    return Integer.compare(a[0], b[0]);
                return Integer.compare(a[1], b[1]);
            }
        );

        // Busy servers: freeTime, weight, index
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0])
                    return Long.compare(a[0], b[0]);
                if (a[1] != b[1])
                    return Long.compare(a[1], b[1]);
                return Long.compare(a[2], b[2]);
            }
        );

        for (int i = 0; i < n; i++) {
            free.offer(new int[]{servers[i], i});
        }

        int[] ans = new int[m];

        long time = 0;

        for (int j = 0; j < m; j++) {

            // Time must never move backwards
            time = Math.max(time, j);

            // Release all servers that have finished
            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                long[] s = busy.poll();

                free.offer(new int[]{
                    (int) s[1],
                    (int) s[2]
                });
            }

            // If no server is free, wait
            if (free.isEmpty()) {

                time = busy.peek()[0];

                // Release all servers free at this time
                while (!busy.isEmpty() && busy.peek()[0] <= time) {
                    long[] s = busy.poll();

                    free.offer(new int[]{
                        (int) s[1],
                        (int) s[2]
                    });
                }
            }

            // Select the best available server
            int[] s = free.poll();

            int weight = s[0];
            int index = s[1];

            ans[j] = index;

            // Assign task and update server's free time
            busy.offer(new long[]{
                time + tasks[j],
                weight,
                index
            });
        }

        return ans;
    }
}
