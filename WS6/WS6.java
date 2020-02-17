import java.util.*;
import java.io.*;

interface Hashable {
    boolean equals(Hashable other);
    int getHash(int mod);
}

class Book {
    public String title, author, year;
    public Book(String title, String author, String year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }
}

class MyMap<K extends Hashable, V> {
    int size;
    K[] keys;
    V[] table;

    public MyMap(int size) {
        this.size = size;
        this.table = (V[]) new Object[size];
        this.keys = (K[]) new Hashable[size];
    }

    int put(K key, V value) {
        int hashValue = key.getHash(size);
        // System.out.println("hashnya"+hashValue);
        // TODO: Lengkapi dengan implementasi meletakkan buku berjudul key pada rak
        if(this.keys[hashValue]==null){
            keys[hashValue] = key;
            table[hashValue] = value;
            return hashValue;
        }else{
            while(this.keys[hashValue]!=null){
                if(hashValue<this.size-1){
                    hashValue++;
                }else{
                    hashValue = 0;
                }
            }
            keys[hashValue] = key;
            table[hashValue] = value;
            return hashValue;
        }
    }

    V get(K key) {
        int hashValue = key.getHash(size);
        // System.out.println("hashnya"+hashValue);
        // TODO: Lengkapi dengan implementasi mendapatkan informasi mengenai buku berjudul key
        for(int i = hashValue;i<this.size;i++){
            if(this.keys[i]!=null){
                if(key.equals(this.keys[i])){
                    return table[i];
                }
            }
        }
        for(int i = 0;i<hashValue;i++){
            if(this.keys[i]!=null){
                if(key.equals(this.keys[i])){
                    return table[i];
                }
            }
            
        }

        return null;
    }

    int remove(K key) {
        int hashValue = key.getHash(size);
        // System.out.println("hashnya"+hashValue);
        // TODO: Lengkapi dengan implementasi mengeluarkan buku berjudul key dari rak
        if(keys[hashValue].equals(key)){
            keys[hashValue] = null;
            table[hashValue] = null;
            return hashValue;
        }else{
            for(int i = hashValue;i<size;i++){
                if(this.keys[i]!=null){
                    if(this.keys[i].equals(key)){
                        keys[i] = null;
                        table[i] = null;
                        return i;
                    }
                }
            }

            for(int i = 0;i<hashValue;i++){
                if(this.keys[i]!=null){
                    if(this.keys[i].equals(key)){
                        keys[i] = null;
                        table[i] = null;
                        return i;
                    }
                }
            }

            return -1;
        }
    }
}

class MyString implements Hashable {
    private final int BASE = 29;
    public String s;

    public MyString(String s) {
        this.s = s;
    }

    public int getHash(int mod) {
        // TODO: Lengkapi dengan fungsi f(title, N)
        int counter = 0;
        int duaSembilan = 1;
        for(int i = 0;i<this.s.length();i++){
            int nilaiHuruf = (int)this.s.charAt(i)-96;
            if(i!=0){
                duaSembilan = duaSembilan * 29;
            }
            counter+=(nilaiHuruf*duaSembilan)%mod;
        }
        return counter%mod;
    }

    public boolean equals(Hashable other) {
        return s.equals(((MyString) other).s);
    }
}

public class WS6{
    static InputReader in = new InputReader(System.in);
    static PrintWriter out = new PrintWriter(System.out);
    
    public static void main(String[] args) {
        int N = in.nextInt();
        int Q = in.nextInt();

        MyMap<MyString, Book> myMap = new MyMap<>(N);

        for (int i = 0; i < Q; i++) {
            String command = in.next();
            if(command.equals("RECEIVE")){
                String title = in.next();
                String author = in.next();
                String year = in.next();
                MyString judulBuku = new MyString(title);
                Book newBook = new Book(title, author, year);
                out.println(myMap.put(judulBuku, newBook));
            }else if(command.equals("SEND")){
                String title = in.next();
                MyString judul = new MyString(title);
                out.println(myMap.remove(judul));
            }else{
                String title = in.next();
                MyString judul = new MyString(title);
                Book buku = myMap.get(judul);
                if(buku!= null){
                    out.println(buku.author + " " + buku.year);
                }
            }

           // TODO: Lengkapi dengan memproses jenis command yang diterima
        }

        out.close();
    }
    
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

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

    }
}