
class Solution {
    public int minMoves(int[] nums) {

        int max = nums[0];

        // Find maximum element
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > max) {
                max = nums[i];
            }
        }

        int moves = 0;

        // Calculate total moves
        for (int i = 0; i < nums.length; i++) {
            moves += max - nums[i];
        }

        return moves;
    }
}
