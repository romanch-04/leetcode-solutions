class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {

        TreeSet<Integer> available = new TreeSet<>();

        for (int i = 0; i < k; i++) {
            available.add(i);
        }

        // {completionTime, serverIndex}
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> Long.compare(a[0], b[0])
        );

        int[] count = new int[k];
        int max = 0;

        for (int i = 0; i < arrival.length; i++) {

            long time = arrival[i];

            // Release completed servers
            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                long[] finished = busy.poll();
                available.add((int) finished[1]);
            }

            // All servers are busy
            if (available.isEmpty()) {
                continue;
            }

            // Find the next available server
            int preferred = i % k;

            Integer server = available.ceiling(preferred);

            // Wrap around
            if (server == null) {
                server = available.first();
            }

            available.remove(server);

            // Count handled requests
            count[server]++;
            max = Math.max(max, count[server]);

            // Mark server as busy
            busy.offer(new long[]{
                time + load[i],
                server
            });
        }

        // Find busiest servers
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if (count[i] == max) {
                result.add(i);
            }
        }

        return result;
    }
}
