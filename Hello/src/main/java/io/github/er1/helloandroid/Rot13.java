package io.github.er1.helloandroid;

public class Rot13 {
    public static String rot13(CharSequence in) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < in.length(); i++) {
            char ch = in.charAt(i);
            char c = Character.toUpperCase(ch);
            if (c >= 'A' && c <= 'M')
                ch += 13;
            if (c >= 'N' && c <= 'Z')
                ch -= 13;
            out.append(ch);
        }
        return out.toString();
    }
}
