
import java.util.*;

class Solution {
    private List<Integer>[] adj;
    private int[][] up;
    private int[] depth, tin, tout, parent;
    private int[] rootMask, bit;
    private char[] chars;
    private int timer, LOG, n;

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        this.n = n;
        chars = s.toCharArray();

        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        up = new int[LOG][n];
        depth = new int[n];
        tin = new int[n];
        tout = new int[n];
        parent = new int[n];
        rootMask = new int[n];
        bit = new int[n + 2];

        buildTree();

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");
            int u = Integer.parseInt(p[1]);

            if (p[0].equals("update")) {
                char c = p[2].charAt(0);

                int oldMask = 1 << (chars[u] - 'a');
                int newMask = 1 << (c - 'a');
                int delta = oldMask ^ newMask;

                if (delta != 0) {
                    rangeXor(tin[u], tout[u], delta);
                    chars[u] = c;
                }
            } else {
                int v = Integer.parseInt(p[2]);
                int lca = getLCA(u, v);

                int maskU = rootMask[u] ^ pointQuery(tin[u]);
                int maskV = rootMask[v] ^ pointQuery(tin[v]);
                int lcaMask = 1 << (chars[lca] - 'a');

                int pathMask = maskU ^ maskV ^ lcaMask;

                ans.add((pathMask & (pathMask - 1)) == 0);
            }
        }

        return ans;
    }

    private void buildTree() {
        int[] iter = new int[n];
        int[] stack = new int[n];
        int top = 0;

        stack[0] = 0;
        parent[0] = 0;
        rootMask[0] = 1 << (chars[0] - 'a');
        timer = 0;
        tin[0] = timer++;

        while (top >= 0) {
            int u = stack[top];

            if (iter[u] < adj[u].size()) {
                int v = adj[u].get(iter[u]++);

                if (v == parent[u]) continue;

                parent[v] = u;
                depth[v] = depth[u] + 1;
                rootMask[v] = rootMask[u] ^ (1 << (chars[v] - 'a'));

                up[0][v] = u;
                for (int j = 1; j < LOG; j++) {
                    up[j][v] = up[j - 1][up[j - 1][v]];
                }

                tin[v] = timer++;
                stack[++top] = v;
            } else {
                tout[u] = timer;
                top--;
            }
        }

        for (int j = 1; j < LOG; j++) {
            up[j][0] = 0;
        }
    }

    private int getLCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < LOG; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) return u;

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return up[0][u];
    }

    // XOR delta over Euler interval [left, right).
    private void rangeXor(int left, int right, int delta) {
        add(left + 1, delta);
        add(right + 1, delta);
    }

    private void add(int i, int value) {
        while (i < bit.length) {
            bit[i] ^= value;
            i += i & -i;
        }
    }

    private int pointQuery(int index) {
        int result = 0;
        int i = index + 1;

        while (i > 0) {
            result ^= bit[i];
            i -= i & -i;
        }

        return result;
    }
}
