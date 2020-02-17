 import java.io.*;
import java.util.*;

/*
* Class Graph memiliki properti int JUMLAH_VERTEX, merupakan jumlah vertex pada graf
*
* Vertex pada graf dilabeli menggunakan bilangan bulat 0 sampai dengan JUMLAH_VERTEX-1. 
*
* Adjacency List direpresentasikan menggunakan array of ArrayList. Index pada array merepresentasikan vertex i. ArrayList berisi bilangan-bilangan bulat, merepresentasikan vertex yang terhubung dengan vertex i.
* 
* Contoh:
*  (3)    (0)        	|	array[0] => 1 2
*    \   /   \			|	array[1] => 0 2
*     \ /     \			|	array[2] => 0 1 3
*     (2)-----(1)		|	array[3] => 2
*/

class Edge{
    public int destination;
    public int weight;

    public Edge(int tujuan, int berat){
        this.destination = tujuan;
        this.weight = berat;
    }

    public String toString(){
        return this.destination + "+ " + this.weight;
    }
}

class Graph{
    private static int JUMLAH_VERTEX;
    public static ArrayList<Integer> adjList[];
    public static ArrayList<Edge> berat[];
    public int[] jarakVertex; 
    public static boolean[] flag;
    public int[] distance;
    public int[] previous;
  

    Graph(int v){
        JUMLAH_VERTEX = v; 
        adjList = new ArrayList[JUMLAH_VERTEX+1]; 
        berat = new ArrayList[JUMLAH_VERTEX+1];
        for (int i=0; i<JUMLAH_VERTEX+1; i++){ 
            adjList[i] = new ArrayList(); 
            berat[i] = new ArrayList();
        }
        khususOS = new boolean[JUMLAH_VERTEX+1];
        jarakVertex = new int[JUMLAH_VERTEX+1];
        flag = new boolean[JUMLAH_VERTEX+1];
        distance= new int[JUMLAH_VERTEX+1];
        previous = new int[JUMLAH_VERTEX+1];
    }

    void printGraph(){
        for(int i=0; i<JUMLAH_VERTEX; i++){
            Iterator<Integer> itr = adjList[i].listIterator();
            System.out.print(i+" => ");
            while(itr.hasNext()){
                System.out.print(itr.next()+" ");
            }
            System.out.println("");
        }
    }

    void addEdge(int v, int w, int x){
        adjList[v].add(w);
        adjList[w].add(v);
        berat[v].add(new Edge(w, x));
        berat[w].add(new Edge(v,x));
    } 

    void openStore(int a){
        ArrayDeque<Integer> Q = new ArrayDeque<Integer>();
        if(!flag[a]){
            flag[a] = true;
            Q.add(a);
        }
        while(!Q.isEmpty()){
            int v = Q.poll();
            for(int b:adjList[v]){
                if(!flag[b]){
                    flag[b] = true;
                    Q.add(b);
                }
            }
        }
    }

    int cecewegede(int a, int b){
        int counter = 0;
        for(int i = 0;i<JUMLAH_VERTEX;i++){
            flag[i] = false;
        }
        ArrayDeque<Integer> Q = new ArrayDeque<Integer>();
        flag[a]=true;
        Q.add(a);
        while(!Q.isEmpty()){
            int v = Q.poll();
            for(int c:adjList[v]){
                for(Edge k:berat[v]){
                    if(!flag[c]&&k.destination==c&&k.weight<=b){
                        flag[c] = true;
                        Q.add(c);
                        counter++;
                    }
                }
            }
        }

        return counter;

    }



    int laor(int V, int tujuan){
        int counter = 0;
        for(int i = 0; i<adjList.length;i++){
            flag[i] = false;
            jarakVertex[i] = -1;
        }
        ArrayDeque<Integer> Q = new ArrayDeque<Integer>();
        flag[V] = true;
        Q.add(V);
        while(!Q.isEmpty()){
            // counter++;
            int v = Q.poll();
            if(v==V){
                counter = 1;
            }else{
                counter = jarakVertex[v]+1;
            }
            // System.out.println(v);
            for(int a : adjList[v]){
                if(!flag[a]){
                    flag[a] = true;
                    jarakVertex[a] = counter;
                    Q.add(a);
                }
            }
        }
        return jarakVertex[tujuan];
    }

    //mengutip dari GEEKS FOR GEEKS
    int laorc(int asal, int tujuan){
        int[] jauh = new int[JUMLAH_VERTEX+1];
        int[] banyak = new int[JUMLAH_VERTEX+1];
        for(int i = 0;i<JUMLAH_VERTEX+1;i++){
            jauh[i] = Integer.MAX_VALUE;
            banyak[i] = 0;
        }
        bfsKhususLAORC(asal, jauh, banyak);
        return banyak[tujuan]%10001;
    }

    void bfsKhususLAORC(int a, int[] b, int[] c){
        for(int i =0;i<JUMLAH_VERTEX+1;i++){
            flag[i] = false;
        }
        b[a] = 0;
        c[a] = 1;

        ArrayDeque<Integer> Q = new ArrayDeque<Integer>();
        flag[a] = true;
        Q.add(a);

        while(!Q.isEmpty()){
            int v = Q.poll();
            for(int x : adjList[v]){
                if(!flag[x]){
                    flag[x] = true;
                    Q.add(x);
                }
                if(b[x]>b[v]+1){
                    b[x] = b[v]+1;
                    c[x] = c[v];
                }else if(b[x] == b[v]+1){
                    c[x]+=c[v]%10001;
                }
            }
        }
    }

    //EH OM APAKABAR BROOOO!!!!! 
    int dijkstra(int asal, int akhir){
        PriorityQueue<Integer> Q = new PriorityQueue<Integer>();
        for(int i = 0;i<JUMLAH_VERTEX+1;i++){
            flag[i] = false;
            distance[i] = Integer.MAX_VALUE;
            previous[i] = -1;
        }

        int nodeSeen = 0;
        distance[asal] = 0;
        Q.add(asal);
        while(!Q.isEmpty()&&nodeSeen<JUMLAH_VERTEX){
            int current = Q.poll();
            if(!flag[current]){
                flag[current] = true;
                nodeSeen++;
                for(Edge a:berat[current]){
                    int tujuan = a.destination;
                    int weight = a.weight;
                    if(distance[tujuan]>distance[current]+weight){
                        distance[tujuan] = distance[current]+weight;
                        previous[tujuan] = current;
                        Q.add(tujuan);
                    }
                }
            }
        }
        return distance[akhir];
    }

    int[] djikstraKhususSM(int asal){
        PriorityQueue<Integer> Q = new PriorityQueue<Integer>();
        for(int i = 0;i<JUMLAH_VERTEX+1;i++){
            flag[i] = false;
            distance[i] = Integer.MAX_VALUE;
            previous[i] = -1;
        }

        int nodeSeen = 0;
        distance[asal] = 0;
        Q.add(asal);
        while(!Q.isEmpty()&&nodeSeen<JUMLAH_VERTEX){
            int current = Q.poll();
            if(!flag[current]){
                flag[current] = true;
                nodeSeen++;
                for(Edge a:berat[current]){
                    int tujuan = a.destination;
                    int weight = a.weight;
                    if(distance[tujuan]>distance[current]+weight){
                        distance[tujuan] = distance[current]+weight;
                        previous[tujuan] = current;
                        Q.add(tujuan);
                    }
                }
            }
        }

        return distance;
    }


}

public class tp4{
    static InputReader in = new InputReader(System.in);
    static PrintWriter out = new PrintWriter(System.out);

    public static void main(String[] args) throws IOException{
        int jmlVertex = in.nextInt();
        int jmlEdge = in.nextInt();
        int jmlInstruksi = in.nextInt();

        Graph graf = new Graph(jmlVertex);

        for(int i = 0; i < jmlEdge; i++){
            graf.addEdge(in.nextInt(), in.nextInt(), in.nextInt());
        }

        for(int i = 0;i<jmlInstruksi;i++){
            String inst = in.nextLine();
            String[] inputan = inst.split(" ");
            if(inputan[0].equals("OS")){
                int counter = 0;
                for(int k = 0;k<jmlVertex+1;k++){
                    graf.flag[k]=false;
                }

                for(int k = 1;k<inputan.length;k++){
                    graf.openStore(Integer.parseInt(inputan[k]));
                }

                for(boolean a : graf.flag){
                    if(a)
                    counter++;
                }
                System.out.println(counter);
                // out.println(counter);
            }else if(inputan[0].equals("CCWGD")){
                int asal = Integer.parseInt(inputan[1]);
                int max = Integer.parseInt(inputan[2]);
                System.out.println(graf.cecewegede(asal,max));
            }else if(inputan[0].equals("LAOR")){
                int asal = Integer.parseInt(inputan[1]);
                int tujuan = Integer.parseInt(inputan[2]);
                System.out.println(graf.laor(asal, tujuan));
            }else if(inputan[0].equals("LAORC")){
                int asal = Integer.parseInt(inputan[1]);
                int tujuan = Integer.parseInt(inputan[2]);
                System.out.println(graf.laorc(asal, tujuan));
            }else if(inputan[0].equals("SR")){
                int jarak = graf.dijkstra(Integer.parseInt(inputan[1]), Integer.parseInt(inputan[2]));
                if(jarak == Integer.MAX_VALUE){
                    System.out.println(-1);
                }else{
                    System.out.println(jarak);
                }
            }else if(inputan[0].equals("SM")){
                int palingKecil = Integer.MAX_VALUE;
                int[] jarakBorman = Arrays.copyOf(graf.djikstraKhususSM(Integer.parseInt(inputan[1])), jmlVertex+1);
                int[] jarakNorman = Arrays.copyOf(graf.djikstraKhususSM(Integer.parseInt(inputan[2])), jmlVertex+1);
                for(int k = 1;k<jmlVertex+1;k++){
                    if(jarakBorman[k]!=Integer.MAX_VALUE&&jarakNorman[k]!=Integer.MAX_VALUE){
                        if(jarakBorman[k]>jarakNorman[k]){
                            if(jarakBorman[k]<palingKecil){
                                palingKecil = jarakBorman[k];
                            }
                        }else{
                            if(jarakNorman[k]<palingKecil){
                                palingKecil = jarakNorman[k];
                            }
                        }
                    }
                }
                if(palingKecil==Integer.MAX_VALUE){
                    System.out.println(-1);
                }else{
                    System.out.println(palingKecil);
                }
                
            }
        }


        //TO-DO
        //mencetak vertex yang memiliki jarak tertentu terhadap vertex awal.
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

        public String nextLine() throws IOException{
            return reader.readLine();
        }

    }
} 