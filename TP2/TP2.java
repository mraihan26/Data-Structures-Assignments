import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class TP2 {

    private static InputReader in;
    private static PrintWriter out;
    private static LinkedList[] arr;
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        readInput();
        out.close();
    }

    public static boolean lebihKecil(LinkedList a, LinkedList b){
        if(a.size()<b.size()){
            for(int i = 0;i<a.size();i++){
                if((int)a.get(i)<(int)b.get(i)){
                    return true;
                }else if((int)a.get(i)>(int)b.get(i)){
                    return false;
                }
            }
            return true;
        }else{
            for(int i = 0;i<b.size();i++){
                if((int)a.get(i)<(int)b.get(i)){
                    return true;
                }else if((int)a.get(i)>(int)b.get(i)){
                    return false;
                }
            }
            return false;
        }
    }


    public static void cetak(LinkedList<Integer> a){
        for(int i = 0;i<a.size();i++){
            out.print(a.get(i));
            if(i!=a.size()-1){
                out.print(" ");
            }
        }
    }
    

    private static void sort(LinkedList[] arr){
        for (int ii = 1; ii < arr.length; ii++) {
            LinkedList temp = arr[ii];
            int jj = ii;
            while (( jj > 0) && (lebihKecil(temp,arr[jj - 1]))) {
                arr[jj] = arr[jj - 1];
                jj--;
            }
            arr[jj] = temp;
        }
    }
    private static void readInput() throws IOException {
        int jumlahBaris = in.nextInt();
        arr = new LinkedList[jumlahBaris];
        for(int i = 0;i<jumlahBaris;i++){
            int jumlahData = in.nextInt();
            arr[i] = new LinkedList<Integer>();
            for(int j = 0;j<jumlahData;j++){
                arr[i].add(in.nextInt());
            }
        }

        int jumlahInstruksi = in.nextInt();
        for(int i = 0;i<jumlahInstruksi;i++){
            String inst = in.next();
            if(inst.equals("IN_FRONT")||inst.equals("IN_BACK")){
                int chips = in.nextInt();
                int baris = in.nextInt()-1;
                if(inst.equals("IN_FRONT")){
                    arr[baris].addFirst(chips);
                }else{
                    arr[baris].addLast(chips);
                }
                sort(arr);
            }else if(inst.equals("MOVE_FRONT") || inst.equals("MOVE_BACK")){
                int awal = in.nextInt()-1;
                int akhir = in.nextInt()-1;
                if(inst.equals("MOVE_FRONT")){
                    arr[akhir].addAll(0, arr[awal]);
                    LinkedList b = arr[0];
                    arr[0] = arr[awal];
                    arr[awal] = b;
                    LinkedList[] arrBaru = new LinkedList[arr.length-1];
                    for(int j = 1;j<arr.length;j++){
                        arrBaru[j-1] = arr[j];
                    }
                    arr = arrBaru;
                }else{
                    arr[akhir].addAll(arr[akhir].size(), arr[awal]);
                    LinkedList b = arr[0];
                    arr[0] = arr[awal];
                    arr[awal] = b;
                    LinkedList[] arrBaru = new LinkedList[arr.length-1];
                    for(int j = 1;j<arr.length;j++){
                        arrBaru[j-1] = arr[j];
                    }
                    arr = arrBaru;
                }
                sort(arr);
            }else if(inst.equals("NEW")){
                int chips = in.nextInt();
                LinkedList[] arrBaru = new LinkedList[arr.length+1];
                for(int j = 0;j<arr.length;j++){
                    arrBaru[j] = arr[j];
                }
                arrBaru[arr.length] = new LinkedList<Integer>();
                arrBaru[arr.length].add(chips);
                arr = arrBaru;
                sort(arr);
            }else if(inst.equals("OUT_FRONT")||inst.equals("OUT_BACK")){
                int baris = in.nextInt()-1;
                if(inst.equals("OUT_FRONT")){
                    arr[baris].pollFirst();
                    if(arr[baris].isEmpty()){
                        LinkedList b = arr[0];
                        arr[0] = arr[baris];
                        arr[baris] = b;
                        LinkedList[] arrBaru = new LinkedList[arr.length-1];
                        for(int j = 1;j<arr.length;j++){
                            arrBaru[j-1] = arr[j];
                        }
                        arr = arrBaru;
                    }
                }else{
                    arr[baris].pollLast();
                    if(arr[baris].isEmpty()){
                        LinkedList b = arr[0];
                        arr[0] = arr[baris];
                        arr[baris] = b;
                        LinkedList[] arrBaru = new LinkedList[arr.length-1];
                        for(int j = 1;j<arr.length;j++){
                            arrBaru[j-1] = arr[j];
                        }
                        arr = arrBaru;
                    }
                }
                sort(arr);
            }
        }

        for(int i = 0;i<arr.length;i++){
            cetak(arr[i]);
            if(i!=arr.length-1)
            out.println();
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