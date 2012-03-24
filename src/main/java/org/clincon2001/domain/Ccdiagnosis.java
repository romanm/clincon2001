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
@Table(schema = "public",name = "ccdiagnosis")
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "ccdiagnosis", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class Ccdiagnosis {

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Ccdiagnosis().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countCcdiagnoses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Ccdiagnosis o", Long.class).getSingleResult();
    }

	public static List<Ccdiagnosis> findAllCcdiagnoses() {
        return entityManager().createQuery("SELECT o FROM Ccdiagnosis o", Ccdiagnosis.class).getResultList();
    }

	public static Ccdiagnosis findCcdiagnosis(Long id) {
        if (id == null) return null;
        return entityManager().find(Ccdiagnosis.class, id);
    }

	public static List<Ccdiagnosis> findCcdiagnosisEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Ccdiagnosis o", Ccdiagnosis.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Ccdiagnosis attached = Ccdiagnosis.findCcdiagnosis(this.id);
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
    public Ccdiagnosis merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Ccdiagnosis merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	@ManyToOne
    @JoinColumn(name = "clincon", referencedColumnName = "id", nullable = false)
    private Clincon clincon;

	@ManyToOne
    @JoinColumn(name = "diagnosis", referencedColumnName = "id", nullable = false)
    private Diagnosis diagnosis;

	public Clincon getClincon() {
        return clincon;
    }

	public void setClincon(Clincon clincon) {
        this.clincon = clincon;
    }

	public Diagnosis getDiagnosis() {
        return diagnosis;
    }

	public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }
}
