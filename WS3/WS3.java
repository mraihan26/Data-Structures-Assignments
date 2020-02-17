import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.lang.Comparable;

/**
 * Pada template ini, terdapat class Participant yang perlu diimplementasikan.
 * Setelah itu, implementasikan solusi kamu pada class Solution Semangat semuaa
 * :)
 */
public class WS3 {

    // Kamu boleh menambahkan instance variable atau method lain jika dirasa perlu
    static InputReader in;
    static PrintWriter out;
    static int n, t;
    static Participant[] participants;
    static Participant[] ori;

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        String[] nt = in.nextLine().split(" ");
        n = Integer.parseInt(nt[0]);
        t = Integer.parseInt(nt[1]);
        participants = new Participant[n + 1];
        ori = new Participant[n+1];
        

        for (int i = 1; i <= n; i++) {
            int listeningScore, readingScore, writingScore;
            String[] scores = in.nextLine().split(" ");
            listeningScore = Integer.parseInt(scores[0]);
            readingScore = Integer.parseInt(scores[1]);
            writingScore = Integer.parseInt(scores[2]);
            Participant orang = new Participant(i, listeningScore, readingScore, writingScore);
            participants[i] = orang;
            ori[i] = orang;
        }
        for (int i = 1; i < participants.length; i++) {
            int max = i;
            for (int j = i + 1; j < participants.length; j++) {
                if (participants[j].compareTo(participants[max]) == 1) {
                    max = j;
                }
            }

            if (max != i) {
                participants[i].tukar++;
                participants[max].tukar++;
            }
            Participant temp = participants[max];
            participants[max] = participants[i];
            participants[i] = temp;
        }

        if (t == 1) {
            for (int i = 1; i < participants.length; i++) {
                printOutput(participants[i].nama);
            }
        } else {
            for (int i = 1; i < participants.length; i++) {
                printOutput(ori[i].tukar);
            }
        }

        /**
         * TODO : implementasikan solusi dibawah ini Kamu juga boleh menambahkan kode
         * pada template diatas jika perlu
         */

        // Jangan dihapus
        out.close();
    }

    private static void printOutput(int answer) throws IOException {
        out.println(answer);
    }

    /**
     * faster Input, taken from https://codeforces.com/submissions/Petr
     */
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

        public String nextLine() throws IOException {
            return reader.readLine();
        }
    }
}

/**
 * class Participant berikut merepresentasikan peserta tes, implementasikan!
 */
class Participant implements Comparable<Participant> {

    int nama;
    int listening;
    int reading;
    int writing;
    int tukar;

    /**
     * TODO : implementasikan class Participant, tambahkan instance variable beserta
     * Constructor yang diperlukan boleh menambahkan method lain juga jika dirasa
     * perlu
     */

    public Participant(int nama, int nilaiListening, int nilaiReading, int nilaiWriting) {
        this.nama = nama;
        this.listening = nilaiListening;
        this.reading = nilaiReading;
        this.writing = nilaiWriting;
        this.tukar = 0;
    }

    /**
     * TODO : implementasikan fungsi compareTo dibawah untuk membandingkan nilai
     * antar peserta
     */
    @Override
    public int compareTo(Participant other) {
        if(this.writing==other.writing){
            if(this.reading==other.reading){
                if(this.listening > other.listening){
                    return 1;
                }else{
                    return -1;
                }
            }else if(this.reading>other.reading){
                return 1;
            }else{
                return -1;
            }

        }else if(this.writing>other.writing){
            return 1;
        }else{
            return -1;
        }

    }}
