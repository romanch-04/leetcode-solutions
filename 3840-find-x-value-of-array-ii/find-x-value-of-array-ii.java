class Solution {

    int k;
    int[][] cnt;
    int[] prod;

    void build(int[] nums, int node, int l, int r) {
        if(l == r) {
            prod[node] = nums[l] % k;
            cnt[node][prod[node]] = 1;
            return;
        }

        int mid = (l + r) / 2;

        build(nums, node * 2, l, mid);
        build(nums, node * 2 + 1, mid + 1, r);

        merge(node);
    }

    void merge(int node) {
        int left = node * 2;
        int right = node * 2 + 1;

        prod[node] = (prod[left] * prod[right]) % k;

        for(int r=0; r<k; r++) {
            cnt[node][r] += cnt[left][r];

            int x = (prod[left] * r) % k;
            cnt[node][x] += cnt[right][r];
        }
    }

    void update(int node, int l, int r, int pos, int val) {
        if(l == r)  {
            prod[node] = val % k;

            for(int i=0; i<k; i++) {
                cnt[node][i] = 0;
            }

            cnt[node][prod[node]] = 1;
            return;
        }

        int mid = (l + r) / 2;

        if(pos <= mid) {
            update(node * 2, l, mid, pos, val);
        } else {
            update(node * 2 + 1, mid + 1, r, pos, val);
        }

        //Recalculate this node
        for(int i=0; i<k; i++) {
            cnt[node][i] = 0;
        }
        merge(node);
    }

    //returns information for range [ql, qr]
    int[] query(int node, int l, int r, int ql, int qr) {

        //no overlap
        if(r < ql || l > qr) {
            return null;
        }

        //complete overlap
        if(ql <= l && r <= qr) {
            int[] res = new int[k + 1];

            res[0] = prod[node];

            for(int i=0; i<k; i++) {
                res[i+1] = cnt[node][i];
            }
            return res;
        }

        int mid = (l + r) / 2;

        int[] a = query(node * 2, l, mid, ql, qr);
        int[] b = query(node * 2 + 1, mid + 1, r, ql, qr);

        //only right side exists
        if(a == null) {
            return b;
        }

        //only left side exists
        if(b == null) {
            return a;
        }

        //Merge both sides
        int[] res = new int[k + 1];

        res[0] = (a[0] * b[0]) % k;

        for(int i=0; i<k; i++) {

            // Prefix completely inside left
            res[i + 1] += a[i + 1];

            // Left complete + prefix  of right
            int x = (a[0] * i) % k;
            res[x + 1] += b[i + 1];
        }
        return res;
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
         this.k = k;

         int n = nums.length;

         cnt = new int[4 * n][k];
         prod = new int[4 * n];

         build(nums, 1, 0, n - 1);

         int[] ans = new int[queries.length];

         for(int i=0; i<queries.length; i++) {

            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            //update nums[index]
            update(1, 0, n-1, index, value);

            //Query [start ... n-1]
            int[] temp = query(1, 0, n-1, start, n-1);

            ans[i] = temp[x + 1];       
        }

        return ans;
    }
}