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
@Table(schema = "public",name = "participant")
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "participant", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class Participant {

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
        EntityManager em = new Participant().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countParticipants() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Participant o", Long.class).getSingleResult();
    }

	public static List<Participant> findAllParticipants() {
        return entityManager().createQuery("SELECT o FROM Participant o", Participant.class).getResultList();
    }

	public static Participant findParticipant(Long id) {
        if (id == null) return null;
        return entityManager().find(Participant.class, id);
    }

	public static List<Participant> findParticipantEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Participant o", Participant.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Participant attached = Participant.findParticipant(this.id);
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
    public Participant merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Participant merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	@ManyToOne
    @JoinColumn(name = "clincon", referencedColumnName = "id", nullable = false)
    private Clincon clincon;

	@ManyToOne
    @JoinColumn(name = "doctor01", referencedColumnName = "id", nullable = false)
    private Doctor doctor01;

	@ManyToOne
    @JoinColumn(name = "doctor02", referencedColumnName = "id", nullable = false)
    private Doctor doctor02;

	@ManyToOne
    @JoinColumn(name = "doctor03", referencedColumnName = "id", nullable = false)
    private Doctor doctor03;

	@ManyToOne
    @JoinColumn(name = "doctor04", referencedColumnName = "id", nullable = false)
    private Doctor doctor04;

	@ManyToOne
    @JoinColumn(name = "doctor05", referencedColumnName = "id", nullable = false)
    private Doctor doctor05;

	@ManyToOne
    @JoinColumn(name = "doctor06", referencedColumnName = "id", nullable = false)
    private Doctor doctor06;

	@ManyToOne
    @JoinColumn(name = "doctor07", referencedColumnName = "id", nullable = false)
    private Doctor doctor07;

	@ManyToOne
    @JoinColumn(name = "doctor08", referencedColumnName = "id", nullable = false)
    private Doctor doctor08;

	@ManyToOne
    @JoinColumn(name = "doctor09", referencedColumnName = "id", nullable = false)
    private Doctor doctor09;

	@ManyToOne
    @JoinColumn(name = "doctor10", referencedColumnName = "id", nullable = false)
    private Doctor doctor10;

	public Clincon getClincon() {
        return clincon;
    }

	public void setClincon(Clincon clincon) {
        this.clincon = clincon;
    }

	public Doctor getDoctor01() {
        return doctor01;
    }

	public void setDoctor01(Doctor doctor01) {
        this.doctor01 = doctor01;
    }

	public Doctor getDoctor02() {
        return doctor02;
    }

	public void setDoctor02(Doctor doctor02) {
        this.doctor02 = doctor02;
    }

	public Doctor getDoctor03() {
        return doctor03;
    }

	public void setDoctor03(Doctor doctor03) {
        this.doctor03 = doctor03;
    }

	public Doctor getDoctor04() {
        return doctor04;
    }

	public void setDoctor04(Doctor doctor04) {
        this.doctor04 = doctor04;
    }

	public Doctor getDoctor05() {
        return doctor05;
    }

	public void setDoctor05(Doctor doctor05) {
        this.doctor05 = doctor05;
    }

	public Doctor getDoctor06() {
        return doctor06;
    }

	public void setDoctor06(Doctor doctor06) {
        this.doctor06 = doctor06;
    }

	public Doctor getDoctor07() {
        return doctor07;
    }

	public void setDoctor07(Doctor doctor07) {
        this.doctor07 = doctor07;
    }

	public Doctor getDoctor08() {
        return doctor08;
    }

	public void setDoctor08(Doctor doctor08) {
        this.doctor08 = doctor08;
    }

	public Doctor getDoctor09() {
        return doctor09;
    }

	public void setDoctor09(Doctor doctor09) {
        this.doctor09 = doctor09;
    }

	public Doctor getDoctor10() {
        return doctor10;
    }

	public void setDoctor10(Doctor doctor10) {
        this.doctor10 = doctor10;
    }
}
