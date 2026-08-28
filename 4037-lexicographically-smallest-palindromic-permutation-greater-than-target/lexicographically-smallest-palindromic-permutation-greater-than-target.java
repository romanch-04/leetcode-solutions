class Solution {

    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();

        // Count characters
        int[] count = new int[26];

        for (char ch : s.toCharArray()) {
            count[ch - 'a']++;
        }

        // Check palindrome possibility
        int odd = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {

            if (count[i] % 2 != 0) {
                odd++;
                middle = (char) ('a' + i);
            }
        }

        // More than one odd frequency -> no palindrome possible
        if (odd > 1) {
            return "";
        }

        // Build character counts for left half
        int halfLength = n / 2;

        int[] halfCount = new int[26];

        for (int i = 0; i < 26; i++) {
            halfCount[i] = count[i] / 2;
        }

        /*
         * -------------------------------------------------------
         * STEP 1:
         * Try to make the LEFT HALF exactly equal to target's
         * left half.
         *
         * This must be checked FIRST.
         *
         * Example:
         *
         * s      = aabb
         * target = abaa
         *
         * left = ab
         * palindrome = abba
         *
         * abba > abaa
         *
         * Therefore answer is abba.
         * -------------------------------------------------------
         */

        int[] remainEqual = halfCount.clone();

        StringBuilder equalLeft = new StringBuilder();

        boolean canMakeEqualLeft = true;

        for (int i = 0; i < halfLength; i++) {

            int ch = target.charAt(i) - 'a';

            if (remainEqual[ch] == 0) {
                canMakeEqualLeft = false;
                break;
            }

            equalLeft.append((char) ('a' + ch));
            remainEqual[ch]--;
        }

        if (canMakeEqualLeft) {

            StringBuilder answer = new StringBuilder();

            answer.append(equalLeft);

            // Middle character for odd length
            if (n % 2 != 0) {
                answer.append(middle);
            }

            // Mirror left half
            answer.append(
                new StringBuilder(equalLeft).reverse()
            );

            String palindrome = answer.toString();

            // If equal left half already gives a palindrome
            // greater than target, this is the smallest answer.
            if (palindrome.compareTo(target) > 0) {
                return palindrome;
            }
        }

        /*
         * -------------------------------------------------------
         * STEP 2:
         *
         * Equal left half did not work.
         *
         * Now we need to make the left half greater than
         * target's left half.
         *
         * We change the RIGHTMOST possible position first.
         *
         * This gives the smallest possible lexicographical answer.
         * -------------------------------------------------------
         */

        for (int pos = halfLength - 1; pos >= 0; pos--) {

            int[] remain = halfCount.clone();

            StringBuilder prefix = new StringBuilder();

            boolean possible = true;

            /*
             * Keep everything before pos equal to target.
             */
            for (int i = 0; i < pos; i++) {

                int ch = target.charAt(i) - 'a';

                if (remain[ch] == 0) {
                    possible = false;
                    break;
                }

                prefix.append((char) ('a' + ch));
                remain[ch]--;
            }

            if (!possible) {
                continue;
            }

            /*
             * At pos, choose the smallest character
             * greater than target[pos].
             */
            int targetChar = target.charAt(pos) - 'a';

            for (int ch = targetChar + 1; ch < 26; ch++) {

                if (remain[ch] == 0) {
                    continue;
                }

                StringBuilder left = new StringBuilder(prefix);

                left.append((char) ('a' + ch));
                remain[ch]--;

                /*
                 * Fill remaining positions with the smallest
                 * possible characters.
                 */
                for (int c = 0; c < 26; c++) {

                    while (remain[c] > 0) {
                        left.append((char) ('a' + c));
                        remain[c]--;
                    }
                }

                /*
                 * Build palindrome.
                 */
                StringBuilder answer = new StringBuilder();

                answer.append(left);

                if (n % 2 != 0) {
                    answer.append(middle);
                }

                answer.append(
                    new StringBuilder(left).reverse()
                );

                return answer.toString();
            }
        }

        // No valid palindrome greater than target
        return "";
    }
}