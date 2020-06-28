public class Decriptazione {
	static private double h = 0.01;
	static private int n = 100000;

	double x[] = new double[n];
	double y[] = new double[10000];
	x[0]= 3;
	y[0]= -2;
	private double[] autoval1 = new double[n];
	private double[] autoval2 = new double[n];

	static private double rho = 0.6;
	static private double w = 5.9;
	static private double k = 19.5;
	private double[] t = new double[(int) (n * h)];

	for(int i=1; i<(int)(n*h); i++)
	{
		t[i] = t[i - 1] + h;
	}

	static private double k1x;
	static private double k2x;
	static private double k3x;
	static private double k4x;
	static private double k1y;
	static private double k2y;
	static private double k3y;
	static private double k4y;

	public double calcoloSequenza() {
		
		this.trovaCaos(dimensione);

		for (i = 0; i<dimensione; i++){
			
			k1x = Decriptazione.f1(this.t[i], this.x[i], this.y[i]);
			k1y = Decriptazione.f2(this.t[i], this.x[i], this.y[i]);

			k2x = Decriptazione.f1(this.t[i] + 0.5 * h, this.x[i] + 0.5 * h * k1x, this.y[i] + 0.5 * h * k1y);
			k2y = Decriptazione.f2(this.t[i] + 0.5 * h, this.x[i] + 0.5 * h * k1x, this.y[i] + 0.5 * h * k1y);

			k3x = Decriptazione.f1(this.t[i] + 0.5 * h, this.x[i] + 0.5 * h * k2x, this.y[i] + 0.5 * h * k2y);
			k3y = Decriptazione.f2(this.t[i] + 0.5 * h, this.x[i] + 0.5 * h * k2x, this.y[i] + 0.5 * h * k2y);

			k4x = Decriptazione.f1(this.t[i] + h, this.x[i] + k3x * h, this.y[i] + k3y * h);
			k4y = Decriptazione.f2(this.t[i] + h, this.x[i] + k3x * h, this.y[i] + k3y * h);

			this.x[i + 1] = this.x[i] + (1 / 6) * (k1x + 2 * k2x + 2 * k3x + k4x) * h;
			this.y[i + 1] = this.y[i] + (1 / 6) * (k1y + 2 * k2y + 2 * k3y + k4y) * h;
		}
		private double[][] stheta = new double[dimensione][2];
		for(i=0; i<dimensione; i++){
			
			stheta[i][1] = Math.floor(Math.pow(this.x[i] - Math.floor(this.x[i]), 150));
			stheta[i][2] = Math.floor(Math.pow(this.y[i] - Math.floor(this.y[i]), 150));
		}
		return stheta;
	}

	public void trovaCaos(int dim) {
		private boolean controllo1 = true;
		private boolean controllo2 = true;
		for(i = 0; i<n; i++)
		{
			k1x = Decriptazione.f1(this.t[i], this.x[i], this.y[i]);
			k1y = Decriptazione.f2(this.t[i], this.x[i], this.y[i]);

			k2x = Decriptazione.f1(this.t[i] + 0.5 * h, this.x[i] + 0.5 * h * k1x, this.y[i] + 0.5 * h * k1y);
			k2y = Decriptazione.f2(this.t[i] + 0.5 * h, this.x[i] + 0.5 * h * k1x, this.y[i] + 0.5 * h * k1y);

			k3x = Decriptazione.f1(this.t[i] + 0.5 * h, this.x[i] + 0.5 * h * k2x, this.y[i] + 0.5 * h * k2y);
			k3y = Decriptazione.f2(this.t[i] + 0.5 * h, this.x[i] + 0.5 * h * k2x, this.y[i] + 0.5 * h * k2y);

			k4x = Decriptazione.f1(this.t[i] + h, this.x[i] + k3x * h, this.y[i] + k3y * h);
			k4y = Decriptazione.f2(this.t[i] + h, this.x[i] + k3x * h, this.y[i] + k3y * h);

			this.x[i + 1] = this.x[i] + (1 / 6) * (k1x + 2 * k2x + 2 * k3x + k4x) * h;
			this.y[i + 1] = this.y[i] + (1 / 6) * (k1y + 2 * k2y + 2 * k3y + k4y) * h;

			this.autoval1[i] = rho - 2 * (Math.pow(rho, 2))
				* (Math.pow(this.x[i], 2) + Math.sqrt(Math.pow(rho - 2 * rho * Math.pow(this.x[i], 2), 2)
						- (k * Math.sin(w * this.t[i]) - 4 * rho * this.x[i] * this.y[i])));
			this.autoval2[i] = rho - 2 * (Math.pow(rho, 2))
				* (Math.pow(this.x[i], 2) - Math.sqrt(Math.pow(rho - 2 * rho * Math.pow(this.x[i], 2), 2)
						- (k * Math.sin(w * this.t[i]) - 4 * rho * this.x[i] * this.y[i])));
			if (controllo1 & this.autoval1[i] < 0) {
				controllo1 = false;
			}
			if (controllo2 & this.autoval2[i] > 0) {
				controllo2 = false;
			}
			if (!(controllo1 | controllo2)) {
				double appoggio = this.x[i];
				this.x = new double[dim];
				this.x[0] = appoggio;
				appoggio = this.y[i];
				this.y = new double[dim];
				this.y[0] = appoggio;
				this.t = new double[dim * h];
				for (int i = 1; i < (int) (n * h); i++) {
					this.t[i] = this.t[i - 1] + h;
				}
				break;
			}
		}
	}

	static public double f1(double t, double x, double y) {
		return y;
	}

	static public double f2(double t, double x, double y) {
		return -k*Math.sin(w*t)*x+2*rho*y-2*rho*(Math.pow(x, 2))*y;
	}
}
