class Solution {
    public int smallestUniqueSubarray(int[] nums) {

        int n = nums.length;

        // Step 1: Build suffix array
        int[] sa = buildSuffixArray(nums);

        // Step 2: Calculate rank of each suffix
        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            rank[sa[i]] = i;
        }

        // Step 3: Calculate LCP array
        int[] lcp = new int[n];

        int h = 0;

        for (int i = 0; i < n; i++) {

            int r = rank[i];

            if (r == 0) {
                h = 0;
                continue;
            }

            int j = sa[r - 1];

            while (i + h < n &&
                   j + h < n &&
                   nums[i + h] == nums[j + h]) {
                h++;
            }

            lcp[r] = h;

            if (h > 0) {
                h--;
            }
        }

        int ans = n;

for (int i = 0; i < n; i++) {

    int start = sa[i];

    int maxLcp = lcp[i];

    if (i + 1 < n) {
        maxLcp = Math.max(maxLcp, lcp[i + 1]);
    }

    int uniqueLength = maxLcp + 1;

    // Check whether this length fits inside the suffix
    if (uniqueLength <= n - start) {
        ans = Math.min(ans, uniqueLength);
    }
}

return ans;
    }
    // Build suffix array using counting sort
    private int[] buildSuffixArray(int[] nums) {

        int n = nums.length;
        int N = n + 1;

        int[] a = new int[N];

        for (int i = 0; i < n; i++) {
            a[i] = nums[i];
        }

        // Sentinel value 0 (all nums[i] >= 1)
        a[n] = 0;

        int[] p = new int[N];
        int[] c = new int[N];

        int[] cnt = new int[Math.max(100002, N)];

        // Initial sorting
        for (int x : a) {
            cnt[x]++;
        }

        for (int i = 1; i < cnt.length; i++) {
            cnt[i] += cnt[i - 1];
        }

        for (int i = N - 1; i >= 0; i--) {
            p[--cnt[a[i]]] = i;
        }

        // Initial equivalence classes
        int classes = 1;
        c[p[0]] = 0;

        for (int i = 1; i < N; i++) {

            if (a[p[i]] != a[p[i - 1]]) {
                classes++;
            }

            c[p[i]] = classes - 1;
        }

        int[] pn = new int[N];
        int[] cn = new int[N];

        // Doubling algorithm
        for (int len = 1; len < N; len *= 2) {

            for (int i = 0; i < N; i++) {
                pn[i] = p[i] - len;

                if (pn[i] < 0) {
                    pn[i] += N;
                }
            }

            Arrays.fill(cnt, 0, classes, 0);

            for (int i = 0; i < N; i++) {
                cnt[c[pn[i]]]++;
            }

            for (int i = 1; i < classes; i++) {
                cnt[i] += cnt[i - 1];
            }

            for (int i = N - 1; i >= 0; i--) {
                int x = pn[i];
                p[--cnt[c[x]]] = x;
            }

            cn[p[0]] = 0;
            int newClasses = 1;

            for (int i = 1; i < N; i++) {

                int cur = p[i];
                int prev = p[i - 1];

                int mid = (cur + len) % N;
                int midPrev = (prev + len) % N;

                if (c[cur] != c[prev] ||
                    c[mid] != c[midPrev]) {
                    newClasses++;
                }

                cn[cur] = newClasses - 1;
            }

            int[] temp = c;
            c = cn;
            cn = temp;

            classes = newClasses;
        }

        // Remove the suffix consisting only of sentinel
        int[] sa = new int[n];

        for (int i = 0; i < n; i++) {
            sa[i] = p[i + 1];
        }

        return sa;
    }
}
