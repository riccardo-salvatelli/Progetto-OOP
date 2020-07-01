import java.io.BufferedOutputStream;
import java.io.File;


public class testDecriptazione {
    public static void main(String[] args) {
        File a = new File("/home/riccardo/Scaricati/pippo.pdf");
        Decriptazione decripta = new Decriptazione();

        int[] testSequenza = new int[(int)a.length()];
        for (int i=0; i<a.length();i++){
            testSequenza[i] = i%250;
        }
        BufferedOutputStream e = decripta.chaosXOR(a, testSequenza);
    }
}
