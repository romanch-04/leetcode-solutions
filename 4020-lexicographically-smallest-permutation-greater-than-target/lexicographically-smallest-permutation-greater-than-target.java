class Solution {
    public String lexGreaterPermutation(String s, String target) {
         int n = s.length();

        // Count how many times each character appears in s
        int[] count = new int[26];

        for (char ch : s.toCharArray()) {
            count[ch - 'a']++;
        }

        // We will move from right to left.
        // count[] represents the characters still available.
        for (int i = 0; i < n; i++) {

            int ch = target.charAt(i) - 'a';

            // Use target[i] if it is available.
            // This allows us to keep the prefix the same.
            if (count[ch] > 0) {
                count[ch]--;
            } else {
                // target[i] cannot be used.
                // So we cannot keep this position the same.
                break;
            }

            // If we reach here, the prefix target[0...i] is possible.
            // We will try to change a position from right to left.
        }

        /*
         * The above approach alone is not enough because
         * we need to try changing the RIGHTMOST possible position.
         *
         * So we rebuild the counts and process from right to left.
         */

        // Reset character frequencies
        count = new int[26];

        for (char ch : s.toCharArray()) {
            count[ch - 'a']++;
        }

        // Try every position from right to left
        for (int i = n - 1; i >= 0; i--) {

            // First, keep target[i] available for this position.
            // We will use it as part of the prefix if possible.
            int current = target.charAt(i) - 'a';

            /*
             * Before trying this position, we need all characters
             * before i to match target.
             *
             * Instead of rebuilding everything, we check the prefix
             * using a temporary count.
             */
            int[] remain = count.clone();

            boolean possible = true;

            // Check whether target[0 ... i-1] can be created
            for (int j = 0; j < i; j++) {

                int x = target.charAt(j) - 'a';

                if (remain[x] == 0) {
                    possible = false;
                    break;
                }

                remain[x]--;
            }

            if (!possible) {
                continue;
            }

            /*
             * Now we have successfully created the prefix.
             *
             * We need a character GREATER than target[i].
             *
             * Start from target[i] + 1 so that the final
             * string becomes greater than target.
             */
            for (int c = current + 1; c < 26; c++) {

                if (remain[c] == 0) {
                    continue;
                }

                // Start with the unchanged prefix
                StringBuilder ans =
                    new StringBuilder(target.substring(0, i));

                // Put the smallest possible character
                // that is greater than target[i]
                ans.append((char) ('a' + c));

                // We used this character
                remain[c]--;

                /*
                 * Put all remaining characters in sorted order.
                 *
                 * Sorting the remaining part makes the complete
                 * string as small as possible.
                 */
                for (int x = 0; x < 26; x++) {

                    while (remain[x] > 0) {
                        ans.append((char) ('a' + x));
                        remain[x]--;
                    }
                }

                return ans.toString();
            }

            /*
             * We couldn't make the string greater at position i.
             *
             * Move one position to the left and try again.
             */
        }

        // No valid string greater than target exists
        return "";
    }
}