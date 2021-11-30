/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank2i.io;

import bank2i.modele.AgenceLocale;
import bank2i.modele.AgenceRegionale;
import bank2i.modele.Client;
import bank2i.modele.Instance;
import io.exception.EmptyFieldException;
import io.exception.FieldFormatException;
import io.exception.FieldNameException;
import io.exception.FileExistException;
import io.exception.FormatFileException;
import io.exception.NumberColumnsException;
import io.exception.OpenFileException;
import io.exception.ReaderException;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Classe qui permet de lire une instance pour le projet de POO3 2021/2022. Le
 * format des instances est decrit dans le sujet du projet. Attention, ceci est
 * un squellette de code, il faut le completer. Des commentaires contenant
 * 'TODO' vous aident pour voir a quel endroit vous pouvez completer. N'hesitez
 * pas a apporter toutes les modifications que vous jugez utiles.
 *
 * @author Maxime Ogier
 */
public class InstanceReader {

    /**
     * Le fichier contenant l'instance.
     */
    private File instanceFile;
    /**
     * Le numero de la ligne courante dans le fichier.
     */
    private int numLigne;
    /**
     * L'entete des colonnes pour la lecture des clients.
     */
    private static final String[] HEADER_CLIENTS = new String[]{"Id client",
        "Abscisse", "Ordonnee", "Chiffre d'affaires", "Emprunts"};
    /**
     * L'entete des colonnes pour la lecture des agences locales.
     */
    private static final String[] HEADER_AGENCES_LOCALES = new String[]{"Id agence locale",
        "Abscisse", "Ordonnee", "Cout location", "Nb max clients", "Accessibilite"};
    /**
     * L'entete des colonnes pour la lecture des agences regionales.
     */
    private static final String[] HEADER_AGENCES_REGIONALES = new String[]{"Id agence regionale",
        "Abscisse", "Ordonnee", "Cout location", "Chiffre d'affaires max", "Emprunts max"};

    /**
     * Constructeur par donnee du chemin du fichier d'instance.
     *
     * @param inputPath le chemin du fichier d'instance, qui doit se terminer
     * par l'extension du fichier (.csv).
     * @throws ReaderException lorsque le fichier n'est pas au bon format ou ne
     * peut pas etre ouvert.
     */
    public InstanceReader(String inputPath) throws ReaderException {
        if (inputPath == null) {
            throw new OpenFileException();
        }
        if (!inputPath.endsWith(".csv")) {
            throw new FormatFileException("csv", "csv");
        }
        String instanceName = inputPath;
        this.instanceFile = new File(instanceName);
        this.numLigne = 0;
    }

    /**
     * Methode pour lire le fichier d'instance.
     *
     * @throws ReaderException lorsque les donnees dans le fichier d'instance
     * sont manquantes ou au mauvais format.
     */
    public void readInstance() throws ReaderException {
        Scanner scanner = null;
        try {
            scanner = new Scanner(instanceFile);
        } catch (FileNotFoundException ex) {
            throw new FileExistException(instanceFile.getName());
        }
        // Dans la ligne qui suit vous recuperez le nom de l'instance.
        String nom = readStringInLine(scanner, "Nom");
        ////////////////////////////////////////////
        // TODO : Vous pouvez creer une instance.
        ////////////////////////////////////////////

        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bank2IPU");
        final EntityManager em = emf.createEntityManager();
        try {
            final EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                readStringInLine(scanner, HEADER_CLIENTS);
                
                // On insère une nouvelle instance en DB
                Instance i = new Instance(nom);
                em.persist(i);
                
                System.out.println("ID Instance: " + i.getId());
                
                // Dans la boucle qui suit, nous allons lire les donnees relatives a chaque client.
                while (true) {
                    InfosClient elem = readClientInLine(scanner, HEADER_AGENCES_LOCALES);
                    if (elem == null) {
                        break;
                    }
                    Client c = new Client(elem.getIdentifiant(), elem.getAbscisse(), elem.getOrdonnee(), elem.getChiffreAffaires(), elem.getEmprunts(), i);
                    System.out.println("c:" + c.toString());
                    em.persist(c);
                    // Notez que elem est un objet qui contient cinq attributs : 
                    // un identifiant ; une abscisse ; une ordonnee, un chiffre d'affaires
                    // et un montant d'emprunts.
                    // Vous pouvez acceder a ces attributs de la maniere suivante :
                    // elem.getIdentifiant()
                    // elem.getAbscisse()
                    // elem.getOrdonnee()
                    // elem.getChiffreAffaires()
                    // elem.getEmprunts()

                    ////////////////////////////////////////////
                    // TODO : Vous pouvez ajoutez chacun des clients a votre instance
                    ////////////////////////////////////////////
                }

                // Dans la boucle qui suit, nous allons lire les donnees relatives a chaque agence locale.
                while (true) {
                    InfosAgenceLocale elem = readAgenceLocaleInLine(scanner, HEADER_AGENCES_REGIONALES);
                    if (elem == null) {
                        break;
                    }
                    AgenceLocale l = new AgenceLocale(elem.getIdentifiant(), elem.getAbscisse(), elem.getOrdonnee(), elem.getCoutLocation(), elem.getNbMaxClients(), elem.getAccessibilitte(), i);
                    System.out.println("al:" + l.toString());
                    em.persist(l);
                    // Notez que elem est un objet qui contient six attributs : 
                    // un identifiant ; une abscisse ; une ordonnee ; un cout de
                    // location ; un nombre de clients maximum ; un coefficient d'accessibilite.
                    // Vous pouvez acceder a ces attributs de la maniere suivante :
                    // elem.getIdentifiant()
                    // elem.getAbscisse()
                    // elem.getOrdonnee()
                    // elem.getCoutLocation()
                    // elem.getNbMaxClients()
                    // elem.getAccessibilitte()

                    ////////////////////////////////////////////
                    // TODO : Vous pouvez ajoutez chacune des agences locales a votre instance
                    ////////////////////////////////////////////
                }

                // Dans la boucle qui suit, nous allons lire les donnees relatives a chaque agence regionale.
                while (true) {
                    InfosAgenceRegionale elem = readAgenceRegionaleInLine(scanner);
                    if (elem == null) {
                        break;
                    }
                    AgenceRegionale r = new AgenceRegionale(elem.getIdentifiant(), elem.getAbscisse(), elem.getOrdonnee(), elem.getCoutLocation(), elem.getChiffreAffairesMax(), elem.getEmpruntsMax(), i);
                    System.out.println("ar:" + r.toString());
                    em.persist(r);
                    // Notez que elem est un objet qui contient six attributs : 
                    // un identifiant ; une abscisse ; une ordonnee ; un cout de
                    // location ; un chiffre d'affaires maximum ; un montant d'emprunts
                    // maximum.
                    // Vous pouvez acceder a ces attributs de la maniere suivante :
                    // elem.getIdentifiant()
                    // elem.getAbscisse()
                    // elem.getOrdonnee()
                    // elem.getCoutLocation()
                    // elem.getChiffreAffairesMax()
                    // elem.getEmpruntsMax()

                    ////////////////////////////////////////////
                    // TODO : Vous pouvez ajoutez chacune des agences regionales a votre instance
                    ////////////////////////////////////////////
                }
                et.commit();
            } catch (Exception ex) {
                System.out.println(ex);
                et.rollback();
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }

    /**
     * Lecture de plusieurs chaines de caracteres dans une seule ligne.
     *
     * @param scanner lecteur du fichier
     * @param names tous les noms qui doivent etre dans la ligne courante
     * (separes par des points-virgules)
     * @throws ReaderException lorsque le format attendu n'est pas respecte
     */
    private void readStringInLine(Scanner scanner, String[] names) throws ReaderException {
        String[] values = nextLine(scanner);
        if (values.length != names.length) {
            throw new NumberColumnsException(numLigne, names);
        }
        for (int col = 0; col < values.length; col++) {
            if (!values[col].equalsIgnoreCase(names[col])) {
                throw new FieldNameException(numLigne, col + 1, names[col]);
            }
        }
    }

    /**
     * Lecture d'un nombre entier dans la seconde colonne d'une ligne.
     *
     * @param scanner lecteur du fichier
     * @param name le nom qui doit etre indique dans la premiere colonne
     * @return le nombre entier
     * @throws ReaderException lorsque le format attendu n'est pas respecte
     */
    private int readIntInLine(Scanner scanner, String name) throws ReaderException {
        String[] values = nextLine(scanner);
        checkLine(values, name);
        int val;
        try {
            val = Integer.parseInt(values[1]);
        } catch (NumberFormatException ex) {
            throw new FieldFormatException(numLigne, 2, "un nombre entier");
        }
        return val;
    }

    /**
     * Lecture des informations sur un client dans les cinq premieres colonnes :
     * l'identifiant sous forme de chaine de caractères, l'abscisse, l'ordonnee,
     * le chiffre d'affaires et le montant d'emprunts sous forme de nombre reel.
     *
     * @param scanner lecteur du fichier
     * @param echappement tableau de chaines de caractere qui indique la fin de
     * la lecture des clients
     * @return les informations sur le client, null s'il n'y a rien ou
     * l'echapppement
     * @throws ReaderException lorsque le format attendu n'est pas respecte
     */
    private InfosClient readClientInLine(Scanner scanner, String[] echappement) throws ReaderException {
        String[] values = nextLine(scanner);
        if (values.length == 0) {
            return null;
        }
        if (tableauStringIdentique(values, echappement)) {
            return null;
        }
        if (values.length < 5) {
            throw new EmptyFieldException(numLigne, 5);
        }
        InfosClient val;
        String identifiant = values[0];
        double abscisse = readDouble(values[1], 2);
        double ordonnee = readDouble(values[2], 3);
        double chiffreAffaire = readDouble(values[3], 4);
        double emprunts = readDouble(values[4], 5);
        val = new InfosClient(identifiant, abscisse, ordonnee, chiffreAffaire, emprunts);
        return val;
    }

    /**
     * Indique si deux tableaux de chaines de caracteres sont identiques ou non.
     *
     * @param values le tableau a comparer
     * @param cmp le tableau de reference
     * @return true si les deux tableau sont de meme longueur et contiennent les
     * memes chaines de caracteres dans le meme ordre
     */
    private boolean tableauStringIdentique(String[] values, String[] cmp) {
        if (values.length != cmp.length) {
            return false;
        }
        for (int i = 0; i < values.length; i++) {
            if (!values[i].equalsIgnoreCase(cmp[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Lecture des informations sur une agence locale dans les six premieres
     * colonnes : l'identifiant sous forme de chaine de caractères, l'abscisse,
     * l'ordonnee, le cout de location sous forme de nombre reel, le nombre
     * maximum de clients sous forme de nombre entier, et le coefficient
     * d'accessibilité sous forme de nomre reel.
     *
     * @param scanner lecteur du fichier
     * @return les informations sur l'agence locale
     * @throws ReaderException lorsque le format attendu n'est pas respecte
     */
    private InfosAgenceLocale readAgenceLocaleInLine(Scanner scanner,
            String[] echappement) throws ReaderException {
        String[] values = nextLine(scanner);
        if (values.length == 0) {
            return null;
        }
        if (tableauStringIdentique(values, echappement)) {
            return null;
        }
        if (values.length < 6) {
            throw new EmptyFieldException(numLigne, 6);
        }
        InfosAgenceLocale val;
        String identifiant = values[0];
        double abscisse = readDouble(values[1], 2);
        double ordonnee = readDouble(values[2], 3);
        double coutLocation = readDouble(values[3], 4);
        int nbClientsMax = readInt(values[4], 5);
        double coeffAccessibilite = readDouble(values[5], 6);
        val = new InfosAgenceLocale(identifiant, abscisse, ordonnee, coutLocation,
                nbClientsMax, coeffAccessibilite);
        return val;
    }

    /**
     * Lecture des informations sur une agence regionale dans les six premieres
     * colonnes : l'identifiant sous forme de chaine de caractères, l'abscisse,
     * l'ordonnee, le cout de location, le chiffre d'affaires maximum et le
     * montant d'emprunts maximum sous forme de nombre reel.
     *
     * @param scanner lecteur du fichier
     * @return les informations sur l'agence regionale
     * @throws ReaderException lorsque le format attendu n'est pas respecte
     */
    private InfosAgenceRegionale readAgenceRegionaleInLine(Scanner scanner) throws ReaderException {
        String[] values = nextLine(scanner);
        if (values.length == 0) {
            return null;
        }
        if (values.length < 6) {
            throw new EmptyFieldException(numLigne, 6);
        }
        InfosAgenceRegionale val;
        String identifiant = values[0];
        double abscisse = readDouble(values[1], 2);
        double ordonnee = readDouble(values[2], 3);
        double coutLocation = readDouble(values[3], 4);
        double chiffreAffairesMax = readDouble(values[4], 5);
        double empruntsmax = readDouble(values[5], 6);
        val = new InfosAgenceRegionale(identifiant, abscisse, ordonnee,
                coutLocation, chiffreAffairesMax, empruntsmax);
        return val;
    }

    /**
     * Lecture d'un nombre entier a partir d'une chaine de caracteres.
     *
     * @param value la chaine de caracteres
     * @param numColonne le numero de la colonne dans laquelle on lit le nombre
     * @return le nombre entier
     * @throws ReaderException lorsque le format attendu n'est pas respecte
     */
    private int readInt(String value, int numColonne) throws ReaderException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new FieldFormatException(numLigne, numColonne, "un nombre entier");
        }
    }

    /**
     * Lecture d'un nombre reel a partir d'une chaine de caracteres.
     *
     * @param value la chaine de caracteres
     * @param numColonne le numero de la colonne dans laquelle on lit le nombre
     * @return le nombre reel
     * @throws ReaderException lorsque le format attendu n'est pas respecte
     */
    private double readDouble(String value, int numColonne) throws ReaderException {
        try {
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            Number number = format.parse(value);
            double d = number.doubleValue();
            return d;
        } catch (ParseException ex) {
            throw new FieldFormatException(numLigne, numColonne, "un nombre reel");
        }
    }

    /**
     * Lecture d'une chaine de caracteres dans la seconde colonne d'une ligne.
     *
     * @param scanner lecteur du fichier
     * @param name le nom qui doit etre indique dans la premiere colonne
     * @return la chaine de caracteres
     * @throws ReaderException lorsque le format attendu n'est pas respecte
     */
    private String readStringInLine(Scanner scanner, String name) throws ReaderException {
        String[] values = nextLine(scanner);
        checkLine(values, name);
        return values[1];
    }

    /**
     * Verification de la validite d'une ligne.
     *
     * @param values les valeurs dans la ligne, separes par colonne
     * @param name le nom qui doit etre dans la premiere colonne
     * @throws FieldNameException lorsque le nom dans la premiere colonne n'est
     * pas correct
     * @throws EmptyFieldException losque la valeur dans la seconde colonne
     * n'est pas remplie
     */
    private void checkLine(String[] values, String name) throws FieldNameException, EmptyFieldException {
        if (!values[0].equalsIgnoreCase(name)) {
            throw new FieldNameException(numLigne, 1, name);
        }
        if (values.length == 1 || values[1].length() == 0) {
            throw new EmptyFieldException(numLigne, 2);
        }
    }

    /**
     * Lecture de la prochaine ligne du fichier.
     *
     * @param scanner lecteur du fichier
     * @return les valeurs presentes dans les differentes colonnes (separees par
     * des points-virgules)
     */
    private String[] nextLine(Scanner scanner) {
        String[] values = null;
        do {
            if (!scanner.hasNextLine()) {
                return new String[]{};
            }
            String line = scanner.nextLine();
            values = line.split(";");
            this.numLigne++;
        } while (values.length == 0);
        return values;
    }

    /**
     * Classe interne qui represente les informations generales sur les clients
     * et les agences : identifiant, abscisse, ordonnee.
     */
    class Infos {

        private String identifiant;
        private double abscisse;
        private double ordonnee;

        public Infos(String identifiant, double abscisse, double ordonnee) {
            this.identifiant = identifiant;
            this.abscisse = abscisse;
            this.ordonnee = ordonnee;
        }

        public String getIdentifiant() {
            return identifiant;
        }

        public double getAbscisse() {
            return abscisse;
        }

        public double getOrdonnee() {
            return ordonnee;
        }
    }

    /**
     * Classe interne qui represente les informations sur un client.
     */
    class InfosClient extends Infos {

        private double chiffreAffaires;
        private double emprunts;

        public InfosClient(String identifiant, double abscisse, double ordonnee,
                double chiffreAffaires, double emprunts) {
            super(identifiant, abscisse, ordonnee);
            this.chiffreAffaires = chiffreAffaires;
            this.emprunts = emprunts;
        }

        public double getChiffreAffaires() {
            return chiffreAffaires;
        }

        public double getEmprunts() {
            return emprunts;
        }

        @Override
        public String toString() {
            return "Client{chiffreAffaires=" + chiffreAffaires + ", emprunts=" + emprunts + ", id=" + super.getIdentifiant() + ", x=" + super.getAbscisse() + ",y=" + super.getOrdonnee() + '}';
        }

    }

    /**
     * Classe interne qui represente les informations sur une agence.
     */
    class InfosAgence extends Infos {

        private double coutLocation;

        public InfosAgence(String identifiant, double abscisse, double ordonnee,
                double coutLocation) {
            super(identifiant, abscisse, ordonnee);
            this.coutLocation = coutLocation;
        }

        public double getCoutLocation() {
            return coutLocation;
        }

        @Override
        public String toString() {
            return "InfosAgence{" + "coutLocation=" + coutLocation + '}';
        }

    }

    /**
     * Classe interne qui represente les informations sur une agence locale.
     */
    class InfosAgenceLocale extends InfosAgence {

        private int nbMaxClients;
        private double accessibilitte;

        public InfosAgenceLocale(String identifiant, double abscisse,
                double ordonnee, double coutLocation, int nbMaxClients,
                double accessibilitte) {
            super(identifiant, abscisse, ordonnee, coutLocation);
            this.nbMaxClients = nbMaxClients;
            this.accessibilitte = accessibilitte;
        }

        public int getNbMaxClients() {
            return nbMaxClients;
        }

        public double getAccessibilitte() {
            return accessibilitte;
        }
    }

    /**
     * Classe interne qui represente les informations sur une agence regionale.
     */
    class InfosAgenceRegionale extends InfosAgence {

        private double chiffreAffairesMax;
        private double empruntsMax;

        public InfosAgenceRegionale(String identifiant, double abscisse,
                double ordonnee, double coutLocation, double chiffreAffairesMax,
                double empruntsMax) {
            super(identifiant, abscisse, ordonnee, coutLocation);
            this.chiffreAffairesMax = chiffreAffairesMax;
            this.empruntsMax = empruntsMax;
        }

        public double getChiffreAffairesMax() {
            return chiffreAffairesMax;
        }

        public double getEmpruntsMax() {
            return empruntsMax;
        }
    }

    /**
     * Un petit test pour verifier que tout fonctionne correctement.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            InstanceReader reader = new InstanceReader("instances/instance_test.csv");
            InstanceReader reader2 = new InstanceReader("instances/instance_1.csv");
            InstanceReader reader3 = new InstanceReader("instances/instance_2.csv");
            InstanceReader reader4 = new InstanceReader("instances/instance_3.csv");
            reader.readInstance();
            System.out.println("Instance lue avec success !");
            reader2.readInstance();
            System.out.println("Instance lue avec success !");
            reader3.readInstance();
            System.out.println("Instance lue avec success !");
            reader4.readInstance();
            System.out.println("Instance lue avec success !");
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
