import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Kurt
 * Date: 5/6/13
 * Time: 9:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static final char[] characters = "abcdefghijklmnopqrstuvxyz".toCharArray();

    public static void main(String[] args) {
        String input = "";
        Random r = new SecureRandom();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1000000; i++) {
            builder.append(characters[r.nextInt(characters.length)]);
        }
        input = builder.toString();

        System.out.println(input);
        InputStream is = new ByteArrayInputStream(input.getBytes(Charset.defaultCharset()));
        PalindromeFinder finder = new PalindromeFinder(is);
        long start = System.currentTimeMillis();
        finder.findPalindromes();
        //System.out.println(finder.getPalindromes());
        System.out.println("Found " + finder.getPalindromes().size() + " in " + (System.currentTimeMillis() - start) + " ms");
        System.out.println("Max length is " + finder.getMaxSize());
    }
}
