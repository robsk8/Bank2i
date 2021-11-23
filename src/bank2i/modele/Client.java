/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bank2i.modele;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    
    public Client() {
        this.idClient = "-1";
        this.x = 0;
        this.y = 0;
        this.chiffreAffaire = 0;
        this.montantEmprunt = 0;
    }

    public Client(String id, double x, double y, double chiffreAffaire, double montantEmprunt) {
        if (id != null)
            this.idClient = id;
        this.x = x;
        this.y = y;
        if (chiffreAffaire > 0)
            this.chiffreAffaire = chiffreAffaire;
        if( montantEmprunt > 0)
            this.montantEmprunt = montantEmprunt;
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
        Client c1 = new Client("Toto", 5.5, 4.5, 4.5, 4.6);
        System.out.println("c1:" + c1.toString() + ")");
    }

}
