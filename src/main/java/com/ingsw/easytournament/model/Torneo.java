package com.ingsw.easytournament.model;

import com.ingsw.easytournament.utils.DatabaseConnessione;
import com.ingsw.easytournament.utils.SessioneUtente;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.*;

public class Torneo {
    private int id;
    private String nome;
    private LocalDate data;
    private int id_modalità;
    private int id_utente;
    private Modalita modalita;
    private List<Squadra> squadre;
    private Map<Integer,List<Incontro>> incontri;


    public Torneo(LocalDate data, int id, int id_utente, String nome, int m, String confTorneo) {
        this.data = data;
        this.id = id;
        this.id_utente = id_utente;
        this.id_modalità = m;
        this.nome = nome;

        configuraModalita(confTorneo);
    }

    public Torneo(String nome, LocalDate data, int id_modalità, List<Squadra> squadre) {
        this.id_utente = SessioneUtente.getInstance().getUserId();
        this.nome = nome;
        this.data = data;
        this.squadre = squadre;
        this.id_modalità = id_modalità;

        configuraModalita(null);
    }

    private void configuraModalita(String confTorneo) {
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
        if (confTorneo != null && !confTorneo.isEmpty()) {
            this.modalita.setConfigurazione(confTorneo);
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
                inizializzaIncontriEliminazioneDiretta(this.squadre);
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

    private void inizializzaIncontriEliminazioneDiretta(List<Squadra> squadre){
        int stadio = squadre.size()/2;
        this.incontri.put(stadio, new ArrayList<>());

        Collections.shuffle(squadre);
        for (int i = 0; i < squadre.size()-1; i+=2){
            this.incontri.get(stadio).add(new Incontro(this.id, squadre.get(i),squadre.get(i+1),-1));
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

            List<Squadra> squadraGirone = this.squadre.subList(start, end);

            generaGironeItaliana(squadraGirone,g+1, andataEritorno);
        }
    }

    private void generaGironeItaliana(List<Squadra> squadreDaCopiare, int idGirone, boolean andataEritorno) {
        List<Squadra> squadre = new ArrayList<>(squadreDaCopiare);

        int numGiornate;
        if(squadre.size() % 2 != 0) {
            squadre.add(new Squadra(""));
        }
        numGiornate = squadre.size()-1;
        if(andataEritorno){
            numGiornate *= 2;
        }
        for (int giornata= 1; giornata < numGiornate + 1; giornata++){
            this.incontri.putIfAbsent(giornata, new ArrayList<>());
            for(int i = 0; i < squadre.size()/2; i++){
                Squadra squadra1 = squadre.get(i);
                Squadra squadra2 = squadre.get(squadre.size()-i-1);
                if (andataEritorno && giornata > numGiornate/2 ){
                    squadra1 = squadre.get(squadre.size()-i-1);
                    squadra2 = squadre.get(i);
                }
                this.incontri.get(giornata).add(new Incontro(this.id, squadra1,squadra2,idGirone));
            }
            Squadra squadraRuotata = squadre.remove(squadre.size() - 1);
            squadre.add(1, squadraRuotata);

        }
    }


    //per eliminazione diretta
    public Pair<Integer, List<Incontro>> avanzaTurno(){
        int ultimoTurno = Collections.min(this.incontri.keySet());
        List<Squadra> squadrePerdenti = new ArrayList<>();
        List<Squadra> squadreVincitrici = new ArrayList<>();

        for (Incontro incontri : this.incontri.get(ultimoTurno)){
            if (incontri.getPunteggioSquadra1() > incontri.getPunteggioSquadra2()){
                if (ultimoTurno == 2) {
                    squadrePerdenti.add(incontri.getSquadra2());
                }
                squadreVincitrici.add(incontri.getSquadra1());
            }
            else {
                if (ultimoTurno == 2) {
                    squadrePerdenti.add(incontri.getSquadra1());
                }
                squadreVincitrici.add(incontri.getSquadra2());
            }
        }

        //metodo per finalina

        inizializzaIncontriEliminazioneDiretta(squadreVincitrici);
        ultimoTurno = Collections.min(this.incontri.keySet());

//        if (ultimoTurno == 1 && this.getModalita() instanceof EliminazioneDiretta) {
//            EliminazioneDiretta eliminazioneDiretta = (EliminazioneDiretta) modalita;
//            if (eliminazioneDiretta.getFinalina()){
//                creaFinalina(squadrePerdenti);
//            }
//        }
        System.out.println(ultimoTurno);
        return new Pair<>(ultimoTurno, this.incontri.get(ultimoTurno));
    }

    private void creaFinalina(List<Squadra> squadre){
        this.incontri.put(0, new ArrayList<>());
        this.incontri.get(0).add(new Incontro(this.id, squadre.get(0),squadre.get(1),-1));
    }

    public Pair<Integer, Incontro> getFinalina(){
        if (!this.incontri.containsKey(0)) return null;
        return new Pair<>(0, this.incontri.get(0).get(0));
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
        configuraModalita(null);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Squadra> getSquadre() {
        return squadre;
    }

    public void setSquadre(List<Squadra> squadre) {
        this.squadre = squadre;
    }

    public Map<Integer, List<Incontro>> getIncontri() {
        return incontri;
    }

    public void setIncontri(Map<Integer, List<Incontro>> incontri) {
        this.incontri = incontri;
    }


}



