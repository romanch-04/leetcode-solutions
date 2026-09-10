/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {

    int ans = 0;

    public int averageOfSubtree(TreeNode root) {
        helper(root);
        return ans;
    }

    // Returns: [sum, count]
    int[] helper(TreeNode root) {

        // Empty node
        if (root == null) {
            return new int[]{0, 0};
        }

        // Get information from left subtree
        int[] left = helper(root.left);

        // Get information from right subtree
        int[] right = helper(root.right);

        // Calculate current subtree
        int sum = root.val + left[0] + right[0];
        int count = 1 + left[1] + right[1];

        // Calculate average
        int average = sum / count;

        // Check if current node equals average
        if (root.val == average) {
            ans++;
        }

        return new int[]{sum, count};
    }
}