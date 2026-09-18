class Solution {
    public List<String> maxNumOfSubstrings(String s) {

        List<String> result = new ArrayList<>();

        int n = s.length();

        // First and last occurrence of each character
        int[] first = new int[26];
        int[] last = new int[26];

        Arrays.fill(first, n);

        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';

            first[c] = Math.min(first[c], i);
            last[c] = i;
        }

        // Store valid intervals [start, end]
        List<int[]> intervals = new ArrayList<>();

        for (int c = 0; c < 26; c++) {

            if (first[c] == n) {
                continue;
            }

            int l = first[c];
            int r = last[c];

            boolean valid = true;

            // Expand the interval if necessary
            for (int i = l; i <= r; i++) {

                int current = s.charAt(i) - 'a';

                // This character appeared before l,
                // so the interval cannot contain all occurrences.
                if (first[current] < l) {
                    valid = false;
                    break;
                }

                // Need to include all occurrences of this character
                r = Math.max(r, last[current]);
            }

            if (valid) {
                intervals.add(new int[]{l, r});
            }
        }

        // Sort by ending position
        intervals.sort((a, b) -> Integer.compare(a[1], b[1]));

        int previousEnd = -1;

        for (int[] interval : intervals) {

            int l = interval[0];
            int r = interval[1];

            // Non-overlapping
            if (l > previousEnd) {
                result.add(s.substring(l, r + 1));
                previousEnd = r;
            }
        }

        return result;
    }
}