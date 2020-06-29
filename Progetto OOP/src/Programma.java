import java.util.Vector;

public class Programma {

	public static void main(String[] args) {
		File f1 = new File(10);
		Decriptazione dec = new Decriptazione();
		Vector<double[]> sequenza = new Vector<double[]>();
		sequenza.add(dec.calcoloSequenza(f1.getDimensione()));
		System.out.println(sequenza.get(0));
		System.out.print(f1.getDimensione());

	}

}
