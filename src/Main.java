import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {


    }
}
// 2 stack solution with no
// "3[a2[c]]"
class Solution {
    String decodeString(String s) {
        Stack<Integer> countStack = new Stack<>();
        Stack<StringBuilder> stringStack = new Stack<>();
        StringBuilder currentString = new StringBuilder();
        int k = 0;
        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) { // this section executes until full int handled
                k = k * 10 + ch - '0';

            } else if (ch == '[') {  // when '[' found, means preciding int fully handled
                countStack.push(k);// so load it up
                stringStack.push(currentString); // whatever the currrent string is, load that into its stack since
                // we want it separate from the new string within the [] that we are handling
                currentString = new StringBuilder(); // create new buiderrr to handle [] content
                k = 0; // since k val pushed, reset to zero so now both empty builder and k for new content
            } else if (ch == ']') {
                StringBuilder decodedString = stringStack.pop(); // retrieve prior builderr content
                for (int currentK = countStack.pop(); currentK > 0; currentK--) {
                    decodedString.append(currentString); // append new build to old build 'k' times
                } // getting a littlee lost here with nested loop how its a+cc + a + cc, etc and not ust
                // decoded = a, append 'cc' 3x. acccccc. how getting other a's?
                currentString = decodedString; // fully finished appending, so store curreent string as 'complete' build
                // until later stored if another '[' found
            } else {
                currentString.append(ch);
            }
        }
        return currentString.toString();
    }
}

//single stack solution
class Solution {
    public String decodeString(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ']') {

                List<Character> decodedString = new ArrayList<>(); // stores letters
                while (stack.peek() != '[') {
                    decodedString.add(stack.pop()); // since adding items into List in LIFO, will be backwards
                }

                stack.pop(); // pops the '['

                int base = 1;  // this is important for encountering non-single digit ints like 100 or 15, etc.
                int k = 0;
                while (!stack.isEmpty() && Character.isDigit(stack.peek())) { // int now established when complete
                    k = k + (stack.pop() - '0') * base;
                    base *= 10;
                }
                while (k != 0) {  // now loop List in reverse (rev of rev = OG) and add chars at indices k # of times
                    for (int j = decodedString.size() - 1; j >= 0; j--) {
                        stack.push(decodedString.get(j)); // pushing back to stack
                    } // downside here is redundant push/pop such as "3[a2[c]]" where 'c' is added to List then
                    // 2 'c' pushed back on but then anotherr closing brackeet so we pop those 'c's right back to list again
                    // then push 'a' to list and push evrrything back etc. basically, nested brackts = redundant push/pops
                    k--;
                }
            }
            else { // pushes all chars to stack unless ']'
                stack.push(s.charAt(i));
            }
        }
        char[] result = new char[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        return new String(result);
    }
}

// mine,
class Solution {
    public String decodeString(String s) {
        Stack<Character> stack = new Stack<>();
        StringBuilder finalBuild = new StringBuilder();
        for(Character c: s.toCharArray()){
            if(Character.isLetterOrDigit(c)){
                stack.push(c);
            } else{
                if(c == ']'){
                    StringBuilder b = new StringBuilder();
                    while(Character.isLetter(stack.peek())){
                        b.append(Character.toString(stack.pop()));
                    }
                    int freq = Integer.parseInt(Character.toString(stack.pop()));
                    for(int i = 1; i<freq; i++){
                        StringBuilder temp = new StringBuilder();
                        temp = b;
                        b = b.append(temp);
                    }
                    finalBuild.append(b);
                }
            }
        }
        return finalBuild.toString();
    }
}
// problem is reverse ordering of bc in "3[a]2[bc]" and getting one extra a. can reverse ok but this gts sloppy. answrr time





// stack not needed? just one stored for freq and another
// to store start index of bracket contents for substring
// then stringbuilder.append(substring) through while loop
// < freq. convert to string
// "3[a]2[bc]" that's fine here, but not for others
// similar to reverse polish notation "3[a2[c]]"

// every closing bracket, we can process contained contents
// push 3, push 'a' push 2 push c, closing bracket found
// then must pop until int found so pop c and 2, build a string
// and push it to stack. pop cc, pop a, pop 3, so combined acc and
// loop to append accaccacc
// requires onee stack to takee both int and string...
// unless ints pushed as string then parseed but not surre
// of meethod to do that. would reequiree a try catch block
// which havn't seen in leetcode
// could use a Hashmap<String,Integer> and store "int" = int
// for #s 0-9 but probably not theee right idea
// Character.isDigit is a class method boolean ****
// Character.isLetter and isLetterOrDigit also exist

// push all ints/letters as Character to stack. say if ] found
// then pop and it letter found, combine into string until number
// found, then loop to append the string onto itself by int-1 times.
// store the current string then continue appending when moree
// closed bracket ] proceesseeed
