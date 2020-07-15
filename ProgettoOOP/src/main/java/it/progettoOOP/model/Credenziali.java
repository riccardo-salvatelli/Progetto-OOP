package it.progettoOOP.model;

import it.progettoOOP.exception.ChiaviNullException;

/**
 * <b>Credenziali</b> viene unicamente utilizzata in {@link it.progettoOOP.service.ServizioFileImpl#scaricaFile(String, double[])}
 * come parametro del metodo
 */
public class Credenziali {
    /**
     * rappresenta l'id o il path del file presente in dropbox.
     */
    private String id;

    /**
     * contiene al suo interno il il valore relativo alla prima e alla seconda chiave,
     * necessarie per decriptare il file
     */
    private double[] chiavi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return <code>double[]</code> che contiene le chiavi se <code>chiavi != null</code>.
     * @throws ChiaviNullException Lancia quest'eccezione quando <code>chiavi == null</code>
     */
    public double[] getChiavi() throws ChiaviNullException {
        if (chiavi == null)
            throw new ChiaviNullException("Chiavi mancanti");
        return chiavi;
    }

    public void setChiavi(double[] chiavi) {
        this.chiavi = chiavi;
    }

    public Credenziali(String id, double[] chiavi) {
        this.id = id;
        this.chiavi = chiavi;
    }

}
