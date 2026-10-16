class Solution {
    static final long MOD = 1_000_000_007L;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        SegTree st = new SegTree(n);
        Map<Integer, Integer> last = new HashMap<>();
        long ans = 0;

        for (int i = 0; i < n; i++) {
            int prev = last.getOrDefault(nums[i], -1);

            st.update(prev + 1, i, 1);
            last.put(nums[i], i);

            ans = (ans + st.query(0, i)) % MOD;
        }

        return (int) ans;
    }

    static class SegTree {
        int n;
        long[] sum, sq, lazy;

        SegTree(int n) {
            this.n = n;
            sum = new long[4 * n];
            sq = new long[4 * n];
            lazy = new long[4 * n];
        }

        void apply(int node, int l, int r, long val) {
            long len = r - l + 1L;
            sq[node] = (sq[node] + 2 * val * sum[node]
                       + len * val % MOD * val) % MOD;
            sum[node] = (sum[node] + len * val) % MOD;
            lazy[node] = (lazy[node] + val) % MOD;
        }

        void push(int node, int l, int r) {
            if (lazy[node] == 0 || l == r) return;

            int mid = (l + r) / 2;
            apply(node * 2, l, mid, lazy[node]);
            apply(node * 2 + 1, mid + 1, r, lazy[node]);
            lazy[node] = 0;
        }

        void update(int ql, int qr, int val) {
            update(1, 0, n - 1, ql, qr, val);
        }

        void update(int node, int l, int r,
                    int ql, int qr, int val) {
            if (ql <= l && r <= qr) {
                apply(node, l, r, val);
                return;
            }

            push(node, l, r);
            int mid = (l + r) / 2;

            if (ql <= mid)
                update(node * 2, l, mid, ql, qr, val);
            if (qr > mid)
                update(node * 2 + 1, mid + 1, r, ql, qr, val);

            sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
            sq[node] = (sq[node * 2] + sq[node * 2 + 1]) % MOD;
        }

        long query(int ql, int qr) {
            return query(1, 0, n - 1, ql, qr);
        }

        long query(int node, int l, int r, int ql, int qr) {
            if (ql <= l && r <= qr) return sq[node];

            push(node, l, r);
            int mid = (l + r) / 2;
            long res = 0;

            if (ql <= mid)
                res += query(node * 2, l, mid, ql, qr);
            if (qr > mid)
                res += query(node * 2 + 1, mid + 1, r, ql, qr);

            return res % MOD;
        }
    }
}