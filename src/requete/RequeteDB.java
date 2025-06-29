/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requete;

import bank2i.modele.Agence;
import bank2i.modele.AgenceLocale;
import bank2i.modele.AgenceRegionale;
import bank2i.modele.Client;
import bank2i.modele.Instance;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Robin Duflot
 */
public class RequeteDB {

    private Connection objet;
    private static RequeteDB instance;

    public List<Client> ensClients(Instance i) throws SQLException { // retourne les niveaux et numeros de la table risque
        List<Client> listeClient = new ArrayList<>();

        String requete = "SELECT * FROM CLIENT WHERE INSTANCE_ID = " + i.getId();
        Statement stmt = objet.createStatement();
        ResultSet res = stmt.executeQuery(requete);
        while (res.next()) {
           
           Client c = new Client(res.getString("CLIENT_ID"), res.getDouble("ABSCISSE"), res.getDouble("ORDONNEE"), res.getDouble("CHIFFRE_AFFAIRE"), res.getDouble("MONTANT_EMPRUNT"),i);
           listeClient.add(c);
        }
        res.close();
        stmt.close();
        return listeClient;
    }
    
    public List<AgenceLocale> ensAgenceLocale(Instance i) throws SQLException { // retourne les niveaux et numeros de la table risque
        List<AgenceLocale> listeAgenceLocale = new ArrayList<>();

        String requete = "SELECT * FROM AGENCE WHERE DTYPE LIKE 'AgenceLocale' AND INSTANCE_ID = " + i.getId();
        Statement stmt = objet.createStatement();
        ResultSet res = stmt.executeQuery(requete);
        while (res.next()) {
            AgenceLocale a = new AgenceLocale(res.getString("AGENCE_ID"), res.getDouble("ABSCISSE"), res.getDouble("ORDONNEE"), res.getDouble("COUT_LOCATION"), res.getInt("NBMAX_CLIENTS"), res.getDouble("COEFF_ACCESSIBILITE"),i);
            listeAgenceLocale.add(a);
        }
        res.close();
        stmt.close();
        return listeAgenceLocale;
    }
    
    public List<AgenceRegionale> ensAgenceRegionale(Instance i) throws SQLException { // retourne les niveaux et numeros de la table risque
        List<AgenceRegionale> listeAgenceRegionale = new ArrayList<>();

        String requete = "SELECT * FROM AGENCE WHERE DTYPE LIKE 'AgenceRegionale' AND INSTANCE_ID = " + i.getId();
        Statement stmt = objet.createStatement();
        ResultSet res = stmt.executeQuery(requete);
        while (res.next()) {
            AgenceRegionale a = new AgenceRegionale(res.getString("AGENCE_ID"), res.getDouble("ABSCISSE"), res.getDouble("ORDONNEE"), res.getDouble("COUT_LOCATION"), res.getDouble("CA_MAX"), res.getDouble("EMPRUNT_MAX"),i);
            listeAgenceRegionale.add(a);
        }
        res.close();
        stmt.close();
        return listeAgenceRegionale;
    }
    
    public List<Instance> ensInstance() throws SQLException { // retourne les niveaux et numeros de la table risque
        List<Instance> listeInstance = new ArrayList<>();

        String requete = "SELECT * FROM INSTANCE";
        Statement stmt = objet.createStatement();
        ResultSet res = stmt.executeQuery(requete);
        while (res.next()) {
            Instance i = new Instance(res.getLong("ID"), res.getString("NOM"));
            listeInstance.add(i);
        }
        res.close();
        stmt.close();
        return listeInstance;
    }

    private void connect() throws SQLException, ClassNotFoundException {
        String driverClass = "org.apache.derby.jdbc.ClientDriver";
        String urlDatabase = "jdbc:derby://localhost:1527/Bank2iDB";
        String user = "robin";
        String pwd = "mysql";
        Class.forName(driverClass);
        this.objet = DriverManager.getConnection(urlDatabase, user, pwd);

    }

    private RequeteDB() throws SQLException, ClassNotFoundException {
        connect();
    }

    public static RequeteDB getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new RequeteDB();
        }

        return new RequeteDB();
    }
}
