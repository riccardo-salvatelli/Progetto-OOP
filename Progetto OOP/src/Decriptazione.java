
public class Decriptazione {
	static double rho = 0.6;
	static double w = 5.9;
	static double k = 19.5;

	public int[][] calcoloSequenza(int dimensione, double chiave1, double chiave2) {
		
		
		double h = 0.01;
		int n = 100000;

		double[] x = new double[n];
		double[] y = new double[n];

		x[0] = -3;
		y[0] = 2;
		
		if(chiave1==x[0] && chiave2==y[0]) {
			
		}
		
		
		double[] autoval1 = new double[n];
		double[] autoval2 = new double[n];

		double[] t = new double[(int) (n * h)];

		for (int i = 1; i < (int) (n * h); i++) {
			t[i] = t[i - 1] + h;
		}

		double k1x;
		double k2x;
		double k3x;
		double k4x;
		double k1y;
		double k2y;
		double k3y;
		double k4y;
		boolean controllo1 = true;
		boolean controllo2 = true;

		for (int i = 0; i < n; i++) {
			k1x = Decriptazione.f1(t[i], x[i], y[i]);
			k1y = Decriptazione.f2(t[i], x[i], y[i]);

			k2x = Decriptazione.f1(t[i] + 0.5 * h, x[i] + 0.5 * h * k1x, y[i] + 0.5 * h * k1y);
			k2y = Decriptazione.f2(t[i] + 0.5 * h, x[i] + 0.5 * h * k1x, y[i] + 0.5 * h * k1y);

			k3x = Decriptazione.f1(t[i] + 0.5 * h, x[i] + 0.5 * h * k2x, y[i] + 0.5 * h * k2y);
			k3y = Decriptazione.f2(t[i] + 0.5 * h, x[i] + 0.5 * h * k2x, y[i] + 0.5 * h * k2y);

			k4x = Decriptazione.f1(t[i] + h, x[i] + k3x * h, y[i] + k3y * h);
			k4y = Decriptazione.f2(t[i] + h, x[i] + k3x * h, y[i] + k3y * h);

			x[i + 1] = x[i] + (1.0 / 6.0) * (k1x + 2 * k2x + 2 * k3x + k4x) * h;
			y[i + 1] = y[i] + (1.0 / 6.0) * (k1y + 2 * k2y + 2 * k3y + k4y) * h;

			autoval1[i] = rho - 2 * (Math.pow(rho, 2)) * (Math.pow(x[i], 2) + Math.sqrt(
					Math.pow(rho - 2 * rho * Math.pow(x[i], 2), 2) - (k * Math.sin(w * t[i]) - 4 * rho * x[i] * y[i])));
			autoval2[i] = rho - 2 * (Math.pow(rho, 2)) * (Math.pow(x[i], 2) - Math.sqrt(
					Math.pow(rho - 2 * rho * Math.pow(x[i], 2), 2) - (k * Math.sin(w * t[i]) - 4 * rho * x[i] * y[i])));
			if (controllo1 & autoval1[i] < 0) {
				controllo1 = false;
			}
			if (controllo2 & autoval2[i] > 0) {
				controllo2 = false;
			}
			if (!(controllo1 | controllo2)) {
				double appoggio = x[i];
				x = new double[dimensione];
				x[0] = appoggio;
				appoggio = y[i];
				y = new double[dimensione];
				y[0] = appoggio;
				t = new double[(int) (dimensione / h)];
				for (int j = 1; j < (int) (dimensione / h); j++) {
					t[j] = t[j - 1] + h;
				}
				break;
			}
		}
		for (int i = 0; i < dimensione - 1; i++) {

			k1x = Decriptazione.f1(t[i], x[i], y[i]);
			k1y = Decriptazione.f2(t[i], x[i], y[i]);

			k2x = Decriptazione.f1(t[i] + 0.5 * h, x[i] + 0.5 * h * k1x, y[i] + 0.5 * h * k1y);
			k2y = Decriptazione.f2(t[i] + 0.5 * h, x[i] + 0.5 * h * k1x, y[i] + 0.5 * h * k1y);

			k3x = Decriptazione.f1(t[i] + 0.5 * h, x[i] + 0.5 * h * k2x, y[i] + 0.5 * h * k2y);
			k3y = Decriptazione.f2(t[i] + 0.5 * h, x[i] + 0.5 * h * k2x, y[i] + 0.5 * h * k2y);

			k4x = Decriptazione.f1(t[i] + h, x[i] + k3x * h, y[i] + k3y * h);
			k4y = Decriptazione.f2(t[i] + h, x[i] + k3x * h, y[i] + k3y * h);

			x[i + 1] = x[i] + (1.0 / 6.0) * (k1x + 2 * k2x + 2 * k3x + k4x) * h;
			y[i + 1] = y[i] + (1.0 / 6.0) * (k1y + 2 * k2y + 2 * k3y + k4y) * h;
		}
		int[][] stheta = new int[dimensione][2];
		for (int i = 0; i < dimensione; i++) {

			stheta[i][0] = ((int) Math.floor(Math.pow(10, 9) * (x[i] - Math.floor(x[i])))) % 255;
			stheta[i][1] = ((int) Math.floor(Math.pow(10, 9) * (y[i] - Math.floor(y[i])))) % 255;
		}
		return stheta;
	}

	static public double f1(double t, double x, double y) {
		return y;
	}

	static public double f2(double t, double x, double y) {
		return -k * Math.sin(w * t) * x + 2 * rho * y - 2 * rho * (Math.pow(x, 2)) * y;
	}
}