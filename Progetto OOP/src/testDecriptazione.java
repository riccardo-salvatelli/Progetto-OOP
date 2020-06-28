import java.io.BufferedOutputStream;
import java.io.File;
import module.Decriptazione;


public class testDecriptazione {
    public static void main(String[] args) {
        File a = new File("/home/riccardo/Scaricati/pippo.pdf");
        Decriptazione decripta = new Decriptazione();

        int[] testSequenza = new int[3028];
        for (int i=0; i<3028;i++){
            testSequenza[i] = i;
        }
        BufferedOutputStream e = decripta.chaosXOR(a, testSequenza);
    }
}
