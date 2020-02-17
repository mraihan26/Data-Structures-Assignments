import java.io.*;
import java.util.*;
import java.lang.Math;

class Node {
    Node left;
    Node right;
    int value;
    
    Node(int value) {
        this.value = value;
        left = null;
        right = null;
    }

    public void getPostOrder(List<Integer> a){
        if(left!=null){
            left.getPostOrder(a);
        }
        if(right!=null){
            right.getPostOrder(a);
        }
        a.add(value);
    }

    public void printPostOrder(){
        if( left != null )
        left.printPostOrder(); // Left
        if( right != null )
        right.printPostOrder(); // Right
        System.out.println(value); // Nod
    }
}

class Tree {
    Node root;
    
    Tree() {
        root = null;
    }
    
    private Node insert(Node subroot, int value) {
        // TODO: lengkapi method insert pada binary search tree
        if(subroot==null){
            subroot = new Node(value);
        }else if(value<subroot.value){
            subroot.left = insert(subroot.left, value);
        }else if(value>subroot.value){
            subroot.right = insert(subroot.right, value);
        }
        return subroot;
    }
    
    // method insert yang dapat digunakan di main
    // dapat dipanggil dengan cara tree.insert(x)
    public void insert(int value) {
        root = insert(root, value);
    }

    public void getPostOrder(List<Integer> a){

        if(root != null){
            root.getPostOrder(a);
        }
        
    }

    public int findHeight(Node a){
        if(a == null){
            return -1;
        }else{
            return Math.max(findHeight(a.left)+1, findHeight(a.right)+1);
        }

    }

    public int findHeight(){
        return findHeight(root);
    }

    public void printPostOrder(){
        if(root!=null){
            root.printPostOrder();
        }
    }
    
    // TODO: buat method lain di sini jika dibutuhkan
}

public class ws4{
    private static InputReader in;
    private static PrintWriter out;
    private static int N;
    
    public static void main(String[] args) throws IOException {
        in = new InputReader(System.in);
        out = new PrintWriter(System.out);
        ArrayList<Tree> daftarTree = new ArrayList<Tree>();

        int T = in.nextInt();
        for (int t = 0; t < T; t++) {
            N = in.nextInt();
            List<Integer> A = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                A.add(in.nextInt());
            }
            Tree binary = new Tree();
            for(int i= A.size()-1;i>=0;i--){
                binary.insert(A.get(i));
            }
            ArrayList<Integer> postOrder = new ArrayList<Integer>();
            binary.getPostOrder(postOrder);
            boolean betul = true;
            for(int i = 0;i<A.size();i++){
                if(!A.get(i).equals(postOrder.get(i))){
                    betul = false;
                }
            }
            if(betul){
                daftarTree.add(binary);
            }else{
                daftarTree.add(new Tree());
            }

        }

        for(Tree binary:daftarTree){
            System.out.println(binary.findHeight());
        }
        
        out.close();
    }

    // public static Node bikinTree(List<Integer> a, int awal, int akhir){
    //     if(awal > akhir){
    //         return null;
    //     }

    //     Node akar = new Node(a.get(akhir));

    //     int batas;
    //     for(batas = akhir;batas>=awal;batas--){
    //         if(a.get(batas)<akar.value){
    //             break;
    //         }
    //     }

    //     akar.right = bikinTree(a, awal, batas);
    //     akar.left = bikinTree(a, batas+1, akhir-1);
    //     return akar;
    // }

    // taken from https://codeforces.com/submissions/Petr
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
 
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
    }
}
