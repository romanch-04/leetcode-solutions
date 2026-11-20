class Solution {
    public int longestPalindrome(String s) {
        
        int[] freq = new int[128];

        // Count character frequencies
        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i)]++;
        }

        int length = 0;
        boolean hasOdd = false;

        // Calculate palindrome length
        for (int count : freq) {

            length += (count / 2) * 2;

            if (count % 2 == 1) {
                hasOdd = true;
            }
        }

        if (hasOdd) {
            length++;
        }

        return length;
    }
}