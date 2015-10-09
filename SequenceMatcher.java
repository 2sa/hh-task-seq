
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

class BigInt {
    ArrayList<Integer> a;
    BigInt b;
    int base = 10;

    public BigInt() {
        a = new ArrayList<>();
        a.add(1);
    }

    public BigInt(int d) {
        a = new ArrayList<>();
        a.add(d);
        b = new BigInt();
    }

    void increment()  {
        int carry = 0;

        for (int i= 0 ; i < Math.max(a.size(), b.a.size()) || carry != 0; ++i) {
            if (i == a.size()) {
                a.add(0);
            }

            int ai = a.get(i);
            ai += carry + (i < b.a.size() ? b.a.get(i) : 0);
            carry = ai >= base ? 1 : 0;

            if (carry != 0) {
                ai -= base;
            }

            a.set(i, ai);
        }

        while (a.get(a.size() - 1) == 0) {
            a.remove(a.size() - 1);
        }
    }

    int get(int i) {
        return a.get(a.size() - i - 1);
    }

    int size() {
        return a.size();
    }
}

class Sequence {
    BigInt bi;
    int last = 0;
    public Sequence() {
        bi = new BigInt(1);
    }

    int next() {
        if (last >= bi.size()) {
            bi.increment();
            last = 0;
        }

        return bi.get(last++);
    }
}

public class SequenceMatcher
{
    private Sequence se = new Sequence();
    private int[] results;
    private ArrayList<Node> trie;
    private ArrayList<Integer> lengthOfPattern;
    int found = 0;

    class Node {
        public int flag;
        public int ch;
        public int par;
        public int suff;
        public int gsuff;

        public List<Integer> nodes;

        public Node(int p, int c) {
            ch = c;
            par = p;
            suff = -1;
            gsuff = -1;
            flag = 0;
            nodes = new ArrayList<>();
            for(int i =0; i < 10; ++i)
                nodes.add(-1);
        }
    }

    public SequenceMatcher() {
        Node a = new Node(-1, 73474824);
        trie = new ArrayList<>();
        lengthOfPattern = new ArrayList<>();
        trie.add(a);
        size = 0;
        lengthOfPattern.add(size);
        ++size;

    }

    private int addPattern(String s) {
        int num = 0;
        Node a;
        int l = s.length();
        int ind;

        for (char c : s.toCharArray()) {
            ind = c - '0';

            if (trie.get(num).nodes.get(ind)==-1) {
                a = new Node(num, ind);
                trie.add(a);
                trie.get(num).nodes.set(ind, trie.size() - 1);
            }

            num = trie.get(num).nodes.get(ind);
        }

        lengthOfPattern.add(l);
        trie.get(num).flag = size++;
        return 1;
    }

    private void buildLinks() {
        Queue<Integer> q = new LinkedList<>();

        for (int i : trie.get(0).nodes) {
            if (i != -1)
                q.add(i);
        }

        while (!q.isEmpty()) {
            int cur = q.peek();
            int ind = trie.get(cur).ch;
            int p = trie.get(cur).par;
            int suff = trie.get(p).suff;

            q.poll();

            for (int i : trie.get(cur).nodes ) {
                if (i != -1)
                    q.add(i);
            }

            trie.get(cur).suff = 0;

            while (suff != -1) {
                if (trie.get(suff).nodes.get(ind)>0) {
                    trie.get(cur).suff = trie.get(suff).nodes.get(ind);
                    break;
                }

                suff = trie.get(suff).suff;
            }

            int gs = trie.get(cur).suff;

            while (gs != -1) {
                if (trie.get(gs).flag!=0) {
                    trie.get(cur).gsuff = gs;
                    break;
                }

                gs = trie.get(gs).suff;
            }
        }
    }

    private void matchAll() {
        results = new int[lengthOfPattern.size()];
        int v = 0;
        int l = 0;
        int ind;

        while (found < (lengthOfPattern.size() - 1)) {
            ind = se.next();
            l++;
            v = go(v, ind);
            check(v, l);
        }

        for(int i = 1; i < results.length; ++i)
        {
            System.out.printf("%d\n", results[i]);
        }
    }

    private void check(int v, int i) {
        for (int u = v; u != -1; u = trie.get(u).gsuff) {
            if (trie.get(u).flag!=0) {
                int nw = i+1 - lengthOfPattern.get(trie.get(u).flag);
                if(results[trie.get(u).flag] == 0) {
                    results[trie.get(u).flag] = nw;
                    ++found;
                }
            }
        }
    }

    private int go(int v, int ch) {
        int ind = ch;
        if(ind == -1 || v == -1) return 0;
        if (trie.get(v).nodes.get(ind)!=-1) {
            return trie.get(v).nodes.get(ind);
        }

        if (v == 0) {
            return 0;
        }

        return go(trie.get(v).suff, ch);
    }

    private int size;

    public static void main(String[] args) throws IOException {
        SequenceMatcher ac = new SequenceMatcher();
        FileInputStream in = new FileInputStream(args[0]);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        
        while (reader.ready()) {
            String pattern = reader.readLine().trim();
            ac.addPattern(pattern);
        }
        
        ac.buildLinks();
        ac.matchAll();
    }
}


