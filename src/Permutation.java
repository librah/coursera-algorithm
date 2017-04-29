import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    // takes a command-line integer k; reads in a sequence of strings
    // from standard input using StdIn.readString(); and prints exactly
    // k of them, uniformly at random. Print each item from the sequence
    // at most once.
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Permutation <n>");
            System.out.println(" <n>: number of items to print");
            return;
        }

        int k = Integer.parseInt(args[0]);
        String input[] = StdIn.readAllStrings();
        RandomizedQueue<String> q = new RandomizedQueue<>();
        for (int i = 0; i < input.length; i++) {
            q.enqueue(input[i]);
        }

        for (String s : q) {
            if (k > 0) {
                StdOut.println(s);
                k --;
            } else {
                break;
            }
        }
    }
}
