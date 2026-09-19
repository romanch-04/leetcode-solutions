class Solution {
    public boolean checkOverlap(int radius, int xCenter, int yCenter, int x1, int y1, int x2, int y2) {
        
        //finding the closest point of rectangle to the  circle center
        int closestx = Math.max(x1, Math.min(xCenter, x2));
        int closesty = Math.max(y1, Math.min(yCenter, y2));

        //Distance between circle center and closest point
        int dx = xCenter - closestx;
        int dy = yCenter - closesty;

        //Checking if distance is with in the radius
        return dx * dx + dy * dy <= radius * radius;
    }
}