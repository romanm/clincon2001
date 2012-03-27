package org.clincon2001.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@Table(schema = "public",name = "patientows1stgeorg")
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "patientows1stgeorg", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class Patientows1stgeorg {

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Patientows1stgeorg().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countPatientows1stgeorgs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Patientows1stgeorg o", Long.class).getSingleResult();
    }

	public static List<Patientows1stgeorg> findAllPatientows1stgeorgs() {
        return entityManager().createQuery("SELECT o FROM Patientows1stgeorg o", Patientows1stgeorg.class).getResultList();
    }

	public static Patientows1stgeorg findPatientows1stgeorg(Long ows1stgeorg) {
        if (ows1stgeorg == null) return null;
        return entityManager().find(Patientows1stgeorg.class, ows1stgeorg);
    }

	public static List<Patientows1stgeorg> findPatientows1stgeorgEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Patientows1stgeorg o", Patientows1stgeorg.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Patientows1stgeorg attached = Patientows1stgeorg.findPatientows1stgeorg(this.ows1stgeorg);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public Patientows1stgeorg merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Patientows1stgeorg merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	@ManyToOne
    @JoinColumn(name = "patient", referencedColumnName = "id", nullable = false)
    private Patient patient;

	public Patient getPatient() {
        return patient;
    }

	public void setPatient(Patient patient) {
        this.patient = patient;
    }

	@Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ows1stgeorg")
    private Long ows1stgeorg;

	public Long getOws1stgeorg() {
        return this.ows1stgeorg;
    }

	public void setOws1stgeorg(Long id) {
        this.ows1stgeorg = id;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
