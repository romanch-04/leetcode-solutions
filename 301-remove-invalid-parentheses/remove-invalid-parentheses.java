class Solution {
    Set<String> ans = new HashSet<>();
    public List<String> removeInvalidParentheses(String s) {
        int left = 0;
        int right = 0;
        for(char ch: s.toCharArray()) {
            if(ch == '(') {
                left++;
            } else if(ch == ')'){
                if(left > 0) {
                    left--;
                }
                else {
                    right++;
                }
            }
        }
        remove(s, 0, left, right);
        return new ArrayList<>(ans);
    }
    void remove(String s, int index, int left, int right) {
        if(left == 0 && right == 0) {
            if(isValid(s)) {
                ans.add(s);
            }
            return;
        }
        for(int i=index; i<s.length(); i++) {
            if(i>index && s.charAt(i) == s.charAt(i-1)) {
                continue;
            }
            char ch = s.charAt(i);
            if(ch == '(' && left > 0) {
                String next = s.substring(0, i) + s.substring(i+1);
                remove(next, i, left-1, right);
            }
            if(ch == ')' && right > 0) {
                String next = s.substring(0, i) + s.substring(i+1);
                remove(next, i, left, right-1);
            }
        }
    }
    boolean isValid(String s) {
        int bal = 0;
        for(char ch: s.toCharArray()) {
            if(ch == '(') {
                bal++;
            } else if(ch == ')') {
                bal--;
            }
            if(bal < 0) {
                return false;
            }
        }
        return bal == 0;
    }
}