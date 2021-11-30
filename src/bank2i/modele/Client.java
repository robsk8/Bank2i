/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bank2i.modele;

import java.awt.BorderLayout;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Quentin SAGNOL (quentin.sagnol@ig2i.centralelille.fr)
 * @date 16 nov. 2021
 */
@Entity
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="CLIENT_ID")
    private String idClient;
    @Column(name="ABSCISSE",nullable = false)
    private double x;
    @Column(name="ORDONNEE",nullable = false)
    private double y;
    @Column(name="CHIFFRE_AFFAIRE",nullable = false)
    private double chiffreAffaire;
    @Column(name="MONTANT_EMPRUNT",nullable = false)
    private double montantEmprunt;
    @ManyToOne
    @JoinColumn(name="INSTANCE_ID")
    private Instance instance;
    @ManyToOne
    @JoinColumn(name="AGENCE_LOCALE_ID")
    private AgenceLocale agenceLocale;
    @ManyToOne
    @JoinColumn(name="AGENCE_REGIONALE_ID")
    private AgenceRegionale agenceRegionale;
    
    public Client() {
        this.idClient = "-1";
        this.x = 0;
        this.y = 0;
        this.chiffreAffaire = 0;
        this.montantEmprunt = 0;
        this.instance = new Instance();
    }

    public Client(String id, double x, double y, double chiffreAffaire, double montantEmprunt, Instance instance) {
        if (id != null)
            this.idClient = id;
        this.x = x;
        this.y = y;
        if (chiffreAffaire > 0)
            this.chiffreAffaire = chiffreAffaire;
        if (montantEmprunt > 0)
            this.montantEmprunt = montantEmprunt;
        this.instance = instance;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getChiffreAffaire() {
        return chiffreAffaire;
    }

    public double getMontantEmprunt() {
        return montantEmprunt;
    }

    public Instance getInstance() {
        return instance;
    }

    public AgenceLocale getAgenceLocale() {
        return agenceLocale;
    }

    public void setAgenceLocale(AgenceLocale agenceLocale) {
        if (agenceLocale != null)
            this.agenceLocale = agenceLocale;
    }

    public AgenceRegionale getAgenceRegionale() {
        return agenceRegionale;
    }

    public void setAgenceRegionale(AgenceRegionale agenceRegionale) {
        if (agenceRegionale != null)
            this.agenceRegionale = agenceRegionale;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + idClient + ", x=" + x + ", y=" + y + ", chiffreAffaire=" + chiffreAffaire + ", montantEmprunt=" + montantEmprunt + '}';
    }

    public static void main(String[] args) {
        Client c1 = new Client();
        AgenceLocale al1 = new AgenceLocale();
        al1.ajouterClient(c1);
        for(Client c : al1.getClients())
            System.out.println(c.toString());
    }

}
