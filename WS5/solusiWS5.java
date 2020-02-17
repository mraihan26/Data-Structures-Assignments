import java.util.*;
import java.io.*;

class MaxHeap {
    List<Integer> heap;
    int level = 0;
    int jumlah = 0;

    public MaxHeap(){
        heap = new ArrayList<Integer>();
    }
    
    public void add(int val) {
        // TODO: lengkapi dengan cara menambah elemen ke maximum binary heap.
        heap.add(val);
        percolateUp();
    }
    
    public int poll() {
        // hapus elemen teratas, tukar dengan elemen terakhir
        int result = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        
        // percolate down
        int now = 0;
        while (2*now + 1 < heap.size()) {
            int maxIdx = 2*now + 1;
            if (2*now + 2 < heap.size() && heap.get(2*now + 2) > heap.get(2*now + 1)) {
                maxIdx = 2*now + 2;
            }
            if (heap.get(now) < heap.get(maxIdx)) {
                // swap elemen
                int temp = heap.get(maxIdx);
                heap.set(maxIdx, heap.get(now));
                heap.set(now, temp);
                
                // lanjut ke bawah
                now = maxIdx;
            } else {
                break;
            }
        }
        
        return result;
    }

    public void percolateUp(){
        int i = heap.size()-1;
        while(true){
            if(heap.get(i)>heap.get((i-1)/2)&&i == 0){
                int temp = heap.get(i);
                heap.set(i, heap.get((i-1)/2));
                heap.set((i-1)/2, temp);
                i = (i-1)/2;
                break;
            }else if(heap.get(i)>heap.get((i-1)/2)){
                int temp = heap.get(i);
                heap.set(i, heap.get((i-1)/2));
                heap.set((i-1)/2, temp);
                i = (i-1)/2;
            }else{
                break;
            }
        }
    }
}

public class solusiWS5 {
    static InputReader in = new InputReader(System.in);
    static PrintWriter out = new PrintWriter(System.out);
    
    public static void main(String[] args) {
        int N = in.nextInt();
        List<Integer> B = new ArrayList<>();
        List<Integer> G = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            B.add(in.nextInt());
        }
        for (int i = 0; i < N; i++) {
            G.add(in.nextInt());
        }

        MaxHeap teri = new MaxHeap();
        MaxHeap teri2 = new MaxHeap();
        int counter = 0;
        int coin = 0;
        for(int i = 0;i<N;i++){
            teri.add(G.get(i));
            teri2.add(G.get(i));
            if(coin<B.get(i)){
                while(coin<B.get(i)){
                    coin+=teri2.poll();
                    counter++;
                }
                coin-=B.get(i);
            }else{
                coin-=B.get(i);
            }
        }

        out.println(counter);
        
        // TODO: lengkapi dengan memasukkan elemen-elemen G dan cetak representasi array heap-nya.

        for(int i = 0;i<N;i++){
            out.print(teri.heap.get(i));
            if(i != teri.heap.size()-1){
                out.print(" ");
            }
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