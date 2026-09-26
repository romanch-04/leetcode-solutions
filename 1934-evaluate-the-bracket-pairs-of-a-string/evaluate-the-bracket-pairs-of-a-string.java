class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {
        //stores key-value pairs in HashMap
        HashMap<String, String> map = new HashMap<>();

        for(List<String> pair: knowledge) {
            map.put(pair.get(0), pair.get(1));
        }

        StringBuilder result = new StringBuilder();

        int i = 0;

        while(i < s.length()) {
            //if we find a opening bracket
            if(s.charAt(i) == '(') {
                int j = i+1;

                //find closing bracket
                while(s.charAt(j) != ')') {
                    j++;
                }

                //Get key inside brackets
                String key = s.substring(i+1, j);

                //check if key exists
                if(map.containsKey(key)) {
                    result.append(map.get(key));
                } else {
                    result.append("?");
                }

                //move i after ')'
                i = j+1;
            } else {
                //normal characters
                result.append(s.charAt(i));
                i++;
            }
        }

        return result.toString();
    }
}