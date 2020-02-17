import java.util.Scanner;

public class WS0_MuhammadRaihan_A{
	public static String balik(String a){
		String baru = "";
		for(int i= a.length()-1;i>=0;i--){
			baru += a.charAt(i);
		}
		return baru;
	}
	
	public static void main(String[] args1){
		Scanner input = new Scanner(System.in);
		String kata = input.nextLine();
		System.out.println(balik(kata));
	}
}