class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {

        int n = parent.length;

        // Step 1: Build original tree
        List<Integer>[] children = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            children[parent[i]].add(i);
        }

        // Step 2: Find new parent using DFS
        int[] newParent = parent.clone();
        int[] last = new int[26];
        int[] previous = new int[n];

        Arrays.fill(last, -1);

        int[] stack = new int[n];
        int[] index = new int[n];

        int top = 0;
        stack[0] = 0;

        last[s.charAt(0) - 'a'] = 0;

        while (top >= 0) {

            int node = stack[top];

            if (index[top] < children[node].size()) {

                int child = children[node].get(index[top]++);

                int c = s.charAt(child) - 'a';

                // Remember previous ancestor with same character
                previous[child] = last[c];

                if (last[c] != -1) {
                    newParent[child] = last[c];
                }

                last[c] = child;

                stack[++top] = child;
                index[top] = 0;

            } else {

                // Restore previous ancestor when DFS backtracks
                last[s.charAt(node) - 'a'] = previous[node];

                top--;
            }
        }

        // Step 3: Build modified tree
        List<Integer>[] newChildren = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            newChildren[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            newChildren[newParent[i]].add(i);
        }

        // Step 4: Get traversal order of modified tree
        int[] order = new int[n];
        int[] queue = new int[n];

        int front = 0, rear = 0;
        int count = 0;

        queue[rear++] = 0;

        while (front < rear) {

            int node = queue[front++];
            order[count++] = node;

            for (int child : newChildren[node]) {
                queue[rear++] = child;
            }
        }

        // Step 5: Calculate subtree sizes
        int[] answer = new int[n];
        Arrays.fill(answer, 1);

        for (int i = n - 1; i > 0; i--) {

            int node = order[i];

            answer[newParent[node]] += answer[node];
        }

        return answer;
    }
}
