class Solution {
    public int totalNumbers(int[] digits) {
         // Count how many times each digit is available
        int[] count = new int[10];

        for (int digit : digits) {
            count[digit]++;
        }

        int answer = 0;

        // Hundreds digit
        for (int a = 1; a <= 9; a++) {

            // We don't have this digit
            if (count[a] == 0) {
                continue;
            }

            count[a]--;

            // Tens digit
            for (int b = 0; b <= 9; b++) {

                if (count[b] == 0) {
                    continue;
                }

                count[b]--;

                // Ones digit must be even
                for (int c = 0; c <= 8; c += 2) {

                    if (count[c] > 0) {
                        answer++;
                    }
                }

                // Give tens digit back
                count[b]++;
            }

            // Give hundreds digit back
            count[a]++;
        }

        return answer;
    }
}