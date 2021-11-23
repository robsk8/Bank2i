/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requete;

import bank2i.modele.Client;
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

    public List<Client> ensClients() throws SQLException { // retourne les niveaux et numeros de la table risque
        List<Client> listeClient = new ArrayList<>();

        String requete = "SELECT * FROM CLIENT";
        Statement stmt = objet.createStatement();
        ResultSet res = stmt.executeQuery(requete);
        while (res.next()) {
            Client c = new Client(res.getString("CLIENT_ID"), res.getInt("ABSCISSE"), res.getInt("ORDONNEE"), res.getInt("CHIFFRE_AFFAIRE"), res.getInt("MONTANT_EMPRUNT"));
            listeClient.add(c);
        }
        res.close();
        stmt.close();
        return listeClient;
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
