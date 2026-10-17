class Solution {
    public int minSwaps(int[] nums) {
        int n = nums.length;

        // Store each number with its original index
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        // Sort indices by digit sum, then by number value
        Arrays.sort(indices, (a, b) -> {
            int sumA = digitSum(nums[a]);
            int sumB = digitSum(nums[b]);

            if (sumA != sumB) {
                return Integer.compare(sumA, sumB);
            }
            return Integer.compare(nums[a], nums[b]);
        });

        // target[i] = sorted position of the element originally at i
        int[] target = new int[n];
        for (int sortedIndex = 0; sortedIndex < n; sortedIndex++) {
            target[indices[sortedIndex]] = sortedIndex;
        }

        // Count cycles
        boolean[] visited = new boolean[n];
        int swaps = 0;

        for (int i = 0; i < n; i++) {
            if (visited[i] || target[i] == i) {
                continue;
            }

            int cycleLength = 0;
            int current = i;

            while (!visited[current]) {
                visited[current] = true;
                current = target[current];
                cycleLength++;
            }

            swaps += cycleLength - 1;
        }

        return swaps;
    }

    private int digitSum(int num) {
        int sum = 0;

        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }

        return sum;
    }
}