class Solution {
    public int maximumSaleItems(int[][] items, int budget) {

        int n = items.length;

        // Step 1: Count frequency of each factor
        int[] freq = new int[n + 1];

        for (int i = 0; i < n; i++) {
            freq[items[i][0]]++;
        }

        // Step 2: Count how many factors are divisible by each factor
        int[] divisible = new int[n + 1];

        for (int i = 1; i <= n; i++) {

            for (int j = i; j <= n; j += i) {
                divisible[i] += freq[j];
            }
        }

        // Step 3: Find minimum price
        int minPrice = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            minPrice = Math.min(minPrice, items[i][1]);
        }

        // Step 4: Sort item indices by increasing price
        Integer[] order = new Integer[n];

        for (int i = 0; i < n; i++) {
            order[i] = i;
        }

        Arrays.sort(order, (a, b) ->
            Integer.compare(items[a][1], items[b][1])
        );

        long remaining = budget;
        long answer = 0;

        // Step 5: Buy bonus-producing copies first
        for (int index : order) {

            int factor = items[index][0];
            int price = items[index][1];

            // No benefit in buying bonus copies at this price
            if (price >= 2L * minPrice) {
                break;
            }

            // Exclude the item itself
            int freeCopies = divisible[factor] - 1;

            // Maximum bonus-producing copies we can afford
            long canBuy = Math.min(
                (long) freeCopies,
                remaining / price
            );

            // Each purchase gives 1 purchased + 1 free item
            answer += 2 * canBuy;

            remaining -= canBuy * price;

            // Cannot afford another copy at this or a higher price
            if (canBuy < freeCopies) {
                break;
            }
        }

        // Step 6: Spend remaining budget on cheapest item
        answer += remaining / minPrice;

        return (int) answer;
    }
}
