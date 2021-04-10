/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piramboia;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author arpor
 */
@Entity
@Table(name = "carreras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carreras.findAll", query = "SELECT c FROM Carreras c"),
    @NamedQuery(name = "Carreras.findByNumSec", query = "SELECT c FROM Carreras c WHERE c.numSec = :numSec"),
    @NamedQuery(name = "Carreras.findByFecha", query = "SELECT c FROM Carreras c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Carreras.findByHoraInicio", query = "SELECT c FROM Carreras c WHERE c.horaInicio = :horaInicio"),
    @NamedQuery(name = "Carreras.findByHoraFin", query = "SELECT c FROM Carreras c WHERE c.horaFin = :horaFin"),
    @NamedQuery(name = "Carreras.findByKms", query = "SELECT c FROM Carreras c WHERE c.kms = :kms"),
    @NamedQuery(name = "Carreras.findByTipoDeEjercicio", query = "SELECT c FROM Carreras c WHERE c.tipoDeEjercicio = :tipoDeEjercicio"),
    @NamedQuery(name = "Carreras.findByPeso", query = "SELECT c FROM Carreras c WHERE c.peso = :peso")})
public class Carreras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "num_sec")
    private Integer numSec;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "hora_inicio")
    @Temporal(TemporalType.TIME)
    private Date horaInicio;
    @Column(name = "hora_fin")
    @Temporal(TemporalType.TIME)
    private Date horaFin;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kms")
    private Double kms;
    @Lob
    @Column(name = "recorrido")
    private String recorrido;
    @Column(name = "tipo_de_ejercicio")
    private String tipoDeEjercicio;
    @Column(name = "peso")
    private Double peso;

    public Carreras() {
    }

    public Carreras(Integer numSec) {
        this.numSec = numSec;
    }

    public Carreras(Integer numSec, Date fecha) {
        this.numSec = numSec;
        this.fecha = fecha;
    }

    public Integer getNumSec() {
        return numSec;
    }

    public void setNumSec(Integer numSec) {
        this.numSec = numSec;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Double getKms() {
        return kms;
    }

    public void setKms(Double kms) {
        this.kms = kms;
    }

    public String getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(String recorrido) {
        this.recorrido = recorrido;
    }

    public String getTipoDeEjercicio() {
        return tipoDeEjercicio;
    }

    public void setTipoDeEjercicio(String tipoDeEjercicio) {
        this.tipoDeEjercicio = tipoDeEjercicio;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numSec != null ? numSec.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carreras)) {
            return false;
        }
        Carreras other = (Carreras) object;
        if ((this.numSec == null && other.numSec != null) || (this.numSec != null && !this.numSec.equals(other.numSec))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "piramboia.Carreras[ numSec=" + numSec + " ]";
    }
    
}
