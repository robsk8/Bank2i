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
public class AgenceRegionale extends Agence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name="CA_MAX")
    private double chiffreAffaireMax;
    @Column(name="EMPRUNT_MAX")
    private double montantEmpruntMax;
    @OneToMany(mappedBy="agenceRegionale", cascade={CascadeType.PERSIST})
    private Collection<Client> clients;

    public AgenceRegionale() {
        super();
        this.chiffreAffaireMax = 0;
        this.montantEmpruntMax = 0;
        this.clients = new ArrayList<>();
    }
    
    public AgenceRegionale(String id, double x, double y, double coutLocation, double chiffreAffaireMax, double montantEmpruntMax, Instance instance) {
        super(id, x, y, coutLocation, instance);
        if (chiffreAffaireMax > 0)
            this.chiffreAffaireMax = chiffreAffaireMax;
        else
            this.chiffreAffaireMax = 0;
        if (montantEmpruntMax > 0)
            this.montantEmpruntMax = montantEmpruntMax;
        else
            this.montantEmpruntMax = 0;
        this.clients = new ArrayList<>();
    }

    public double getChiffreAffaireMax() {
        return chiffreAffaireMax;
    }

    public double getMontantEmpruntMax() {
        return montantEmpruntMax;
    }

    public Collection<Client> getClients() {
        return clients;
    }
    
    /* Ajout d'un client dans une agence régionale */
    public boolean ajouterClient(Client c) {
        if (this.clients.add(c)) {
            c.setAgenceRegionale(this);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "AgenceRegionale{" + super.toString() + ", chiffreAffaireMax=" + chiffreAffaireMax + ", montantEmpruntMax=" + montantEmpruntMax + '}';
    }

    public static void main(String[] args) {
//        AgenceRegionale a1 = new AgenceRegionale("r1", 50.3, 502.3, 59394.34, 40405.0, 4);
//        AgenceRegionale a2 = new AgenceRegionale("r2", 5.3, 2.3, 594.34, -455.0, 5000);
//        System.out.println("a1:" + a1.toString());
//        System.out.println("a2:" + a2.toString());
    }

}
