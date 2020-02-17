import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


public class solusiTP3 {
    private static InputReader in;
    private static PrintWriter out;
    private static Tree pohon;
    private static ArrayList<String> prinan = new ArrayList<String>();
    private static AVLTree pohonAVL = new AVLTree();
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);
        
        readInput();
        out.close();
    }
    
    private static void readInput() throws IOException {
        String donat1 = in.next();
        String donat2 = in.next();
        int banyakWilayah = in.nextInt();
        Wilayah negara = new Wilayah(in.next());
        pohon = new Tree(negara);
        pohon.daftarWilayah.put(pohon.root.data.id, pohon.root);
        int wilayahNegara = in.nextInt();
        for(int i = 0;i<wilayahNegara;i++){
            pohon.add(in.next(), pohon.root);
        }
        for(int i = 1;i<banyakWilayah;i++){
            Node<Wilayah> a = pohon.daftarWilayah.get(in.next());
            int jumlahWilayah = in.nextInt();
            for(int j = 0;j<jumlahWilayah;j++){
                pohon.add(in.next(),a);
            }
        }
        int banyakInstruksi = in.nextInt();
        for(int i = 0; i<banyakInstruksi;i++){
            String instruksi = in.next();
            if(instruksi.equals("TAMBAH")){
                String kelurahan = in.next();
                Node<Wilayah> wilayahTambah = pohon.daftarWilayah.get(kelurahan);
                long tambah1 = in.nextLong();
                long tambah2 = in.nextLong();
                tambahSuara(wilayahTambah, tambah1, tambah2);
                pohonAVL.preOrder(pohonAVL.root);
            }else if(instruksi.equals("ANULIR")){
                String kelurahan = in.next();
                Node<Wilayah> wilayahKurang = pohon.daftarWilayah.get(kelurahan);
                long kurang1 = in.nextLong();
                long kurang2 = in.nextLong();
                kurangSuara(wilayahKurang, kurang1, kurang2);
                pohonAVL.preOrder(pohonAVL.root);
            }else if(instruksi.equals("CEK_SUARA")){
                String kelurahan = in.next();
                cekSuara(kelurahan);
            }else if(instruksi.equals("WILAYAH_MENANG")){
                String donat = in.next();
                if(donat.equals(donat1)){
                    wilayahMenang(0);
                }else{
                    wilayahMenang(1);
                }
            }else if(instruksi.equals("WILAYAH_MINIMAL")){
                String donat = in.next();
                int persen = in.nextInt();
                if(donat.equals(donat1)){
                    wilayahMinimal(0, persen);
                }else{
                    wilayahMinimal(1, persen);
                }
            }else if(instruksi.equals("CEK_SUARA_PROVINSI")){
                cekSuaraProvinsi();
            }else{
                long bilangan = in.nextLong();
                int counter = wilayahSelisih(pohonAVL.root,bilangan);
                prinan.add(""+counter);
            }
        }
        pohonAVL.preOrder(pohonAVL.root);
        System.out.println(pohonAVL.countNodes(pohonAVL.root));

        for(int i = 0;i<prinan.size();i++){
            out.print(prinan.get(i));
            if(i!=prinan.size()-1){
                out.println();
            }
        }
    }

    public static void cekSuara(String w){
        Node<Wilayah> a = pohon.daftarWilayah.get(w);
        if(a!=null){
            prinan.add(a.data.jumlahSuara[0]+" "+a.data.jumlahSuara[1]);
        }
    }

    public static void wilayahMenang(int a){
        int jumlahWilayahMenang = 0;
        for(Node<Wilayah> b : pohon.daftarWilayah.values()){
            if(a==1){
                if(b.data.jumlahSuara[a]>b.data.jumlahSuara[0]){
                    jumlahWilayahMenang++;
                }
            }
            else{
                if(b.data.jumlahSuara[a]>b.data.jumlahSuara[1]){
                    jumlahWilayahMenang++;
                }
            }
        }
        prinan.add(Integer.toString(jumlahWilayahMenang));
    }

    public static void tambahSuara(Node<Wilayah> a, long b, long c){
        long selisihAwal = 0;
        long selisihBaru = 0;
        if(a.data.jumlahSuara[0]>a.data.jumlahSuara[1]){
            selisihAwal = a.data.jumlahSuara[0]-a.data.jumlahSuara[1];
        }else{
            selisihAwal = a.data.jumlahSuara[1]-a.data.jumlahSuara[0];
        }
        a.data.jumlahSuara[0] += b;
        a.data.jumlahSuara[1] += c;
        if(a.data.jumlahSuara[0]>a.data.jumlahSuara[1]){
            selisihBaru = a.data.jumlahSuara[0]-a.data.jumlahSuara[1];
        }else{
            selisihBaru = a.data.jumlahSuara[1]-a.data.jumlahSuara[0];
        }

        pohonAVL.deleteNode(pohonAVL.root,selisihAwal);
        pohonAVL.insert(selisihBaru);
        System.out.println("remove "+selisihAwal);
        System.out.println("add "+selisihBaru);
        
        if(a.parent != null){
            tambahSuara(a.parent, b, c);
        }
    }

    public static void kurangSuara(Node<Wilayah> a, long b, long c){
        long selisihAwal = 0;
        long selisihBaru = 0;
        if(a.data.jumlahSuara[0]>a.data.jumlahSuara[1]){
            selisihAwal = a.data.jumlahSuara[0]-a.data.jumlahSuara[1];
        }else{
            selisihAwal = a.data.jumlahSuara[1]-a.data.jumlahSuara[0];
        }
        a.data.jumlahSuara[0] -= b;
        a.data.jumlahSuara[1] -= c;
        if(a.data.jumlahSuara[0]>a.data.jumlahSuara[1]){
            selisihBaru = a.data.jumlahSuara[0]-a.data.jumlahSuara[1];
        }else{
            selisihBaru = a.data.jumlahSuara[1]-a.data.jumlahSuara[0];
        }

        pohonAVL.deleteNode(pohonAVL.root, selisihAwal);
        pohonAVL.insert(selisihBaru);

        System.out.println("remove "+selisihAwal);
        System.out.println("add "+selisihBaru);

        if(a.parent != null){
            kurangSuara(a.parent, b, c);
        }
    }

    public static void cekSuaraProvinsi(){
        for(Node<Wilayah> a:pohon.root.children){
            prinan.add(a+" "+a.data.jumlahSuara[0]+" "+a.data.jumlahSuara[1]);
        }
    }

    public static void wilayahMinimal(int a, int b){
        int counter = 0;
        for(Node<Wilayah> daerah : pohon.daftarWilayah.values()){
            double persen;
            if(daerah.data.jumlahSuara[0] == daerah.data.jumlahSuara[1]){
                persen = 50;
            }else{
                double totalDonat = (double)daerah.data.jumlahSuara[0]+daerah.data.jumlahSuara[1];
                persen = daerah.data.jumlahSuara[a]/totalDonat*100;
            }
            if(persen>=b){
                counter++;
            }
        }
        prinan.add(Integer.toString(counter));
    }

    public static int wilayahSelisih(AVLNode b,long a){
        if(b == null){
            return 0;
        }else if(b.data>=a){
            return b.count+wilayahSelisih(b.right, a)+wilayahSelisih(b.left, a);
        }else{
            return wilayahSelisih(b.right, a);
        }
    }



    static class InputReader {
        // taken from https://codeforces.com/submissions/Petr
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

        public long nextLong(){
            return Long.parseLong(next());
        }
 
    }

    static class Node<T> {
        T data;
        Node<T> parent;
        ArrayList<Node<T>> children;

        public Node(){
            this.data = null;
            this.parent = null;
            this.children = new ArrayList<Node<T>>();
        }

        public Node(T a){
            this.data = a;
            this.parent = null;
            this.children = new ArrayList<Node<T>>();
        }

        public String toString(){
            return this.data.toString();
        }
    }

    static class Wilayah{
        String id;
        long[] jumlahSuara;

        public Wilayah(String a){
            this.id = a;
            jumlahSuara = new long[]{0,0};
        }

        public String toString(){
            return this.id;
        }
    }

    static class Tree{
        Node<Wilayah> root;
        HashMap<String, Node<Wilayah>> daftarWilayah;

        public Tree(){
            root = null;
            daftarWilayah = new HashMap<String, Node<Wilayah>>();
        }

        public Tree(Wilayah a){
            root = new Node();
            root.data = a;
            root.children = new ArrayList<Node<Wilayah>>();
            root.parent = null;
            daftarWilayah = new HashMap<String, Node<Wilayah>>();
        }

        public void add(String a, Node<Wilayah> b){
            Node<Wilayah> c = new Node();
            c.data = new Wilayah(a);
            c.parent = b;
            b.children.add(c);
            this.daftarWilayah.put(a, c);
        }

        public void cetak(Node<Wilayah> a){
            System.out.println(a.data);
            ArrayList<Node<Wilayah>> child = a.children;
            System.out.println(child);
            for(Node<Wilayah> b : child){
                cetak(b);
            }
        }
    }


    static class AVLNode {
        AVLNode left, right;
        long data;
        int height;
        int count;

        public AVLNode() {
            left = null;
            right = null;
            data = 0;
            height = 0;
            count = 1;
        }

        public AVLNode(long a) {
            left = null;
            right = null;
            data = a;
            height = 0;
            count = 1;
        }
    }

    static class AVLTree {
        AVLNode root;

        public AVLTree() {
            root = null;
        }

        public void insert(long data) {
            this.root = insert(this.root, data);
        }

        public int height(AVLNode a) {
            if (a == null) {
                return -1;
            } else {
                return a.height;
            }
        }

        public long max(long a, long b) {
            if (a > b) {
                return a;
            } else {
                return b;
            }
        }

        public int max(int a, int b) {
            if (a > b) {
                return a;
            } else {
                return b;
            }
        }

        public AVLNode insert(AVLNode root, long data) {
            if (root == null) {
                root = new AVLNode(data);
            } else if (data < root.data) {
                root.left = insert(root.left, data);
                if (height(root.left) - height(root.right) == 2) {
                    if (data < root.left.data) {
                        root = rotateLeftChild(root);
                    } else {
                        root = doubleRotateLeftChild(root);
                    }
                }
            } else if (data > root.data) {
                root.right = insert(root.right, data);
                if (height(root.right) - height(root.left) == 2) {
                    if (data > root.right.data) {
                        root = rotateRightChild(root);
                    } else {
                        root = doubleRotateRightChild(root);
                    }
                }
            } else {
                root.count++;
            }

            root.height = max(height(root.left), height(root.right))+1;
            return root;
        }

        /* Functions to search for an element */
        public boolean search(int val) {
            return search(root, val);
        }

        private boolean search(AVLNode r, int val) {
            boolean found = false;
            while ((r != null) && !found) {
                long rval = r.data;
                if (val < rval)
                    r = r.left;
                else if (val > rval)
                    r = r.right;
                else {
                    found = true;
                    break;
                }
                found = search(r, val);
            }
            return found;
        }

        public int getBalance(AVLNode N) {
            if (N == null)
                return 0;
            return height(N.left) - height(N.right);
        }

        public AVLNode minValueNode(AVLNode root) {
            AVLNode current = root;

            /* loop down to find the leftmost leaf */
            while (current.left != null)
                current = current.left;

            return current;
        }

        public AVLNode deleteNode(AVLNode root, long value) {
            if (root == null) {
                return root;
            }
            if (value < root.data) {
                root.left = deleteNode(root.left, value);
            } else if (value > root.data) {
                root.right = deleteNode(root.right, value);
            } else {
                if (root.count > 1) {
                    root.count--;
                } else {
                    // node with only one child or no child
                    if ((root.left == null) || (root.right == null)) {
                        AVLNode temp = null;
                        if (temp == root.left)
                            temp = root.right;
                        else
                            temp = root.left;

                        // No child case
                        if (temp == null) {
                            temp = root;
                            root = null;
                        } else // One child case
                            root = temp; // Copy the contents of
                                         // the non-empty child
                    } else {
                        // node with two children: Get the inorder
                        // successor (smallest in the right subtree)
                        AVLNode temp = minValueNode(root.right);

                        // Copy the inorder successor's data to this node
                        root.data = temp.data;

                        // Delete the inorder successor
                        root.right = deleteNode(root.right, temp.data);
                    }
                }
            }

            // If the tree had only one node then return
            if (root == null)
                return root;

            // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
            root.height = max(height(root.left), height(root.right))+1;

            // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
            // this node became unbalanced)
            int balance = getBalance(root);

            // If this node becomes unbalanced, then there are 4 cases
            // Left Left Case
            if (balance > 1 && getBalance(root.left) >= 0)
                return rotateLeftChild(root);

            // Left Right Case
            if (balance > 1 && getBalance(root.left) < 0) {
                return doubleRotateLeftChild(root);
            }

            // Right Right Case
            if (balance < -1 && getBalance(root.right) <= 0)
                return rotateRightChild(root);

            // Right Left Case
            if (balance < -1 && getBalance(root.right) > 0) {

                return doubleRotateRightChild(root);
            }

            return root;
        }

        public AVLNode rotateLeftChild(AVLNode k2) {
            AVLNode k1 = k2.left;
            k2.left = k1.right;
            k1.right = k2;
            k2.height = max(height(k2.left), height(k2.right)) + 1;
            k1.height = max(height(k1.left), k2.height) + 1;
            return k1;
        }

        public AVLNode rotateRightChild(AVLNode k1) {
            AVLNode k2 = k1.right;
            k1.right = k2.left;
            k2.left = k1;
            k1.height = max(height(k1.left), height(k1.right)) + 1;
            k2.height = max(height(k2.right), k1.height) + 1;
            return k2;
        }

        public AVLNode doubleRotateLeftChild(AVLNode k3) {
            k3.left = rotateRightChild(k3.left);
            return rotateLeftChild(k3);
        }

        private AVLNode doubleRotateRightChild(AVLNode k1) {
            k1.right = rotateLeftChild(k1.right);
            return rotateRightChild(k1);
        }

        public int countNodes(AVLNode r) {
            if (r == null)
                return 0;
            else {
                int l = 1;
                l += countNodes(r.left);
                l += countNodes(r.right);
                return l;
            }
        }

        static void preOrder(AVLNode root) {
            if (root != null) {
                System.out.printf("%d(%d) ", root.data, root.count);
                preOrder(root.left);
                preOrder(root.right);
            }
        }
    }

}