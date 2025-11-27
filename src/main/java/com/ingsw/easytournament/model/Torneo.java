package com.ingsw.easytournament.model;

import com.ingsw.easytournament.utils.SessioneUtente;

import java.time.LocalDate;
import java.util.*;

public class Torneo {
    private int id;
    private String nome;
    private LocalDate data;
    private int id_modalità;
    private int id_utente;
    private Modalita modalita;
    private List<String> squadre;
    private Map<Integer,List<Incontro>> incontri;


    public Torneo(LocalDate data, int id, int id_utente, String nome, int m) {
        this.data = data;
        this.id = id;
        this.id_utente = id_utente;
        this.id_modalità = m;
        this.nome = nome;
    }

    public Torneo(String nome, LocalDate data, int id_modalità, List<String> squadre) {
        this.id_utente = SessioneUtente.getInstance().getUserId();
        this.nome = nome;
        this.data = data;
        this.squadre = squadre;
        this.id_modalità = id_modalità;

        configuraModalita();
    }

    private void configuraModalita(){
        switch (this.id_modalità){
            case 0: {
                this.modalita = new GironeItaliana("Girone all'italiana");
                break;
            }
            case 1: {
                this.modalita = new EliminazioneDiretta("Eliminazione diretta");
                break;
            }
            case 2: {
                this.modalita = new GironiPlayOff("Gironi + Play-off");
                break;
            }
        }
    }

    public void inizializzaIncontri(){
        this.incontri = new HashMap<>();
        switch (this.id_modalità){
            //girone all'italiana
            case 0: {
                inizializzaIncontriGironeItaliana();
                break;
            }
            //eliminazione diretta
            case 1: {
                inizializzaIncontriEliminazioneDiretta();
                break;
            }
            //gironi + playoff
            case 2: {
                inizializzaIncontriGironiPlayOff();
                break;
            }
        }
    }

    private void inizializzaIncontriGironeItaliana(){
        boolean andataEritorno = ((GironeItaliana) this.modalita).isAndataEritorno();
        generaGironeItaliana(this.squadre,0,andataEritorno);
    }

    private void inizializzaIncontriEliminazioneDiretta(){
        int stadio = this.squadre.size()/2;
        this.incontri.put(stadio, new ArrayList<>());
        Collections.shuffle(this.squadre);
        for (int i = 0; i < this.squadre.size()-1; i+=2){
            this.incontri.get(stadio).add(new Incontro(this.id, this.squadre.get(i),this.squadre.get(i+1),-1));
        }
    }

    private void inizializzaIncontriGironiPlayOff(){
        int numGironi = ((GironiPlayOff) this.modalita).getNumeroGironi();
        int numSquadreGirone = ((GironiPlayOff) this.modalita).getNumSquadreGirone();
        int numVincitoriPerGirone = ((GironiPlayOff) this.modalita).getVincitoriPerGirone();
        boolean andataEritorno = ((GironiPlayOff)this.modalita).isAndataEritorno();

        //mischio le squadre
        Collections.shuffle(this.squadre);

        //itero per ogni girone
        for (int g = 0; g < numGironi; g++){
            //prendo uno start e un end per creare le sottoliste
            int start = g*numSquadreGirone;
            int end = start + numSquadreGirone;

            List<String> squadraGirone = this.squadre.subList(start, end);

            generaGironeItaliana(squadraGirone,g+1,andataEritorno);
        }
    }

    private void generaGironeItaliana(List<String> squadre, int idGirone, boolean andataEritorno) {
        int numGiornate;
        if(squadre.size() % 2 != 0) {
            squadre.add("");
        }
        numGiornate = squadre.size()-1;
        if(andataEritorno){
            numGiornate *= 2;
        }
        for (int giornata= 1; giornata < numGiornate + 1; giornata++){
            this.incontri.put(giornata, new ArrayList<>());
            for(int i = 0; i < squadre.size()/2; i++){
                String squadra1 = squadre.get(i);
                String squadra2 = squadre.get(squadre.size()-i-1);
                if (andataEritorno && giornata > numGiornate/2 ){
                    squadra1 = squadre.get(squadre.size()-i-1);
                    squadra2 = squadre.get(i);
                }
                this.incontri.get(giornata).add(new Incontro(this.id, squadra1,squadra2,idGirone));
            }
        }
    }


    public String getDescrizione(){
        return "Nome torneo: " + this.nome + "\nData di inzio: " + this.data.toString() + "\nModalità: " + this.modalita.getNome() + "\n" + this.modalita.getDescrizione();
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_utente() {
        return id_utente;
    }

    public void setId_utente(int id_utente) {
        this.id_utente = id_utente;
    }

    public int getIdModalità() {
        return id_modalità;
    }

    public Modalita getModalita() {
        return modalita;
    }

    public void setIdModalità(int modalità) {
        this.id_modalità = modalità;
        configuraModalita();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getSquadre() {
        return squadre;
    }

    public void setSquadre(List<String> squadre) {
        this.squadre = squadre;
    }
}



