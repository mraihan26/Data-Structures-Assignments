import java.io.*;

public class ws1{
    /**
     * WARNING : Mengubah implementasi fungsi ini tidak disarankan. Implementasikan
     * kode kamu pada method getBulatPanjangCount!
     */
    public static void main(String[] args) throws IOException {
        /*
         * Kita menggunakan BufferedWriter dan BufferedReader dikarenakan kedua objek
         * ini lebih efisien dalam melakukan operasi IO. Namun objek tersebut tidak
         * terdapat helper function seperti nextInt, nextFloat, dll.
         *
         * Bacaan lebih lanjut :
         * https://medium.com/@isaacjumba/why-use-bufferedreader-and-bufferedwriter-classses-in-java-39074ee1a966
         */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] input = br.readLine().split(" ");

        String binaryString = input[0];
        int k = Integer.parseInt(input[1]);

        int bulatPanjangCount = getBulatPanjangCount(binaryString, k);

        /*
         * Pada bw.write(int c), c merepresentasikan int ascii. Oleh karena itu, kita
         * harus mengkonversi int menjadi sebuah string.
         *
         * Jangan lupa melakukan bw.flush()
         */
        bw.write(String.valueOf(bulatPanjangCount));
        bw.flush();
    }
    
    public static int getBulatPanjangCount(String binaryString, int k) {
        // TODO : Bantu borman star!
        int jumlahTerpanjang = 0;
        int jumlahSementara = 0;
        int jumlahNol = 0;
        int indexNolPertama = binaryString.indexOf("0");
        int indexNolKedua = binaryString.indexOf("0",indexNolPertama+1);
        for(int i = 0;i<binaryString.length();i++){
            if(binaryString.charAt(i) == '0'){
                if(jumlahNol <k){
                    jumlahSementara++;
                    jumlahTerpanjang = jumlahSementara;
                }else{
                    jumlahSementara = jumlahSementara - indexNolKedua + indexNolPertama + 1;
                    if(jumlahSementara>jumlahTerpanjang){
                        jumlahTerpanjang = jumlahSementara;
                    }
                }
            }else{
                if(jumlahNol<k){
                    jumlahSementara++;
                    jumlahTerpanjang++;
                }else{
                    
                }
            }
        }
        if(jumlahNol == k){
            return jumlahTerpanjang;
        }else{
            return 0;
        }    
    }

}