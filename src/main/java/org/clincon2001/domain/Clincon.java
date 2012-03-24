package org.clincon2001.domain;

import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

@Entity
@Table(schema = "public",name = "clincon")
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "clincon", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class Clincon {

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

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Clincon().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countClincons() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Clincon o", Long.class).getSingleResult();
    }

	public static List<Clincon> findAllClincons() {
        return entityManager().createQuery("SELECT o FROM Clincon o", Clincon.class).getResultList();
    }

	public static Clincon findClincon(Long id) {
        if (id == null) return null;
        return entityManager().find(Clincon.class, id);
    }

	public static List<Clincon> findClinconEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Clincon o", Clincon.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Clincon attached = Clincon.findClincon(this.id);
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
    public Clincon merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Clincon merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	@OneToMany(mappedBy = "clincon")
    private Set<Ccdiagnosis> ccdiagnoses;

	@OneToMany(mappedBy = "clincon")
    private Set<Participant> participants;

	@ManyToOne
    @JoinColumn(name = "ccdate", referencedColumnName = "id", nullable = false)
    private Ccdate ccdate;

	@ManyToOne
    @JoinColumn(name = "patient", referencedColumnName = "id", nullable = false)
    private Patient patient;

	@Column(name = "anamnesis", length = 255)
    private String anamnesis;

	@Column(name = "suggestion", length = 255)
    private String suggestion;

	public Set<Ccdiagnosis> getCcdiagnoses() {
        return ccdiagnoses;
    }

	public void setCcdiagnoses(Set<Ccdiagnosis> ccdiagnoses) {
        this.ccdiagnoses = ccdiagnoses;
    }

	public Set<Participant> getParticipants() {
        return participants;
    }

	public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }

	public Ccdate getCcdate() {
        return ccdate;
    }

	public void setCcdate(Ccdate ccdate) {
        this.ccdate = ccdate;
    }

	public Patient getPatient() {
        return patient;
    }

	public void setPatient(Patient patient) {
        this.patient = patient;
    }

	public String getAnamnesis() {
        return anamnesis;
    }

	public void setAnamnesis(String anamnesis) {
        this.anamnesis = anamnesis;
    }

	public String getSuggestion() {
        return suggestion;
    }

	public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
