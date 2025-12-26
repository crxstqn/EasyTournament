package com.ingsw.easytournament.model;

public class GironeItaliana extends Modalita{
    private int puntiVittoria;
    private int puntiSconfitta;
    private int puntiPareggio;
    boolean andataEritorno;

    GironeItaliana(String nome){
        super(nome);
        puntiVittoria = 3;
        puntiPareggio = 1;
        puntiSconfitta = 0;
        andataEritorno = false;
    }

    @Override
    public String getDescrizione() {
        String andataEritorno = this.andataEritorno ? "SI" : "NO";
        return "Punti vittoria: " + puntiVittoria + "\nPunti pareggio: " + puntiPareggio + "\nPunti sconfitta: " + puntiSconfitta
                + "\nAndata e ritorno: " + andataEritorno;
    }

    public boolean isAndataEritorno() {
        return andataEritorno;
    }

    public void setAndataEritorno(boolean andataEritorno) {
        this.andataEritorno = andataEritorno;
    }

    public int getPuntiPareggio() {
        return puntiPareggio;
    }

    public void setPuntiPareggio(int puntiPareggio) {
        this.puntiPareggio = puntiPareggio;
    }

    public int getPuntiSconfitta() {
        return puntiSconfitta;
    }

    public void setPuntiSconfitta(int puntiSconfitta) {
        this.puntiSconfitta = puntiSconfitta;
    }

    public int getPuntiVittoria() {
        return puntiVittoria;
    }

    public void setPuntiVittoria(int puntiVittoria) {
        this.puntiVittoria = puntiVittoria;
    }

    @Override
    public String getConfigurazione() {
        return puntiVittoria + ";" + puntiPareggio + ";" + puntiSconfitta +  ";" + andataEritorno;
    }

    @Override
    public void setConfigurazione(String configurazione) {
        if (configurazione!= null && !configurazione.isEmpty()) {
            try {
                String[] configurazioneSplit = configurazione.split(";");
                this.puntiVittoria = Integer.parseInt(configurazioneSplit[0]);
                this.puntiPareggio = Integer.parseInt(configurazioneSplit[1]);
                this.puntiSconfitta = Integer.parseInt(configurazioneSplit[2]);
                this.andataEritorno = Boolean.parseBoolean(configurazioneSplit[3]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
