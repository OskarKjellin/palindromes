import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Kurt
 * Date: 5/6/13
 * Time: 11:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class PalindromeFinder {
    private int cacheSize = 4;
    private int itemsInCache = 0;
    private int currentIndex = 0;
    private char[] cache = new char[cacheSize];
    private final Scanner scanner;
    private final Map<Integer, String> palindromes = new HashMap<Integer, String>();
    private int maxSize = 0;
    StringBuffer buffer = new StringBuffer();


    public PalindromeFinder(InputStream stream) {
        scanner = new Scanner(stream);
        scanner.useDelimiter("");
    }

    public Map<Integer, String> getPalindromes() {
        return palindromes;
    }

    public void findPalindromes() {
        char current = readFromBuffer(0);
        char next = read();
        char nextNext = read();
        while (true) {
            //Yay, found a palindrome with an even number of chars
            if (current == next) {
                String palindrome = "" + current + next;
                foundPalindrome(palindrome, 0);
            }
            //Yay, found a palindrome with an uneven number of chars
            if (current == nextNext) {
                String palindrome = "" + current + next + nextNext;
                foundPalindrome(palindrome, 0);
            }
            if (!hasInBuffer()) {
                //Base case, last one
                if (next == nextNext) {
                    String palindrome = "" + next + nextNext;
                    foundPalindrome(palindrome, 0);
                }
                break;
            }
            putInCache(current);
            current = next;
            next = nextNext;
            nextNext = readFromBuffer(2);
            currentIndex++;
        }
    }

    private void putInCache(char current) {
        if (itemsInCache == cacheSize) {
            for (int i = 1; i < cache.length - 1; i++) {
                cache[i - 1] = cache[i];
            }
        } else {
            itemsInCache++;
        }

        cache[itemsInCache - 1] = current;
    }

    private char read() {
        char result = scanner.next().charAt(0);
        buffer.append(result);
        return result;
    }

    private char readFromBuffer(int index) {
        if (buffer.length() <= index)
            read();
        char result = buffer.charAt(index);
        buffer.deleteCharAt(0);
        return result;
    }

    private void foundPalindrome(String middle, int lookBack) {
        palindromes.put(currentIndex - lookBack, middle);
        if(middle.length() > maxSize)
            maxSize = middle.length();
        lookBack++;
        if (itemsInCache - lookBack < 0) {
            if (itemsInCache == cacheSize){
                System.out.println("Maybe missed one item. Entire cache is a palindrome. Doubling cache size");
                cacheSize *=2;
                cache = Arrays.copyOf(cache, cacheSize);
            }
            return;
        }
        char back = cache[itemsInCache - lookBack];

        //Buffer is too small
        if (lookBack > (buffer.length() - 1)) {
            if (!hasNext())
                return;
            read();
        }

        char next = buffer.charAt(lookBack);
        if (back == next)
            foundPalindrome(back + middle + next, lookBack);

    }

    private boolean hasNext() {
        return scanner.hasNext();
    }

    private boolean hasInBuffer() {
        return buffer.length() > 2 || scanner.hasNext();
    }
     public int getMaxSize(){
         return maxSize;
     }
}
