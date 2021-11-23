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
public class Agence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="AGENCE_ID")
    private String idAgence;
    @Column(name="ABSCISSE",nullable = false)
    private double x;
    @Column(name="ORDONNEE",nullable = false)
    private double y;
    @Column(name="COUT_LOCATION",nullable = false)
    private double coutLocation;
    
    public Agence() {
        idAgence = "a0";
        x = 0;
        y = 0;
        coutLocation = 0;
    }

    public Agence(String id, double x, double y, double coutLocation) {
        if (id != null)
            this.idAgence = id;
        this.x = x;
        this.y = y;
        if (coutLocation >= 0)
            this.coutLocation = coutLocation;
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
        if (!(object instanceof Agence)) {
            return false;
        }
        Agence other = (Agence) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "id=" + idAgence + ", x=" + x + ", y=" + y + ", coutLocation=" + coutLocation;
    }
    
}
