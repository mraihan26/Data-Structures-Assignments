import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class TP1 {

    private static HashMap<String, Toko> mapToko;
    private static InputReader in;
    private static PrintWriter out;
    private static ArrayList<Integer> banyakCara = new ArrayList<Integer>();

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        readInput();
        for (int i : banyakCara) {
            System.out.println(i);
        }
        out.close();
    }

    public static void Duar(String namatoko, String namadonat, int jumlah) {
        mapToko.get(namatoko).donat.get(namadonat).boom(jumlah);
        if (mapToko.get(namatoko).donat.get(namadonat).stok == 0) {
            mapToko.get(namatoko).donat.remove(namadonat);
        }
    }

    public static void isiUlang(String toko, String donat, int jumlDonat, int jumlChip) {
        if (mapToko.get(toko).donat.get(donat) != null) {
            if (mapToko.get(toko).donat.get(donat).jumlahChip == jumlChip) {
                mapToko.get(toko).donat.get(donat).tambah(jumlDonat);
            }
        } else {
            mapToko.get(toko).donat.put(donat, new Donut(donat, jumlDonat, jumlChip));
        }
    }

    public static void transfer(String toko1, String toko2, String namaDonat, int jmlDonat) {
        if (mapToko.get(toko2).donat.get(namaDonat) != null) {
            if (mapToko.get(toko1).donat.get(namaDonat).jumlahChip == mapToko.get(toko2).donat
                    .get(namaDonat).jumlahChip) {
                Duar(toko1, namaDonat, jmlDonat);
                mapToko.get(toko2).donat.get(namaDonat).tambah(jmlDonat);
            }
        } else {
            mapToko.get(toko2).donat.put(namaDonat,
                    new Donut(namaDonat, jmlDonat, mapToko.get(toko1).donat.get(namaDonat).jumlahChip));
            Duar(toko1, namaDonat, jmlDonat);
        }
    }

    // dibantu oleh : Abhipraya Tjondronegoro, Muhammad Daril Nofriansyah
    // Baddruddin, Salman Al-Farisi
    public static int target(ArrayList<Integer> chipdonat, ArrayList<Integer> jmlDonat, int mauDonat, int pointer,
            int[][] memo) {
        int kemungkinan = 0;
        if (mauDonat == 0) {
            return 1;
        }

        if (mauDonat < 0) {
            return 0;
        }

        if (mauDonat > 0 && chipdonat.size() == 0) {
            return 0;
        }

        if (memo[mauDonat][pointer] != 0) {
            return memo[mauDonat][pointer];
        }

        if (pointer == chipdonat.size()) {
            return 0;
        }

        for (int i = 0; i <= jmlDonat.get(pointer); i++) {
            kemungkinan += target(chipdonat, jmlDonat, (mauDonat - (i * chipdonat.get(pointer))), pointer + 1, memo);
            kemungkinan = kemungkinan % 1000000007;
            memo[mauDonat][pointer] = kemungkinan;
        }

        return kemungkinan % 1000000007;
    }

    private static void readInput() throws IOException {
        int jumlahToko = in.nextInt();
        mapToko = new HashMap<String, Toko>();
        for (int i = 0; i < jumlahToko; i++) {
            String namaToko = in.next();
            int jenisDonat = in.nextInt();
            Toko tokoBaru = new Toko(namaToko);
            for (int j = 0; j < jenisDonat; j++) {
                String namaDonat = in.next();
                int stok = in.nextInt();
                int chip = in.nextInt();
                tokoBaru.donat.put(namaDonat, new Donut(namaDonat, stok, chip));
            }
            mapToko.put(tokoBaru.nama, tokoBaru);
        }

        int jumlahHari = in.nextInt();
        for (int i = 0; i < jumlahHari; i++) {
            int tokoBuka = in.nextInt();
            ArrayList<Integer> chipDonat = new ArrayList<Integer>();
            ArrayList<Integer> stokDonat = new ArrayList<Integer>();
            for (int j = 0; j < tokoBuka; j++) {
                String namaTokoBuka = in.next();
                mapToko.get(namaTokoBuka).buka();
                for (Donut a : mapToko.get(namaTokoBuka).donat.values()) {
                    chipDonat.add(a.jumlahChip);
                    stokDonat.add(a.stok);
                }
            }

            String target = in.next();
            int mauDonat = in.nextInt();
            int[][] banyak = new int[400][chipDonat.size() + 5];

            int cara = target(chipDonat, stokDonat, mauDonat, 0, banyak);
            banyakCara.add(cara);
            chipDonat.clear();
            stokDonat.clear();

            String duar = in.next();
            int meledak = in.nextInt();
            for (int k = 0; k < meledak; k++) {
                String namaToko = in.next();
                String namaDonat = in.next();
                int jmlMeledak = in.nextInt();
                Duar(namaToko, namaDonat, jmlMeledak);
            }

            String rstk = in.next();
            int isi = in.nextInt();
            for (int k = 0; k < isi; k++) {
                String namaToko = in.next();
                String jenisDonat = in.next();
                int jmlDonat = in.nextInt();
                int jmlChip = in.nextInt();
                isiUlang(namaToko, jenisDonat, jmlDonat, jmlChip);
            }

            String trf = in.next();
            int pindah = in.nextInt();
            for (int k = 0; k < pindah; k++) {
                String tokoAwal = in.next();
                String tokoAkhir = in.next();
                String namaDonat = in.next();
                int jmlDonat = in.nextInt();
                transfer(tokoAwal, tokoAkhir, namaDonat, jmlDonat);
            }

            for (Toko j : mapToko.values()) {
                j.tutup();
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

    static class Donut {
        String nama;
        int stok;
        int jumlahChip;

        public Donut(String name, int stock, int chip) {
            this.nama = name;
            this.stok = stock;
            this.jumlahChip = chip;
        }

        public String toString() {
            return this.nama + "-" + this.stok + "-" + this.jumlahChip;
        }

        public void boom(int a) {
            if (this.stok >= a) {
                this.stok = this.stok - a;
            } else {
                this.stok = 0;
            }
        }

        public void tambah(int a) {
            if (this.stok + a <= 100) {
                this.stok += a;
            }
        }
    }

    static class Toko {
        String nama;
        HashMap<String, Donut> donat;
        Boolean open = false;

        public Toko(String name) {
            this.nama = name;
            this.donat = new HashMap<String, Donut>();
        }

        public String toString() {
            return this.nama;
        }

        public boolean bukaApaEngga() {
            return this.open;
        }

        public void buka() {
            this.open = true;
        }

        public void tutup() {
            this.open = false;
        }

    }
}
