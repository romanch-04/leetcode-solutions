class Solution {
    public String reverseParentheses(String s) {

        Stack<Character> stack = new Stack<>();

        for(char ch: s.toCharArray()) {
            if(ch == ')') {
                StringBuilder temp = new StringBuilder();

                //take characters until '('
                while(stack.peek() != '(') {
                    temp.append(stack.pop());
                }

                //remove '('
                stack.pop();

                //temp is already reversed because of stack
                for(int i=0; i<temp.length(); i++) {
                    stack.push(temp.charAt(i));
                }
            } else {
                stack.push(ch);
            }
        }

        StringBuilder result = new StringBuilder();

        while(!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.reverse().toString();
    }
}