package org.clincon2001.domain;

import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(schema = "public",name = "diagnosis")
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "diagnosis", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class Diagnosis {

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Diagnosis().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countDiagnoses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Diagnosis o", Long.class).getSingleResult();
    }

	public static List<Diagnosis> findAllDiagnoses() {
        return entityManager().createQuery("SELECT o FROM Diagnosis o", Diagnosis.class).getResultList();
    }

	public static Diagnosis findDiagnosis(Long id) {
        if (id == null) return null;
        return entityManager().find(Diagnosis.class, id);
    }

	public static List<Diagnosis> findDiagnosisEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Diagnosis o", Diagnosis.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Diagnosis attached = Diagnosis.findDiagnosis(this.id);
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
    public Diagnosis merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Diagnosis merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	@OneToMany(mappedBy = "diagnosis")
    private Set<Ccdiagnosis> ccdiagnoses;

	@Column(name = "diagnosisName", length = 100)
    private String diagnosisName;

	public Set<Ccdiagnosis> getCcdiagnoses() {
        return ccdiagnoses;
    }

	public void setCcdiagnoses(Set<Ccdiagnosis> ccdiagnoses) {
        this.ccdiagnoses = ccdiagnoses;
    }

	public String getDiagnosisName() {
        return diagnosisName;
    }

	public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
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
}
