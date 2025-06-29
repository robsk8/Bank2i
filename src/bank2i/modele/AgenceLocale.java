/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank2i.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author Quentin SAGNOL (quentin.sagnol@ig2i.centralelille.fr)
 * @date 16 nov. 2021
 */
@Entity
public class AgenceLocale extends Agence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "NBMAX_CLIENTS")
    private int nbMaxClients;
    @Column(name = "COEFF_ACCESSIBILITE")
    private double coeffAccessibilite;
    @OneToMany(mappedBy="agenceLocale", cascade={CascadeType.PERSIST})
    private Collection<Client> clients;

    public AgenceLocale() {
        super();
        this.nbMaxClients = 0;
        this.coeffAccessibilite = 5.0;
        this.clients = new ArrayList<>();
    }

    public AgenceLocale(String id, double x, double y, double coutLocation, int nbMaxClients, double coeffAccessibilite, Instance instance) {
        super(id, x, y, coutLocation, instance);
        if (nbMaxClients > 0)
            this.nbMaxClients = nbMaxClients;
        else
            this.nbMaxClients = 0;
        if (coeffAccessibilite <= 5 && coeffAccessibilite >= 1)
            this.coeffAccessibilite = coeffAccessibilite;
        else
            this.coeffAccessibilite = 5.0;
        this.clients = new ArrayList<>();
    }

    public int getNbMaxClients() {
        return nbMaxClients;
    }

    public double getCoeffAccessibilite() {
        return coeffAccessibilite;
    }

    public Collection<Client> getClients() {
        return clients;
    }
    
    /* Ajout d'un client dans une agence locale */
    public boolean ajouterClient(Client c) {
        if (this.clients.add(c)) {
            c.setAgenceLocale(this);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "AgenceLocale{" + super.toString() + ", nbMaxClients=" + nbMaxClients + ", coeffAccessibilite=" + coeffAccessibilite + '}';
    }

    public static void main(String[] args) {
//        AgenceLocale a1 = new AgenceLocale("l1", 50.3, 502.3, 59394.34, 5, 3.4);
//        AgenceLocale a2 = new AgenceLocale("l2", 5.3, 2.3, 594.34, 5, 8);
//        System.out.println("a1:" + a1.toString());
//        System.out.println("a2:" + a2.toString());
    }
}
