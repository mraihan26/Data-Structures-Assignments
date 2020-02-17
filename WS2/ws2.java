import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;

/**
 * Anda hanya ditugaskan untuk menyelesaikan implementasi processQuery.
 * Anda dapat menggunakan variable n dan q dari global variable yang
 * nilainya telah diisi pada method readInput. Anda bebas menambahkan maupun
 * merubah fungsi, variable, dll dalam template ini
 */
public class ws2 {
    private static int n, q;
    
    private static InputReader in;
    private static PrintWriter out;

    private static Stack ruangTunggu = new Stack();
    private static Queue antrian = new LinkedList();
    private static boolean[] tiket = new boolean[100000];

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);


        readInput();
        out.close();
    }
        
    private static void processQuery(int query) throws IOException{
        // TODO: selesaikan fungsi ini agar dapat mensimulasikan perintah
        // dari Borman, jika ada pelanggan yang mendapatkan sate dan
        // ingin diprint maka menggunakan "printOutput(...)"

        if(query == 1){
            int terdepan = (int)antrian.poll();
            if(tiket[terdepan]){
                printOutput(terdepan);
            }else{
                tiket[terdepan] = true;
                ruangTunggu.push(terdepan);
            }
        }else if(query == 2){
            int terdepan = (int)antrian.poll();
            if(tiket[terdepan]){
                printOutput(terdepan);
            }else{
                tiket[terdepan] = true;
                antrian.add(terdepan);
            }
        }else{
            Queue diRuangTunggu = new LinkedList();
            while(!ruangTunggu.isEmpty()){
                diRuangTunggu.add(ruangTunggu.pop());
            }
            while(!diRuangTunggu.isEmpty()){
                antrian.add(diRuangTunggu.poll());
            }
        }

    }
    
    private static void printOutput(int answer) throws IOException {
        out.println(answer);
    }
    
    private static void readInput() throws IOException {
        n = in.nextInt();
        for(int i = 1;i<n+1;i++){
            antrian.add(i);
        }
        q = in.nextInt();
        if(!antrian.isEmpty()){
            for (int i = 0; i < q; i++) {
                int query = in.nextInt();
                processQuery(query);
            }
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
 
    }
}
